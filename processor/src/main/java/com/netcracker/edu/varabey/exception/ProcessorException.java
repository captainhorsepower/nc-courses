package com.netcracker.edu.varabey.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProcessorException extends RuntimeException {
    private HttpStatus status;

    public ProcessorException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
