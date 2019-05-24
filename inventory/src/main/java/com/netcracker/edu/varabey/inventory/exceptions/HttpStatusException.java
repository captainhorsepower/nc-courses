package com.netcracker.edu.varabey.inventory.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class HttpStatusException extends RuntimeException {
    @Getter
    @Setter
    protected HttpStatus httpStatus;

    public HttpStatusException() {
    }

    public HttpStatusException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatusException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }
}
