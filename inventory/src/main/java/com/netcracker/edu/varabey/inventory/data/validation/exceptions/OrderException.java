package com.netcracker.edu.varabey.inventory.data.validation.exceptions;

import org.springframework.http.HttpStatus;

public class OrderException extends HttpStatusException {
    public OrderException(Throwable cause, HttpStatus status) {
        super("Order has problem: " + cause.getMessage(), cause, status);
    }

    public OrderException(Throwable cause) {
        this(cause, HttpStatus.BAD_REQUEST);
    }

    public OrderException(String message, HttpStatus status) {
        super(message, status);
    }

    public OrderException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}
