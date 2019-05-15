package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.service.validation.exceptions.InvalidCustomerException;
import com.netcracker.edu.varabey.spring.Validator;
import lombok.Getter;

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
        if (age < getMinAllowedAge() || age > getMaxAllowedAge()) {
            throw new InvalidCustomerException("age must be within bounds [" + getMinAllowedAge() + ", " + getMaxAllowedAge() + "].");
        }
    }
}
