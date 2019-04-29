package com.netcracker.edu.varabey.service.validation;

public class InvalidCustomerException extends IllegalArgumentException {
    public InvalidCustomerException(String message) {
        super(message);
    }

    public InvalidCustomerException() {
        this("customer is invalid");
    }
}
