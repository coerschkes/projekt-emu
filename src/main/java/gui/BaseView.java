package gui;

import business.Measurement;
import business.db.DbModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BaseView {

    private DbModel dbModel;
    private BaseControl baseControl;

    private Pane pane = new Pane();
    private Label labelMeasurementSeriesId = new Label("MessreihenId");
    private Label labelMeasurementId = new Label("lfd. Nr. der Messung");
    private TextField textMeasurementSeriesId = new TextField();
    private TextField textMeasurementId = new TextField();
    private TextField textDisplay = new TextField();
    private Button buttonReadMeasurementsFromDb = new Button("Messungen aus DB lesen");
    private Button buttonReadMeasurementFromEMU = new Button("Messung aus EMU aufnehmen");

    public BaseView(BaseControl baseControl, Stage stage, DbModel dbModel) {
        Scene scene = new Scene(this.pane, 510, 170);
        stage.setScene(scene);
        stage.setTitle("EMU-Anwendung");
        this.baseControl = baseControl;
        this.dbModel = dbModel;
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
        buttonReadMeasurementsFromDb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Measurement[] resultMeasurements = baseControl.readMeasurements(textMeasurementSeriesId.getText());
                String result = "";
                for (int i = 0; i < resultMeasurements.length; i++) {
                    result = result + resultMeasurements[i].getAttributes() + " / ";
                }
                textDisplay.setText(result);
            }
        });
        buttonReadMeasurementFromEMU.setOnAction(aEvent -> {
            textDisplay.setText(baseControl
                    .readMeasurementFromEmu(textMeasurementSeriesId.getText(),
                            textMeasurementId.getText()).getAttributes());
        });
    }

    void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehlermeldung");
        alert.setContentText(message);
        alert.showAndWait();
    }

}

