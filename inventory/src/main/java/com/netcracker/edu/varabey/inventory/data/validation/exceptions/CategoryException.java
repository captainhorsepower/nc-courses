package com.netcracker.edu.varabey.inventory.data.validation.exceptions;

import org.springframework.http.HttpStatus;

public class CategoryException extends HttpStatusException {
    public CategoryException(Throwable cause, HttpStatus status) {
        super("Category has problem: " + cause.getMessage(), cause, status);
    }

    public CategoryException(Throwable cause) {
        this(cause, HttpStatus.BAD_REQUEST);
    }

    public CategoryException(String message, HttpStatus status) {
        super(message, status);
    }

    public CategoryException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}

