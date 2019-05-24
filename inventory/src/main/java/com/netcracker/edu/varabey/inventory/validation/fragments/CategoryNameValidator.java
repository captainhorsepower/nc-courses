package com.netcracker.edu.varabey.inventory.validation.fragments;

import com.netcracker.edu.varabey.inventory.exceptions.CategoryException;
import com.netcracker.edu.varabey.inventory.exceptions.InvalidNameException;
import com.netcracker.edu.varabey.inventory.util.aop.beanannotation.Validator;
import lombok.Getter;

@Getter
@Validator
public class CategoryNameValidator implements NameValidator {
    private final String forbiddenSymbols;
    private final Integer minNameLength;

    public CategoryNameValidator(String forbiddenSymbolsCategory, Integer minNameLengthCategory) {
        this.forbiddenSymbols = forbiddenSymbolsCategory;
        this.minNameLength = minNameLengthCategory;
    }

    @Override
    public void check(String name) {
        try {
            NameValidator.super.check(name);
        } catch (InvalidNameException e) {
            throw new CategoryException(e);
        }
    }
}
