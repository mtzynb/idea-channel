package com.rahand.idea.channel.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ChannelRequestException extends ResponseStatusException {

    public ChannelRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    @Override
    public HttpHeaders getResponseHeaders() {
        // return response headers
        return null;
    }
}
