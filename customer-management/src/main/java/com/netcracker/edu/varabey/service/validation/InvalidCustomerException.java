package com.netcracker.edu.varabey.service.validation;

public class InvalidCustomerException extends IllegalArgumentException {
    public InvalidCustomerException() {
        this("Invalid Customer passed as argument to ");
    }

    public InvalidCustomerException(String message) {
        super(message);
    }
}
