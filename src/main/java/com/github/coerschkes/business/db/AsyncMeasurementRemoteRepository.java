package com.github.coerschkes.business.db;

import com.github.coerschkes.business.BaseAsyncRemoteRepository;
import com.github.coerschkes.business.model.Measurement;
import com.github.coerschkes.business.model.MeasurementSeries;
import com.github.coerschkes.business.util.GenericObjectMapper;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

public class AsyncMeasurementRemoteRepository extends BaseAsyncRemoteRepository implements AsyncMeasurementRepository {
    private static final String BASE_URL = "http://localhost:8080/database_service_war_exploded/rest";

    public AsyncMeasurementRemoteRepository() {
        super(BASE_URL);
    }

    @Override
    public CompletableFuture<Measurement[]> readMeasurementsFromSeries(final int measurementSeriesId) {
        return CompletableFuture
                .supplyAsync(() ->
                        GenericObjectMapper.fromJson(handleResponseStatus(getInvocationBuilder("measurement/" + measurementSeriesId)
                                .get(), Response.Status.OK).readEntity(String.class), Measurement[].class));
    }

    @Override
    public CompletableFuture<MeasurementSeries[]> readMeasurementSeries() {
        return CompletableFuture.supplyAsync(() ->
                GenericObjectMapper.fromJson(handleResponseStatus(getInvocationBuilder("measurementSeries")
                        .get(), Response.Status.OK).readEntity(String.class), MeasurementSeries[].class));
    }

    @Override
    public CompletableFuture<Void> saveMeasurement(final Measurement measurement) {
        return CompletableFuture.runAsync(() ->
                handleResponseStatus(getInvocationBuilder("measurementSeries/" + measurement.getMeasurementSeriesId())
                        .post(Entity.json(GenericObjectMapper.toJson(measurement))), Response.Status.CREATED)
        );
    }

    @Override
    public CompletableFuture<Void> saveMeasurementSeries(final MeasurementSeries measurementSeries) {
        return CompletableFuture.runAsync(() ->
                handleResponseStatus(getInvocationBuilder("measurementSeries")
                        .post(Entity.json(GenericObjectMapper.toJson(measurementSeries))), Response.Status.CREATED));
    }
}
