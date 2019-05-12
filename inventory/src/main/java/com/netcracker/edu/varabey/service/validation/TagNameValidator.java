package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.spring.Validator;
import lombok.Getter;

@Getter
@Validator
public class TagNameValidator implements NameValidator {
    private final String forbiddenSymbols;
    private final int minNameLength;

    public TagNameValidator(String forbiddenSymbolsTag, int minNameLengthTag) {
        this.forbiddenSymbols = forbiddenSymbolsTag;
        this.minNameLength = minNameLengthTag;
    }
}
