package com.netcracker.edu.varabey.catalog.data.validation.util;

import com.netcracker.edu.varabey.catalog.data.entity.Price;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.InvalidPriceException;

public interface PriceValidator {
    default <P extends Price> void check(P price) {
        if (price.getValue() < 0.) {
            throw new InvalidPriceException("price " + price + " must be >= 0.00");
        }
    }
}
