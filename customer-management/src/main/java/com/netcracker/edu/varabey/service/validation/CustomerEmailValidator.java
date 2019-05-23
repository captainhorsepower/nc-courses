package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.service.validation.exceptions.CustomerException;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidEmailException;
import com.netcracker.edu.varabey.util.custom.beanannotation.Validator;
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
