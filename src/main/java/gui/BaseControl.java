package gui;

import business.Measurement;
import business.db.DbModel;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class BaseControl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseControl.class);
    private final DbModel dbModel;
    private final BaseView baseView;

    public BaseControl(Stage primaryStage) {
        this.dbModel = DbModel.getInstance();
        this.baseView = new BaseView(this, primaryStage, this.dbModel);
        primaryStage.show();
    }

    public Measurement[] readMeasurements(String measurementSeriesId) {
        try {
            return this.callDbModelQuery(dbModel1 -> dbModel1.readMeasurementsFromDb(Integer.parseInt(measurementSeriesId)));
        } catch (NumberFormatException e) {
            baseView.showErrorMessage("Das Format der eingegebenen MessreihenId ist nicht korrekt.");
            throw e;
        }
    }

    private void saveMeasurement(int measurementSeriesId, Measurement measurement) {
        this.callDbModelUpdate(dbModel1 -> dbModel1.saveMeasurement(measurementSeriesId, measurement));
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

    private <T> T callDbModelQuery(final Function<DbModel, T> transformer) {
        try {
            return transformer.apply(this.dbModel);
        } catch (RuntimeException e) {
            throw this.showErrorBasedOnException(e);
        }
    }

    private void callDbModelUpdate(final Consumer<DbModel> modelConsumer) {
        try {
            modelConsumer.accept(this.dbModel);
        } catch (RuntimeException e) {
            throw this.showErrorBasedOnException(e);
        }
    }

    private RuntimeException showErrorBasedOnException(final RuntimeException e) {
        if (e.getCause() instanceof ClassNotFoundException) {
            baseView.showErrorMessage("Fehler bei der Verbindungerstellung zur Datenbank.");
        } else if (e.getCause() instanceof SQLException) {
            baseView.showErrorMessage("Fehler beim Zugriff auf die Datenbank.");
        }
        return e;
    }

}
