package com.netcracker.edu.varabey.service.validation;

public interface EmailValidator {
    default void check(String email) {
        if (!email.matches("[a-z]([._]?[a-z]+)*[@][a-z][a-z]*[.]((org)|(net)|(ru)|(com)|(by))")) {
            throw new IllegalArgumentException("Email + " + email + " is invalid");
        }
    }
}
