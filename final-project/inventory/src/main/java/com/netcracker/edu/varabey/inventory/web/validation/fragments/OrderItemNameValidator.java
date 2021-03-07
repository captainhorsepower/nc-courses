package com.netcracker.edu.varabey.inventory.web.validation.fragments;

import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.inventory.web.validation.exceptions.OrderItemException;
import lombok.Getter;

@Getter
@Validator
public class OrderItemNameValidator implements NameValidator {
    private final String forbiddenSymbols;
    private final Integer minNameLength;

    public OrderItemNameValidator(String forbiddenSymbols, Integer minNameLength) {
        this.forbiddenSymbols = forbiddenSymbols;
        this.minNameLength = minNameLength;
    }

    @Override
    public void check(String name) {
        try {
            NameValidator.super.check(name);
        } catch (Exception e) {
            throw new OrderItemException(e);
        }
    }
}
