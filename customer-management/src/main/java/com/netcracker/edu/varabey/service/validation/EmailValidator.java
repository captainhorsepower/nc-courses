package com.netcracker.edu.varabey.service.validation;

import java.util.regex.Pattern;

public interface EmailValidator {
    default void check(String email) {
        if (!Pattern
                .compile("[a-z]([._\\-]?[a-z]+)*[@][a-z][a-z]*[.]((org)|(net)|(ru)|(com)|(by))", Pattern.UNICODE_CASE)
                .matcher(email)
                .matches()
    ) {
            throw new IllegalArgumentException("Email + " + email + " is invalid");
        }
    }
}
