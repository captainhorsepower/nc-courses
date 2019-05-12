package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.spring.Validator;
import lombok.Getter;

@Getter
@Validator
public class CategoryNameValidator implements NameValidator {
    private final String forbiddenSymbols;
    private final int minNameLength;

    public CategoryNameValidator(String forbiddenSymbolsCategory, int minNameLengthCategory) {
        this.forbiddenSymbols = forbiddenSymbolsCategory;
        this.minNameLength = minNameLengthCategory;
    }
}
