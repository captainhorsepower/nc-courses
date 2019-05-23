package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.service.validation.exceptions.CustomerException;
import com.netcracker.edu.varabey.util.custom.beanannotation.Validator;
import lombok.Getter;

@Getter
@Validator
public class CustomerNameValidator implements NameValidator {
    private final String forbiddenSymbols;
    private final Integer minNameLength;

    public CustomerNameValidator(String forbiddenSymbolsCustomer, Integer minNameLengthCustomer) {
        this.forbiddenSymbols = forbiddenSymbolsCustomer;
        this.minNameLength = minNameLengthCustomer;
    }

    @Override
    public void check(String name) {
        try {
            NameValidator.super.check(name);
        } catch (Exception e) {
            throw new CustomerException(e);
        }
    }
}
