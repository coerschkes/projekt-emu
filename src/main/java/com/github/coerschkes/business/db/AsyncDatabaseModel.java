package com.github.coerschkes.business.db;

import com.github.coerschkes.business.model.Measurement;
import com.github.coerschkes.business.model.MeasurementSeries;

import java.util.concurrent.CompletableFuture;

public final class AsyncDatabaseModel {
    private static AsyncDatabaseModel modelInstance;
    private final AsyncMeasurementRepository repository;

    public static AsyncDatabaseModel getInstance() {
        if (modelInstance == null) {
            modelInstance = new AsyncDatabaseModel();
        }
        return modelInstance;
    }

    private AsyncDatabaseModel() {
        repository = new AsyncMeasurementRemoteRepository();
    }

    public CompletableFuture<Void> saveMeasurement(final Measurement measurement) {
        return repository.saveMeasurement(measurement);
    }

    public CompletableFuture<MeasurementSeries[]> readAllMeasurementSeries() {
        return repository.readMeasurementSeries();
    }

    public CompletableFuture<Void> saveMeasurementSeries(final MeasurementSeries measurementSeries) {
        return this.repository.saveMeasurementSeries(measurementSeries);
    }
}
