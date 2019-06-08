package com.netcracker.edu.varabey.catalog.data.validation.util;


import com.netcracker.edu.varabey.catalog.data.entity.Price;
import com.netcracker.edu.varabey.catalog.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.OfferException;

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
