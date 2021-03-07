package com.netcracker.edu.varabey.catalog.data.validation.util;

import com.netcracker.edu.varabey.catalog.web.controller.dto.util.PriceRange;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.InvalidPriceException;

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
