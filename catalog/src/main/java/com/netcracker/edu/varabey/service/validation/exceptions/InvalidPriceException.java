package com.netcracker.edu.varabey.service.validation.exceptions;

public class InvalidPriceException extends IllegalArgumentException {
    public InvalidPriceException(String message) {
        super(message);
    }
}
