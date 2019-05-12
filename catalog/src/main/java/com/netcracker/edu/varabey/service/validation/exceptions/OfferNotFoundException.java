package com.netcracker.edu.varabey.service.validation.exceptions;

import javax.persistence.EntityNotFoundException;

public class OfferNotFoundException extends EntityNotFoundException {
    public OfferNotFoundException(String message) {
        super(message);
    }

    public OfferNotFoundException() {
        this("");
    }
}

