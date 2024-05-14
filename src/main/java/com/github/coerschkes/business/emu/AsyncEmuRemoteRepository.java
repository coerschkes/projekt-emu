package com.github.coerschkes.business.emu;

import com.github.coerschkes.business.BaseAsyncRemoteRepository;
import com.github.coerschkes.business.model.Measurement;

import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

public class AsyncEmuRemoteRepository extends BaseAsyncRemoteRepository implements AsyncEmuRepository {
    private static final String BASE_URL = "http://localhost:8081/emu_service_war_exploded/rest";

    public AsyncEmuRemoteRepository() {
        super(BASE_URL);
    }

    @Override
    public CompletableFuture<Measurement> readMeasurement() {
        return asyncRequestBuilder.GET("measurement", Response.Status.OK, Measurement.class);
    }
}
