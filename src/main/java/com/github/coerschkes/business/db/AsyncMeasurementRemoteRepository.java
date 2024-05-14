package com.github.coerschkes.business.db;

import com.github.coerschkes.business.model.Measurement;
import com.github.coerschkes.business.model.MeasurementSeries;
import com.github.coerschkes.business.util.GenericObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

public class AsyncMeasurementRemoteRepository implements AsyncMeasurementRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncMeasurementRemoteRepository.class);
    private static final String BASE_URL = "http://localhost:8080/database_service_war_exploded/rest";
    private static final WebTarget WEB_TARGET = ClientBuilder.newClient().target(BASE_URL);

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

    private Invocation.Builder getInvocationBuilder(final String path) {
        return WEB_TARGET.path(path).request(MediaType.APPLICATION_JSON);
    }

    private Response handleResponseStatus(final Response response, final Response.Status happyPathStatus) {
        if (response.getStatusInfo() != happyPathStatus) {
            LOGGER.error("Server side exception on async request: {}", response.readEntity(String.class));
            LOGGER.error("Expected response status {} got {}", happyPathStatus, response.getStatusInfo());
            throw new RequestFailedException(response.getStatusInfo());
        } else {
            return response;
        }
    }
}
