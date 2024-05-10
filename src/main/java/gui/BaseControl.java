package gui;

import business.db.DatabaseModel;
import business.emu.EmuModel;
import business.model.Measurement;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class BaseControl {
    private final DatabaseModel databaseModel;
    private final EmuModel emuModel;
    private final BaseView baseView;

    public BaseControl(final Stage primaryStage) {
        this.databaseModel = DatabaseModel.getInstance();
        this.emuModel = EmuModel.getInstance();
        this.baseView = new BaseView(this, primaryStage);
        primaryStage.show();
    }

    public Measurement[] readMeasurements(final String measurementSeriesId) {
        try {
            return this.databaseModel.readMeasurementsFromDb(Integer.parseInt(measurementSeriesId));
        } catch (Exception e) {
            throw this.wrapExceptionInstance(e);
        }
    }

    private void saveMeasurement(final int measurementSeriesId, final Measurement measurement) {
        try {
            System.out.println("saving measurement " + measurement);
            this.databaseModel.saveMeasurement(measurementSeriesId, measurement);
        } catch (Exception e) {
            throw this.wrapExceptionInstance(e);
        }
    }

    public CompletableFuture<Measurement> readMeasurementFromEmu(final String measurementSeriesId, final String measurementId) {
        final int parsedMeasurementSeriesId = Integer.parseInt(measurementSeriesId);
        final int parsedMeasurementId = Integer.parseInt(measurementId);

        final CompletableFuture<Measurement> future = new CompletableFuture<>();

        emuModel.readMeasurement().whenComplete((measurementValue, throwable) -> {
            if (throwable != null) {
                future.completeExceptionally(throwable);
            } else {
                final String formattedValue = measurementValue.substring(measurementValue.indexOf("(") + 1, measurementValue.indexOf("*"));
                final Measurement result = new Measurement(parsedMeasurementId, Double.parseDouble(formattedValue), System.currentTimeMillis());
                this.saveMeasurement(parsedMeasurementSeriesId, result);
                future.complete(result);
            }
        });
        return future;
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
