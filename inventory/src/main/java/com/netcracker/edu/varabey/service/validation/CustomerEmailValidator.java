package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.service.validation.exceptions.InvalidCustomerException;
import com.netcracker.edu.varabey.spring.Validator;

@Validator
public class CustomerEmailValidator implements EmailValidator {
    @Override
    public void check(String email) {
        if (email == null) {
            throw new InvalidCustomerException("Email must not be null");
        }
        if (!email.matches("[a-z]([._]?[a-z]+)*[@][a-z][a-z]*[.]((org)|(net)|(ru)|(com)|(by))")) {
            throw new InvalidCustomerException("Email + " + email + " is invalid");
        }
    }
}
