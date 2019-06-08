package com.netcracker.edu.varabey.catalog.data.validation.exceptions;

import org.springframework.http.HttpStatus;

public class TagException extends HttpStatusException {
    public TagException(Throwable cause, HttpStatus status) {
        super(cause.getClass().getSimpleName() + " -> " + cause.getMessage());
        this.httpStatus = status;
    }

    public TagException(Throwable cause) {
        this(cause, HttpStatus.BAD_REQUEST);
    }

    public TagException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public TagException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    @Override
    public String getMessage() {
        return getClass().getSimpleName() + " -> " + super.getMessage();
    }
}

