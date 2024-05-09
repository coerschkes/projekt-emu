package gui;

import business.db.DatabaseModel;
import business.model.Measurement;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BaseView {

    private DatabaseModel dbModel;
    private BaseControl baseControl;

    private Pane pane = new Pane();
    private Label labelMeasurementSeriesId = new Label("MessreihenId");
    private Label labelMeasurementId = new Label("lfd. Nr. der Messung");
    private TextField textMeasurementSeriesId = new TextField();
    private TextField textMeasurementId = new TextField();
    private TextField textDisplay = new TextField();
    private Button buttonReadMeasurementsFromDb = new Button("Messungen aus DB lesen");
    private Button buttonReadMeasurementFromEMU = new Button("Messung aus EMU aufnehmen");

    public BaseView(BaseControl baseControl, Stage stage, DatabaseModel databaseModel) {
        Scene scene = new Scene(this.pane, 510, 170);
        stage.setScene(scene);
        stage.setTitle("EMU-Anwendung");
        this.baseControl = baseControl;
        this.dbModel = databaseModel;
        this.initComponents();
        this.initListener();
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

    private void initListener() {
        buttonReadMeasurementsFromDb.setOnAction(e -> {
            final Measurement[] resultMeasurements = baseControl.readMeasurements(textMeasurementSeriesId.getText());
            final StringBuilder result = new StringBuilder();
            for (Measurement resultMeasurement : resultMeasurements) {
                result.append(resultMeasurement.getAttributes()).append(" / ");
            }
            textDisplay.setText(result.toString());
        });
        buttonReadMeasurementFromEMU.setOnAction(event -> textDisplay.setText(baseControl
                .readMeasurementFromEmu(textMeasurementSeriesId.getText(),
                        textMeasurementId.getText()).getAttributes()));
    }

    void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehlermeldung");
        alert.setContentText(message);
        alert.showAndWait();
    }

}

