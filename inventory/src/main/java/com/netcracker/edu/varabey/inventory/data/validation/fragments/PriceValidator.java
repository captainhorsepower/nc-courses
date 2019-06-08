package com.netcracker.edu.varabey.inventory.data.validation.fragments;

import com.netcracker.edu.varabey.inventory.data.entity.Price;
import com.netcracker.edu.varabey.inventory.data.validation.exceptions.InvalidPriceException;

public interface PriceValidator {
    default <P extends Price> void check(P price) {
        if (price == null || price.getValue() < 0.) {
            throw new InvalidPriceException("price " + price + " must be >= 0.00");
        }
    }
}
