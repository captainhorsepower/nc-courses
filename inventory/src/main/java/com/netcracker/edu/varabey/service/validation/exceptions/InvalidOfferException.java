package com.netcracker.edu.varabey.service.validation.exceptions;

public class InvalidOfferException extends IllegalArgumentException {
    public InvalidOfferException(String message) {
        super(message);
    }

    public InvalidOfferException() {
        super();
    }
}
