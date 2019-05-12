package com.netcracker.edu.varabey.service.validation.exceptions;

public class InvalidTagException extends IllegalArgumentException {
    public InvalidTagException(String message) {
        super(message);
    }

    public InvalidTagException() {
        this("provided tag is invalid");
    }
}
