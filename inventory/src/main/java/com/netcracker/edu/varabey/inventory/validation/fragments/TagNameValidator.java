package com.netcracker.edu.varabey.inventory.validation.fragments;

import com.netcracker.edu.varabey.inventory.exceptions.TagException;
import com.netcracker.edu.varabey.inventory.util.aop.beanannotation.Validator;
import lombok.Getter;

@Getter
@Validator
public class TagNameValidator implements NameValidator {
    private final String forbiddenSymbols;
    private final Integer minNameLength;

    public TagNameValidator(String forbiddenSymbols, Integer minNameLength) {
        this.forbiddenSymbols = forbiddenSymbols;
        this.minNameLength = minNameLength;
    }

    @Override
    public void check(String name) {
        try {
            NameValidator.super.check(name);
        } catch (Exception e) {
            throw new TagException(e);
        }
    }
}
