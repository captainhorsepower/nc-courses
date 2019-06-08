package com.netcracker.edu.varabey.inventory.web.validation.exceptions;

import org.springframework.http.HttpStatus;

public class TagException extends HttpStatusException {
    public TagException(Throwable cause, HttpStatus status) {
        super("Tag has problem: " + cause.getMessage(), cause, status);
    }

    public TagException(Throwable cause) {
        this(cause, HttpStatus.BAD_REQUEST);
    }

    public TagException(String message, HttpStatus status) {   
        super(message, status);
    }

    public TagException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}

