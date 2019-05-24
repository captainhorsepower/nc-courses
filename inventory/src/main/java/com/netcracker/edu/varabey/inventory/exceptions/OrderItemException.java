package com.netcracker.edu.varabey.inventory.exceptions;

import org.springframework.http.HttpStatus;

public class OrderItemException extends HttpStatusException {
    public OrderItemException(Throwable cause, HttpStatus status) {
        super("OrderItem has problem: " + cause.getMessage(), cause, status);
    }

    public OrderItemException(Throwable cause) {
        this(cause, HttpStatus.BAD_REQUEST);
    }

    public OrderItemException(String message, HttpStatus status) {
        super(message, status);
    }

    public OrderItemException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}

