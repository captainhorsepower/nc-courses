package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.entity.Price;

public interface PriceValidator {
    default <P extends Price> void check(P price) {
        if (price.getValue() < 0.) {
            throw new IllegalArgumentException("price " + price + " must be >= 0.00");
        }
    }
}
