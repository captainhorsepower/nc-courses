package com.netcracker.edu.varabey.controller.util;

import javax.persistence.EntityNotFoundException;

public class RestPreconditions {
    public static <T> T checkFound(T resource) {
        if (resource == null) {
            throw new EntityNotFoundException("Resource not found.");
        }
        return resource;
    }
}