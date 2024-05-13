package com.github.coerschkes.gui;

import com.github.coerschkes.business.db.AsyncDatabaseModel;
import com.github.coerschkes.business.emu.EmuModel;
import com.github.coerschkes.business.model.Measurement;
import com.github.coerschkes.business.model.MeasurementSeries;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class BaseView {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseView.class);
    private final AsyncDatabaseModel databaseModel;

    @FXML
    private TextField textMeasurementSeriesId;
    @FXML
    private TextField textTimeInterval;
    @FXML
    private TextField textConsumer;
    @FXML
    private TextField textMeasurementSize;
    @FXML
    private TableView<MeasurementSeriesRow> tableContent;
    @FXML
    private StackPane rootPane;

    public BaseView() {
        this.databaseModel = AsyncDatabaseModel.getInstance();
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        readAllMeasurementSeries();
    }

    public void readAllMeasurementSeries() {
        this.databaseModel.readAllMeasurementSeries().whenComplete((measurementSeries, throwable) -> {
            if (throwable != null) {
                this.handleError(throwable);
            } else {
                tableContent.getItems().clear();
                tableContent.setItems(FXCollections.observableList(Arrays.stream(measurementSeries)
                        .map(MeasurementSeriesRow::of)
                        .collect(Collectors.toList())));
            }
        });
    }

    public void addMeasurementSeries() {
        if (!(textMeasurementSeriesId.getText().isEmpty() || textTimeInterval.getText().isEmpty() || textConsumer.getText().isEmpty() || textMeasurementSize.getText().isEmpty())) {
            try {
                databaseModel.saveMeasurementSeries(createMeasurementSeries()).whenComplete((unused, throwable) -> {
                    readAllMeasurementSeries();
                });
                readAllMeasurementSeries();
            } catch (Exception e) {
                handleError(e);
            }
        }
    }

    public void addMeasurement() {
        final MeasurementSeriesRow selectedRow = this.tableContent.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            final int measurementSeriesId = selectedRow.getIdentNumber();

            EmuModel.getInstance().readMeasurement().whenComplete(onMeasurementReceived(measurementSeriesId));
        }
    }

    private BiConsumer<String, Throwable> onMeasurementReceived(final int measurementSeriesId) {
        return (value, throwable) -> {
            if (throwable != null) {
                handleError(throwable);
            } else {
                final String formattedValue = value.substring(value.indexOf("(") + 1, value.indexOf("*"));
                saveMeasurement(measurementSeriesId, new Measurement(0, measurementSeriesId, Double.parseDouble(formattedValue), System.currentTimeMillis()));
            }
        };
    }

    private void saveMeasurement(final int measurementSeriesId, final Measurement measurement) {
        databaseModel.saveMeasurement(measurementSeriesId, measurement).whenComplete((unused, throwable) -> {
            if (throwable != null) {
                handleError(throwable);
            } else {
                readAllMeasurementSeries();
            }
        });
    }

    //todo: check if text can be set numeric only
    private MeasurementSeries createMeasurementSeries() {
        return new MeasurementSeries(Integer.parseInt(textMeasurementSeriesId.getText()), Integer.parseInt(textTimeInterval.getText()), textConsumer.getText(), textMeasurementSize.getText());
    }

    public void handleError(final Throwable e) {
        LOGGER.error("Error during execution", e);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehlermeldung");
        alert.setContentText(mapExceptionMessage(e));
        alert.showAndWait();
    }

    private String mapExceptionMessage(final Throwable e) {
        if (e.getCause() instanceof ClassNotFoundException) {
            return "Fehler bei der Verbindungerstellung zur Datenbank.";
        } else if (e.getCause() instanceof SQLException) {
            return "Fehler beim Zugriff auf die Datenbank.";
        } else if (e.getCause() instanceof NumberFormatException) {
            return "Das Format der eingegebenen MessreihenId ist nicht korrekt.";
        } else {
            return e.getMessage();
        }
    }
}
