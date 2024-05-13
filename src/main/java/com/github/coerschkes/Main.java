package com.github.coerschkes;

import com.github.coerschkes.business.db.AsyncMeasurementRemoteRepository;
import com.github.coerschkes.business.model.Measurement;
import com.github.coerschkes.business.model.MeasurementSeries;
import com.github.coerschkes.gui.BaseControl;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException, ClassNotFoundException {
		new BaseControl(primaryStage);

        AsyncMeasurementRemoteRepository repo = new AsyncMeasurementRemoteRepository();
        callRead(repo);
        CompletableFuture<Void> voidCompletableFuture = repo.saveMeasurement(1, new Measurement(0,1 ,2.3, 1289259073));
        voidCompletableFuture.whenComplete((measurementSeries, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                throw new RuntimeException(throwable);
            } else {
                System.out.println("Saving measurement series complete");
                callRead(repo);
            }
        });
    }

    private static void callRead(AsyncMeasurementRemoteRepository repo) {
        CompletableFuture<MeasurementSeries[]> measurementSeriesFuture = repo.readMeasurementSeries();
        measurementSeriesFuture.whenComplete((measurementSeries, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                throw new RuntimeException(throwable);
            } else {
                System.out.println(Arrays.toString(measurementSeries));
            }
        });
    }

    public static void main(String... args) {
        launch(args);
    }
}
