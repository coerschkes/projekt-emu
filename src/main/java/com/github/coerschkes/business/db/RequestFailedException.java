package com.github.coerschkes.business.db;

import javax.ws.rs.core.Response;

public class RequestFailedException extends RuntimeException {

    public RequestFailedException(final Response.StatusType responseStatus) {
        super("REQUEST FAILED: " + responseStatus.getReasonPhrase());
    }
}
