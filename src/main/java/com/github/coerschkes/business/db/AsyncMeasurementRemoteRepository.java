package com.github.coerschkes.business.db;

import com.github.coerschkes.business.BaseAsyncRemoteRepository;
import com.github.coerschkes.business.model.Measurement;
import com.github.coerschkes.business.model.MeasurementSeries;

import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

public class AsyncMeasurementRemoteRepository extends BaseAsyncRemoteRepository implements AsyncMeasurementRepository {
    private static final String BASE_URL = "http://localhost:8080/database_service_war_exploded/rest";

    public AsyncMeasurementRemoteRepository() {
        super(BASE_URL);
    }

    @Override
    public CompletableFuture<Measurement[]> readMeasurementsFromSeries(final int measurementSeriesId) {
        return asyncRequestBuilder.GET("measurement/" + measurementSeriesId, Response.Status.OK, Measurement[].class);
    }

    @Override
    public CompletableFuture<MeasurementSeries[]> readMeasurementSeries() {
        return asyncRequestBuilder.GET("measurementSeries", Response.Status.OK, MeasurementSeries[].class);
    }

    @Override
    public CompletableFuture<Void> saveMeasurement(final Measurement measurement) {
        return asyncRequestBuilder.POST("measurementSeries/" + measurement.getMeasurementSeriesId(), measurement, Response.Status.CREATED);
    }

    @Override
    public CompletableFuture<Void> saveMeasurementSeries(final MeasurementSeries measurementSeries) {
        return asyncRequestBuilder.POST("measurementSeries", measurementSeries, Response.Status.CREATED);
    }

    @Override
    public CompletableFuture<Void> deleteMeasurementSeries(int measurementSeriesId) {
        return asyncRequestBuilder.DELETE("measurementSeries/" + measurementSeriesId, Response.Status.NO_CONTENT);
    }
}
