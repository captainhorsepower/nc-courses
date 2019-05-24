package com.netcracker.edu.varabey.service.validation.util;

import com.netcracker.edu.varabey.entity.Price;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidPriceException;

public interface PriceValidator {
    default <P extends Price> void check(P price) {
        if (price.getValue() < 0.) {
            throw new InvalidPriceException("price " + price + " must be >= 0.00");
        }
    }
}
