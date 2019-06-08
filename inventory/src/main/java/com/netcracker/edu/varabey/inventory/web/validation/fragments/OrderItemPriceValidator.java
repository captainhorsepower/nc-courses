package com.netcracker.edu.varabey.inventory.web.validation.fragments;


import com.netcracker.edu.varabey.inventory.data.entity.Price;
import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.inventory.web.validation.exceptions.OrderItemException;

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
