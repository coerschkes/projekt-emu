package com.github.coerschkes.gui;

import com.github.coerschkes.business.model.Measurement;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BaseView {
    private final BaseControl baseControl;
    private final Pane pane = new Pane();
    private final Label labelMeasurementSeriesId = new Label("MessreihenId");
    private final Label labelMeasurementId = new Label("lfd. Nr. der Messung");
    private final TextField textMeasurementSeriesId = new TextField();
    private final TextField textMeasurementId = new TextField();
    private final TextField textDisplay = new TextField();
    private final Button buttonReadMeasurementsFromDb = new Button("Messungen aus DB lesen");
    private final Button buttonReadMeasurementFromEMU = new Button("Messung aus EMU aufnehmen");

    public BaseView(final BaseControl baseControl, final Stage stage) {
        Scene scene = new Scene(this.pane, 510, 170);
        stage.setScene(scene);
        stage.setTitle("EMU-Anwendung");
        this.baseControl = baseControl;
        this.initComponents();
        this.registerListeners();
    }

    private void initComponents() {
        labelMeasurementSeriesId.setLayoutX(10);
        labelMeasurementSeriesId.setLayoutY(30);
        labelMeasurementId.setLayoutX(10);
        labelMeasurementId.setLayoutY(70);
        pane.getChildren().addAll(labelMeasurementSeriesId, labelMeasurementId);

        textMeasurementSeriesId.setLayoutX(140);
        textMeasurementSeriesId.setLayoutY(30);
        textMeasurementId.setLayoutX(140);
        textMeasurementId.setLayoutY(70);
        textDisplay.setLayoutX(10);
        textDisplay.setLayoutY(110);
        textDisplay.setPrefWidth(480);
        pane.getChildren().addAll(textMeasurementSeriesId, textMeasurementId, textDisplay);

        buttonReadMeasurementsFromDb.setLayoutX(310);
        buttonReadMeasurementsFromDb.setLayoutY(30);
        buttonReadMeasurementsFromDb.setPrefWidth(180);
        buttonReadMeasurementFromEMU.setLayoutX(310);
        buttonReadMeasurementFromEMU.setLayoutY(70);
        buttonReadMeasurementFromEMU.setPrefWidth(180);
        pane.getChildren().addAll(buttonReadMeasurementsFromDb, buttonReadMeasurementFromEMU);
    }

    private void registerListeners() {
        buttonReadMeasurementsFromDb.setOnAction(e -> {
            final Measurement[] resultMeasurements = baseControl.readMeasurements(textMeasurementSeriesId.getText());
            final StringBuilder result = new StringBuilder();
            for (Measurement resultMeasurement : resultMeasurements) {
                result.append(resultMeasurement.getAttributes()).append(" / ");
            }
            textDisplay.setText(result.toString());
        });
        buttonReadMeasurementFromEMU.setOnAction(event -> readMeasurementFromEmu());
    }

    private void readMeasurementFromEmu() {
        baseControl
                .readMeasurementFromEmu(textMeasurementSeriesId.getText(), textMeasurementId.getText())
                .whenComplete((measurement, throwable) -> {
                    if (throwable != null) {
                        showErrorMessage(throwable.getMessage());
                    } else {
                        textDisplay.setText(measurement.getAttributes());
                    }
                });
    }

    void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehlermeldung");
        alert.setContentText(message);
        alert.showAndWait();
    }

}

