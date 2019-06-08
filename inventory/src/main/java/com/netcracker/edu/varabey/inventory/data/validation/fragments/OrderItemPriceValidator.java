package com.netcracker.edu.varabey.inventory.data.validation.fragments;


import com.netcracker.edu.varabey.inventory.data.entity.Price;
import com.netcracker.edu.varabey.inventory.data.validation.exceptions.OrderItemException;
import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Validator;

@Validator
public class OrderItemPriceValidator implements PriceValidator {
    @Override
    public <P extends Price> void check(P price) {
        try {
            PriceValidator.super.check(price);
        } catch (Exception e) {
            throw new OrderItemException(e);
        }
    }
}
