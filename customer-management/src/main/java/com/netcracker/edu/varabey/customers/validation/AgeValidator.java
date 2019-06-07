package com.netcracker.edu.varabey.customers.validation;

import com.netcracker.edu.varabey.customers.validation.exceptions.InvalidAgeException;

public interface AgeValidator {
    Integer getMinAllowedAge();
    Integer getMaxAllowedAge();

    default void check(Integer age) {
        if (age == null || age < getMinAllowedAge() || age > getMaxAllowedAge()) {
            throw new InvalidAgeException("age=" + age + " must be within bounds [" + getMinAllowedAge() + ", " + getMaxAllowedAge() + "].");
        }
    }
}
