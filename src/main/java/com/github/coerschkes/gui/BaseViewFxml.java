package com.github.coerschkes.gui;

import com.github.coerschkes.business.db.DatabaseModel;
import com.github.coerschkes.business.model.MeasurementSeries;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BaseViewFxml {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseViewFxml.class);
    private final DatabaseModel databaseModel;

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

    public BaseViewFxml() {
        this.databaseModel = DatabaseModel.getInstance();
    }

    public void readAllMeasurementSeries() throws SQLException, ClassNotFoundException {
        MeasurementSeries[] measurementSeries = this.databaseModel.readAllMeasurementSeries();
        tableContent.getItems().clear();
        tableContent.setItems(FXCollections.observableList(Arrays.stream(measurementSeries)
                .map(MeasurementSeriesRow::of)
                .collect(Collectors.toList())));
    }

    public void addMeasurementSeries() {
        if (!(textMeasurementSeriesId.getText().isEmpty() || textTimeInterval.getText().isEmpty() || textConsumer.getText().isEmpty() || textMeasurementSize.getText().isEmpty())) {
            try {
                databaseModel.saveMeasurementSeries(new MeasurementSeries(textMeasurementSeriesId.getText(), textTimeInterval.getText(), textConsumer.getText(), textMeasurementSize.getText()));
            } catch (Exception e) {
                showErrorMessage(e.getMessage());
                LOGGER.error("Error during adding of a measurement series", e);
            }
        }
    }

    void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehlermeldung");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
