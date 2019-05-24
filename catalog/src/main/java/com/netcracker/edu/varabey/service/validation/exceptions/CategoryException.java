package com.netcracker.edu.varabey.service.validation.exceptions;

import org.springframework.http.HttpStatus;

public class CategoryException extends HttpStatusException {

    public CategoryException(Throwable cause, HttpStatus status) {
        super(cause.getClass().getSimpleName() + " -> " + cause.getMessage());
        this.httpStatus = status;
    }

    public CategoryException(Throwable cause) {
        this(cause, HttpStatus.BAD_REQUEST);
    }

    public CategoryException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public CategoryException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    @Override
    public String getMessage() {
        return getClass().getSimpleName() + " -> " + super.getMessage();
    }
}

