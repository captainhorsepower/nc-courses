package com.netcracker.edu.varabey.service.validation.util;

import com.netcracker.edu.varabey.controller.dto.util.PriceRange;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidPriceException;

public interface PriceRangeValidator {
    default void check(Double low, Double high) {
        check(new PriceRange(low, high));
    }
    default void check(PriceRange priceRange) {
        if (priceRange == null || priceRange.getMinPrice() == null || priceRange.getMaxPrice() == null) {
            throw new InvalidPriceException(priceRange + " is invalid");
        }
    }
}
