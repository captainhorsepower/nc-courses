package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.service.validation.exceptions.InvalidNameException;

import java.util.Arrays;

public interface NameValidator {
    String getForbiddenSymbols();
    Integer getMinNameLength();
    default void check(String name) {
        if (name == null || name.length() < getMinNameLength()) {
            throw new InvalidNameException("Name \"" + name + "\" is too short. Min allowed length = " + getMinNameLength());
        }
        if (name.matches(".*[" + getForbiddenSymbols() + "].*")) {
            throw new InvalidNameException(name + "must not contain symbols \"" + Arrays.toString(getForbiddenSymbols().split(" ")) + "\"");
        }
    }
}
