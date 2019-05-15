package com.netcracker.edu.varabey.service.validation;

public interface AgeValidator {
    Integer getMinAllowedAge();
    Integer getMaxAllowedAge();

    default void check(Integer age) {
        if (age < getMinAllowedAge() || age > getMaxAllowedAge()) {
            throw new IllegalArgumentException("age must be within bounds [" + getMinAllowedAge() + ", " + getMaxAllowedAge() + "].");
        }
    }
}
