package com.netcracker.edu.varabey.service.validation.exceptions;

public class InvalidEmailException extends IllegalArgumentException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
