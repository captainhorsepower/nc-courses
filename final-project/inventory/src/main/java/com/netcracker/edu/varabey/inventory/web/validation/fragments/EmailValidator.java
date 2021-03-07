package com.netcracker.edu.varabey.inventory.web.validation.fragments;

import com.netcracker.edu.varabey.inventory.web.validation.exceptions.InvalidEmailException;

import java.util.regex.Pattern;

public interface EmailValidator {
    Pattern getEmailPattern();
    default void check(String email) {
        final boolean matchesEmail = !getEmailPattern().matcher(email).matches();
        if (matchesEmail) {
            throw new InvalidEmailException("Email + \'" + email + "\' is invalid");
        }
    }
}
