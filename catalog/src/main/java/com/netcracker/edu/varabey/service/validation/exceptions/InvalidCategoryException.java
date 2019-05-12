package com.netcracker.edu.varabey.service.validation.exceptions;

public class InvalidCategoryException extends IllegalArgumentException {
    public InvalidCategoryException(String message) {
        super(message);
    }

    public InvalidCategoryException() {
        this("Illegal Category!");
    }
}
