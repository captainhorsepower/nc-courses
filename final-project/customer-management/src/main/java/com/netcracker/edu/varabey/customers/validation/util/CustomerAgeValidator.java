package com.netcracker.edu.varabey.customers.validation.util;

import com.netcracker.edu.varabey.customers.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.customers.validation.exceptions.CustomerException;
import com.netcracker.edu.varabey.customers.validation.exceptions.InvalidAgeException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Validator
public class CustomerAgeValidator implements AgeValidator {
    private final Integer minAllowedAge;
    private final Integer maxAllowedAge;

    public CustomerAgeValidator(Integer minAllowedAge, Integer maxAllowedAge) {
        this.minAllowedAge = minAllowedAge;
        this.maxAllowedAge = maxAllowedAge;
    }

    @Override
    public void check(Integer age) {
        try {
            AgeValidator.super.check(age);
        } catch (InvalidAgeException e) {
            throw new CustomerException(e, HttpStatus.BAD_REQUEST);
        }
    }
}
