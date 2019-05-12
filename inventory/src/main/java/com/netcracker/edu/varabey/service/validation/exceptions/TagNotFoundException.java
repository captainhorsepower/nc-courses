package com.netcracker.edu.varabey.service.validation.exceptions;

import javax.persistence.EntityNotFoundException;

public class TagNotFoundException extends EntityNotFoundException {
    public TagNotFoundException(String message) {
        super(message);
    }

    public TagNotFoundException() {
        this("tag with given id wasn't found");
    }
}
