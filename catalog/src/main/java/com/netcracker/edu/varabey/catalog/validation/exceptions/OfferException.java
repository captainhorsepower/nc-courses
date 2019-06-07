package com.netcracker.edu.varabey.catalog.validation.exceptions;

import org.springframework.http.HttpStatus;

public class OfferException extends HttpStatusException {
    public OfferException(Throwable cause, HttpStatus status) {
        super(cause.getClass().getSimpleName() + " -> " + cause.getMessage());
        this.httpStatus = status;
    }

    public OfferException(Throwable cause) {
        this(cause, HttpStatus.BAD_REQUEST);
    }

    public OfferException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public OfferException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    @Override
    public String getMessage() {
        return getClass().getSimpleName() + " -> " + super.getMessage();
    }
}

