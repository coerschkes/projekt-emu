package com.github.coerschkes.gui;

import com.github.coerschkes.business.db.AsyncDatabaseModel;
import com.github.coerschkes.business.emu.EmuModel;
import com.github.coerschkes.business.model.Measurement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class BaseControl {
    private final AsyncDatabaseModel asyncDatabaseModel;
    private final EmuModel emuModel;
    private final BaseView baseView;

    public BaseControl(final Stage primaryStage) throws IOException {
        this.asyncDatabaseModel = AsyncDatabaseModel.getInstance();
        this.emuModel = EmuModel.getInstance();
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/baseView.fxml"));
        final Scene scene = new Scene(loader.load());
        this.baseView = ((BaseView) loader.getController());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> emuModel.disconnect());
    }

    public CompletableFuture<Measurement[]> readMeasurements(final int measurementSeriesId) {
        return asyncDatabaseModel.readMeasurementsFromDb(measurementSeriesId);
    }

    private CompletableFuture<Void> saveMeasurement(final int measurementSeriesId, final Measurement measurement) {
        return asyncDatabaseModel.saveMeasurement(measurementSeriesId, measurement);
    }

    //todo: refactor here -> maybe handle futures here to reduce view logic
    public CompletableFuture<Measurement> readMeasurementFromEmu(final String measurementSeriesId, final String measurementId) {
        final int parsedMeasurementSeriesId = Integer.parseInt(measurementSeriesId);
        final int parsedMeasurementId = Integer.parseInt(measurementId);

        final CompletableFuture<Measurement> future = new CompletableFuture<>();

        emuModel.readMeasurement().whenComplete((measurementValue, throwable) -> {
            if (throwable != null) {
                future.completeExceptionally(throwable);
            } else {
                final String formattedValue = measurementValue.substring(measurementValue.indexOf("(") + 1, measurementValue.indexOf("*"));
                final Measurement result = new Measurement(parsedMeasurementId, parsedMeasurementSeriesId, Double.parseDouble(formattedValue), System.currentTimeMillis());
                this.saveMeasurement(parsedMeasurementSeriesId, result);
                future.complete(result);
            }
        });
        return future;
    }
}
