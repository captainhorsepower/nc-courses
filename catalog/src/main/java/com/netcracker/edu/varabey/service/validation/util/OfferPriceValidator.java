package com.netcracker.edu.varabey.service.validation.util;


import com.netcracker.edu.varabey.entity.Price;
import com.netcracker.edu.varabey.service.validation.exceptions.OfferException;
import com.netcracker.edu.varabey.util.custom.beanannotation.Validator;

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
