package com.netcracker.edu.varabey.catalog.data.validation;

import com.netcracker.edu.varabey.catalog.data.entity.Offer;
import com.netcracker.edu.varabey.catalog.data.entity.Price;

public interface OfferValidator extends ServiceValidator<Offer, Long> {
    void checkName(String name);
    void checkPrice(Price price);
    void checkForPersist(Offer offer);
}
