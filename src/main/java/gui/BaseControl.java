package gui;

import business.db.DatabaseModel;
import business.model.Measurement;
import javafx.stage.Stage;

import java.sql.SQLException;

public class BaseControl {
    private final DatabaseModel databaseModel;
    private final BaseView baseView;

    public BaseControl(final Stage primaryStage) {
        this.databaseModel = DatabaseModel.getInstance();
        this.baseView = new BaseView(this, primaryStage, this.databaseModel);
        primaryStage.show();
    }

    public Measurement[] readMeasurements(final String measurementSeriesId) {
        try {
            return this.databaseModel.readMeasurementsFromDb(Integer.parseInt(measurementSeriesId));
        } catch (Exception e) {
            throw this.wrapExceptionInstance(e);
        }
    }

    private void saveMeasurement(int measurementSeriesId, Measurement measurement) {
        try {
            this.databaseModel.saveMeasurement(measurementSeriesId, measurement);
        } catch (Exception e) {
            throw this.wrapExceptionInstance(e);
        }
    }

    public Measurement readMeasurementFromEmu(String measurementSeriesId, String measurementId) {
        final int parsedMeasurementSeriesId = Integer.parseInt(measurementSeriesId);
        final int parsedMeasurementId = Integer.parseInt(measurementId);

        // todo: Dummy here, must be substituted
        final Measurement result = new Measurement(parsedMeasurementId, 0.345, System.currentTimeMillis());

        this.saveMeasurement(parsedMeasurementSeriesId, result);
        return result;
    }

    private RuntimeException wrapExceptionInstance(final Exception e) {
        if (e.getCause() instanceof ClassNotFoundException) {
            baseView.showErrorMessage("Fehler bei der Verbindungerstellung zur Datenbank.");
        } else if (e.getCause() instanceof SQLException) {
            baseView.showErrorMessage("Fehler beim Zugriff auf die Datenbank.");
        } else if (e.getCause() instanceof NumberFormatException) {
            baseView.showErrorMessage("Das Format der eingegebenen MessreihenId ist nicht korrekt.");
        }
        return new RuntimeException(e);
    }

}
