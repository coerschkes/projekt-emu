package com.github.coerschkes.business;

import com.github.coerschkes.business.util.RequestFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

public abstract class BaseAsyncRemoteRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseAsyncRemoteRepository.class);
    protected final AsyncRequestBuilder asyncRequestBuilder;

    public BaseAsyncRemoteRepository(final String baseUrl) {
        this.asyncRequestBuilder = new AsyncRequestBuilder(this::handleResponseStatus, baseUrl);
    }

    protected Response handleResponseStatus(final Response response, final Response.Status happyPathStatus) {
        if (response.getStatusInfo() != happyPathStatus) {
            LOGGER.error("Server side exception on async request: {}", response.readEntity(String.class));
            LOGGER.error("Expected response status {} got {}", happyPathStatus, response.getStatusInfo());
            throw new RequestFailedException(response.getStatusInfo());
        } else {
            return response;
        }
    }
}
