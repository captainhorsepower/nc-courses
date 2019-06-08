package com.netcracker.edu.varabey.inventory.data.validation.exceptions;

import org.springframework.http.HttpStatus;

public class CustomerException extends HttpStatusException {
    public CustomerException(Throwable cause, HttpStatus status) {
        super("Customer has problem: " + cause.getMessage(), cause, status);
    }

    public CustomerException(Throwable cause) {
        this(cause, HttpStatus.BAD_REQUEST);
    }

    public CustomerException(String message, HttpStatus status) {
        super(message, status);
    }

    public CustomerException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}
