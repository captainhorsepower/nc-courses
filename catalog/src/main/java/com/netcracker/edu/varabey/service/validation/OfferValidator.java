package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Price;

public interface OfferValidator extends ServiceValidator<Offer, Long> {
    void checkName(String name);
    void checkPrice(Price price);
    void checkForPersist(Offer offer);
}
