package com.netcracker.edu.varabey.service.validation.exceptions;

public class InvalidOrderException extends IllegalArgumentException {
    public InvalidOrderException() {
    }

    public InvalidOrderException(String s) {
        super(s);
    }

    public InvalidOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOrderException(Throwable cause) {
        super(cause);
    }
}
