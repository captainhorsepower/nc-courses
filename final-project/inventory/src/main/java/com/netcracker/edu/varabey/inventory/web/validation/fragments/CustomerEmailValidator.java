package com.netcracker.edu.varabey.inventory.web.validation.fragments;

import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.inventory.web.validation.exceptions.CustomerException;
import com.netcracker.edu.varabey.inventory.web.validation.exceptions.InvalidEmailException;
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
