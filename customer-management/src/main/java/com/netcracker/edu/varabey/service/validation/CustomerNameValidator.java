package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.service.validation.exceptions.InvalidCustomerException;
import com.netcracker.edu.varabey.spring.Validator;
import lombok.Data;
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
        if (name == null || name.length() < getMinNameLength()) {
            throw new InvalidCustomerException("Name \"" + name + "\" is too short.");
        }
        if (name.matches(".*[" + getForbiddenSymbols() + "].*")) {
            throw new InvalidCustomerException(name + "must not contain symbols \"" + getForbiddenSymbols() + "\"");
        }
    }
}
