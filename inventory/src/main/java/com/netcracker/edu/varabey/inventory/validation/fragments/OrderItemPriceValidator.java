package com.netcracker.edu.varabey.inventory.validation.fragments;


import com.netcracker.edu.varabey.inventory.data.entity.Price;
import com.netcracker.edu.varabey.inventory.exceptions.OrderItemException;
import com.netcracker.edu.varabey.inventory.util.aop.beanannotation.Validator;

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
