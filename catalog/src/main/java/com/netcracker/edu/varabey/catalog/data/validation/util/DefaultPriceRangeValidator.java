package com.netcracker.edu.varabey.catalog.data.validation.util;


import com.netcracker.edu.varabey.catalog.web.controller.dto.util.PriceRange;
import com.netcracker.edu.varabey.catalog.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.OfferException;

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
