package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.service.validation.exceptions.CustomerException;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidAgeException;
import com.netcracker.edu.varabey.util.custom.beanannotation.Validator;
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
