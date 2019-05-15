package com.netcracker.edu.varabey.service.validation;

public interface NameValidator {
    String getForbiddenSymbols();
    Integer getMinNameLength();
    default void check(String name) {
        if (name == null || name.length() < getMinNameLength()) {
            throw new IllegalArgumentException("Name \"" + name + "\" is too short.");
        }
        if (name.matches(".*[" + getForbiddenSymbols() + "].*")) {
            throw new IllegalArgumentException(name + "must not contain symbols \"" + getForbiddenSymbols() + "\"");
        }
    }
}
