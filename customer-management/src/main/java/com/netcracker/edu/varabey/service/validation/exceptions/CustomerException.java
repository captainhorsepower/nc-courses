package com.netcracker.edu.varabey.service.validation.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class CustomerException extends RuntimeException {
    @Getter
    @Setter
    private HttpStatus httpStatus;

    public CustomerException(Throwable cause, HttpStatus status) {
        super(cause.getClass().getSimpleName() + " -> " + cause.getMessage());
        this.httpStatus = status;
    }

    public CustomerException(Throwable cause) {
        this(cause, HttpStatus.BAD_REQUEST);
    }

    public CustomerException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public CustomerException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    @Override
    public String getMessage() {
        return getClass().getSimpleName() + " -> " + super.getMessage();
    }
}
