package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.service.validation.exceptions.InvalidCustomerException;
import com.netcracker.edu.varabey.spring.Validator;

@Validator
public class CustomerEmailValidator implements EmailValidator {
    @Override
    public void check(String email) {
        if (email == null || !email.matches("[\\w]([._]?[\\w]+)*[@][\\w][\\w]*[.]((org)|(net)|(ru)|(com)|(by))")) {
            throw new InvalidCustomerException("Email \"" + email + "\" is invalid");
        }
    }
}
