package com.netcracker.edu.varabey.customers.validation.util;

import com.netcracker.edu.varabey.customers.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.customers.validation.exceptions.CustomerException;
import com.netcracker.edu.varabey.customers.validation.exceptions.InvalidEmailException;
import lombok.Getter;

import java.util.regex.Pattern;

@Validator
public class CustomerEmailValidator implements EmailValidator {
    @Getter
    private Pattern emailPattern;

    public CustomerEmailValidator(Pattern emailPattern) {
        this.emailPattern = emailPattern;
    }

    @Override
    public void check(String email) {
        try {
            EmailValidator.super.check(email);
        } catch (InvalidEmailException e) {
            throw new CustomerException(e);
        }
    }
}
