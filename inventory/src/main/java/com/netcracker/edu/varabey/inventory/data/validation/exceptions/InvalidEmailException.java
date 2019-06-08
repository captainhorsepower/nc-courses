package com.netcracker.edu.varabey.inventory.data.validation.exceptions;

public class InvalidEmailException extends IllegalArgumentException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
