package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.spring.Validator;
import lombok.Getter;

@Getter
@Validator
public class OrderItemNameValidator implements NameValidator {
    private final String forbiddenSymbols;
    private final int minNameLength;

    public OrderItemNameValidator(String forbiddenSymbols, int minNameLength) {
        this.forbiddenSymbols = forbiddenSymbols;
        this.minNameLength = minNameLength;
    }
}
