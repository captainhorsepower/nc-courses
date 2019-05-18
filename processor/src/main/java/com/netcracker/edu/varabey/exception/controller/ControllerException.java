package com.netcracker.edu.varabey.exception.controller;

import com.netcracker.edu.varabey.exception.ProcessorException;
import org.springframework.http.HttpStatus;

public class ControllerException extends ProcessorException {
    public ControllerException(String message, HttpStatus status) {
        super(message, status);
    }

    public ControllerException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
