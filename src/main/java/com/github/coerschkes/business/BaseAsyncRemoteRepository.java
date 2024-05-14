package com.github.coerschkes.business;

import com.github.coerschkes.business.util.RequestFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public abstract class BaseAsyncRemoteRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseAsyncRemoteRepository.class);
    private final WebTarget webTarget;

    public BaseAsyncRemoteRepository(final String baseUrl) {
        this.webTarget = ClientBuilder.newClient().target(baseUrl);
    }

    protected Invocation.Builder getInvocationBuilder(final String path) {
        return webTarget.path(path).request(MediaType.APPLICATION_JSON);
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
