package com.github.coerschkes.business.db;

import com.github.coerschkes.business.model.Measurement;
import com.github.coerschkes.business.model.MeasurementSeries;

import java.util.concurrent.CompletableFuture;

public interface AsyncMeasurementRepository {
    /**
     * Returns a {@link CompletableFuture} of all {@link Measurement} from a specified {@link MeasurementSeries}.
     *
     * @param measurementSeriesId - Database ID of the {@link MeasurementSeries} to read
     * @return {@link CompletableFuture} of {@link Measurement[]}
     */
    CompletableFuture<Measurement[]> readMeasurementsFromSeries(final int measurementSeriesId);

    /**
     * Returns a {@link CompletableFuture} of all {@link MeasurementSeries} in the database.
     *
     * @return {@link CompletableFuture} of {@link MeasurementSeries[]}
     */
    CompletableFuture<MeasurementSeries[]> readMeasurementSeries();

    /**
     * Saves a {@link Measurement} to the database and returns a {@link CompletableFuture<Void>}
     *
     * @param measurement         - the {@link Measurement} to save
     * @return {@link CompletableFuture<Void>}
     */
    CompletableFuture<Void> saveMeasurement(final Measurement measurement);

    /**
     * Saves a {@link MeasurementSeries} to the database and returns a {@link CompletableFuture<Void>}
     *
     * @param measurementSeries - the {@link MeasurementSeries} to save
     * @return {@link CompletableFuture<Void>}
     */
    CompletableFuture<Void> saveMeasurementSeries(final MeasurementSeries measurementSeries);

    CompletableFuture<Void> deleteMeasurementSeries(int measurementSeriesId);
}
