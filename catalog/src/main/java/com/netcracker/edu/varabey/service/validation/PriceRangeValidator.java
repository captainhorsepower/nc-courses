package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.controller.dto.util.PriceRange;

public interface PriceRangeValidator {
    default void check(Double low, Double high) {
        check(new PriceRange(low, high));
    }
    default void check(PriceRange priceRange) {
        if (priceRange == null || priceRange.getMinPrice() == null || priceRange.getMaxPrice() == null) {
            throw new IllegalArgumentException(priceRange + " is invalid");
        }
    }
}
