package gui;

import business.Measurement;
import business.db.DatabaseModel;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import javafx.stage.Stage;

import java.sql.SQLException;

public class BaseControl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseControl.class);
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
        Measurement result = null;
        int parsedMeasurementSeriesId = Integer.parseInt(measurementSeriesId);
        int lfdNr = Integer.parseInt(measurementId);

        // Dummy-Messung-Objekt, muss ersetzt werden !!!
        result = new Measurement(lfdNr, 0.345, System.currentTimeMillis());

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
