package com.netcracker.edu.varabey.service.validation.util;


import com.netcracker.edu.varabey.controller.dto.util.PriceRange;
import com.netcracker.edu.varabey.service.validation.exceptions.OfferException;
import com.netcracker.edu.varabey.util.custom.beanannotation.Validator;

@Validator
public class DefaultPriceRangeValidator implements PriceRangeValidator {
    @Override
    public void check(Double low, Double high) {
        try {
            PriceRangeValidator.super.check(low, high);
        } catch (Exception e) {
            throw new OfferException(e);
        }
    }

    @Override
    public void check(PriceRange priceRange) {
        try {
            PriceRangeValidator.super.check(priceRange);
        } catch (Exception e) {
            throw new OfferException(e);
        }
    }
}
