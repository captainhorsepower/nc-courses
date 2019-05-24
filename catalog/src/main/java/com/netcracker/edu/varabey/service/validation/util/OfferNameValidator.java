package com.netcracker.edu.varabey.service.validation.util;

import com.netcracker.edu.varabey.service.validation.exceptions.OfferException;
import com.netcracker.edu.varabey.util.custom.beanannotation.Validator;
import lombok.Getter;

@Getter
@Validator
public class OfferNameValidator implements NameValidator {
    private final String forbiddenSymbols;
    private final Integer minNameLength;

    public OfferNameValidator(String forbiddenSymbols, Integer minNameLength) {
        this.forbiddenSymbols = forbiddenSymbols;
        this.minNameLength = minNameLength;
    }

    @Override
    public void check(String name) {
        try {
            NameValidator.super.check(name);
        } catch (Exception e) {
            throw new OfferException(e);
        }
    }
}
