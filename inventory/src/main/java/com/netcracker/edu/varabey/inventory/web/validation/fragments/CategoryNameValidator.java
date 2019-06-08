package com.netcracker.edu.varabey.inventory.web.validation.fragments;

import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.inventory.web.validation.exceptions.CategoryException;
import com.netcracker.edu.varabey.inventory.web.validation.exceptions.InvalidNameException;
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
