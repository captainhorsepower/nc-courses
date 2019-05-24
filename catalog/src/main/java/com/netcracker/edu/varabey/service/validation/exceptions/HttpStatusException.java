package com.netcracker.edu.varabey.service.validation.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class HttpStatusException extends RuntimeException {
    @Getter
    @Setter
    protected HttpStatus httpStatus;

    public HttpStatusException() {
    }

    public HttpStatusException(String message) {
        super(message);
    }
}
