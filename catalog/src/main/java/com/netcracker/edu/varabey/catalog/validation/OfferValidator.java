package com.netcracker.edu.varabey.catalog.validation;

import com.netcracker.edu.varabey.catalog.entity.Offer;
import com.netcracker.edu.varabey.catalog.entity.Price;

public interface OfferValidator extends ServiceValidator<Offer, Long> {
    void checkName(String name);
    void checkPrice(Price price);
    void checkForPersist(Offer offer);
}
