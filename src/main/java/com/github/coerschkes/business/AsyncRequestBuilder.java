package com.github.coerschkes.business;

import com.github.coerschkes.business.util.GenericObjectMapper;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class AsyncRequestBuilder {
    private final WebTarget webTarget;
    private final BiFunction<Response, Response.Status, Response> errorHandler;

    public AsyncRequestBuilder(final BiFunction<Response, Response.Status, Response> errorHandler, final String baseUrl) {
        this.errorHandler = errorHandler;
        this.webTarget = ClientBuilder.newClient().target(baseUrl);
    }

    public <T> CompletableFuture<T> GET(final String path, final Response.Status happyPathStatus, final Class<T> responseType) {
        return CompletableFuture.supplyAsync(() ->
                GenericObjectMapper.fromJson(errorHandler.apply(getInvocationBuilder(path)
                        .get(), happyPathStatus).readEntity(String.class), responseType));
    }

    public <T> CompletableFuture<Void> POST(final String path, final T t, final Response.Status happyPathStatus) {
        return CompletableFuture.runAsync(() ->
                errorHandler.apply(getInvocationBuilder(path)
                        .post(Entity.json(GenericObjectMapper.toJson(t))), happyPathStatus)
        );
    }

    protected Invocation.Builder getInvocationBuilder(final String path) {
        return webTarget.path(path).request(MediaType.APPLICATION_JSON);
    }

}
