package com.netcracker.edu.varabey.catalog.validation.util;


import com.netcracker.edu.varabey.catalog.entity.Price;
import com.netcracker.edu.varabey.catalog.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.catalog.validation.exceptions.OfferException;

@Validator
public class OfferPriceValidator implements PriceValidator {
    @Override
    public <P extends Price> void check(P price) {
        try {
            PriceValidator.super.check(price);
        } catch (Exception e) {
            throw new OfferException(e);
        }
    }
}
