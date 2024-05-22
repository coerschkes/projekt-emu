package com.github.coerschkes.gui;

import com.github.coerschkes.business.model.MeasurementSeries;
import com.github.coerschkes.business.util.RequestFailedException;
import com.github.coerschkes.gui.util.MeasurementSeriesRow;
import com.github.coerschkes.gui.util.NumericTextFormatter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.ws.rs.ProcessingException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BaseView {
    private ReactiveControl reactiveControl;

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

    public void initialize() {
        this.reactiveControl = new ReactiveControl(this::showError);
        this.textMeasurementSeriesId.setTextFormatter(NumericTextFormatter.create());
        this.textTimeInterval.setTextFormatter(NumericTextFormatter.create());
        readAllMeasurementSeries();
    }

    public void readAllMeasurementSeries() {
        reactiveControl.readMeasurementSeries(series -> {
            tableContent.getItems().clear();
            tableContent.setItems(FXCollections.observableList(Arrays.stream(series)
                    .map(MeasurementSeriesRow::of)
                    .collect(Collectors.toList())));
        });
    }

    public void addMeasurementSeries() {
        if (!(textMeasurementSeriesId.getText().isEmpty() || textTimeInterval.getText().isEmpty() || textConsumer.getText().isEmpty() || textMeasurementSize.getText().isEmpty())) {
            reactiveControl.saveMeasurementSeries(buildMeasurementSeries(), this::readAllMeasurementSeries);

        }
    }

    public void addMeasurement() {
        final MeasurementSeriesRow selectedRow = this.tableContent.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            reactiveControl.readMeasurement(measurement -> reactiveControl.saveMeasurement(measurement, this::readAllMeasurementSeries));
        }
    }

    private void showError(final Throwable e) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(mapExceptionMessage(e));
            System.out.println(e.getCause().getLocalizedMessage());
            alert.showAndWait();
        });
    }

    private String mapExceptionMessage(final Throwable e) {
        if (e.getCause() != null) {
            if (e.getCause() instanceof RequestFailedException) {
                return e.getCause().getMessage();
            } else if (e.getCause() instanceof ProcessingException) {
                return "Unable to connect to the service";
            }
        }
        return "Unknown error. Please view log for detailed information";
    }

    private MeasurementSeries buildMeasurementSeries() {
        return new MeasurementSeries(Integer.parseInt(textMeasurementSeriesId.getText()), Integer.parseInt(textTimeInterval.getText()), textConsumer.getText(), textMeasurementSize.getText());
    }
}
