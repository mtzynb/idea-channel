package com.rahand.idea.channel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.Charset;

public class CustomWebClientResponseException extends WebClientResponseException {
    private final HttpStatus status;
    private final ErrorResponse errorDetails;

    public CustomWebClientResponseException(HttpStatus status, ErrorResponse errorDetails) {
        super(errorDetails.getMessage(), status.value(), status.getReasonPhrase(), null, null, Charset.defaultCharset());
        this.status = status;
        this.errorDetails = errorDetails;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorResponse getErrorDetails() {
        return errorDetails;
    }

}
