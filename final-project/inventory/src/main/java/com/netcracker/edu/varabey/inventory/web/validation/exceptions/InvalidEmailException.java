package com.netcracker.edu.varabey.inventory.web.validation.exceptions;

public class InvalidEmailException extends IllegalArgumentException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
