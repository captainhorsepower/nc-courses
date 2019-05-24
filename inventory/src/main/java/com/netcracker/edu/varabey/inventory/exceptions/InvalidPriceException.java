package com.netcracker.edu.varabey.inventory.exceptions;

public class InvalidPriceException extends IllegalArgumentException {
    public InvalidPriceException(String message) {
        super(message);
    }
}
