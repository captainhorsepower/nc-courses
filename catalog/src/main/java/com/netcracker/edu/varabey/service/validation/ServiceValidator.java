package com.netcracker.edu.varabey.service.validation;

import javax.persistence.EntityNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ServiceValidator<T, I> {
    I extractId(T resource);

    default void checkIdIsNull(I id) {
        if (id != null) {
            throw new IllegalArgumentException("id must be null.");
        }
    }

    default void checkIdIsNotNull(I id) {
        if (id == null) {
            throw new IllegalArgumentException("id must be NOT null");
        }
    }

    default void checkNotNull(T resource) {
        checkNotNull(resource, "resource must NOT be null");
    }

    default void checkNotNull(T resource, String errorMessage) {
        if (resource == null) {
            throw new EntityNotFoundException(errorMessage);
        }
    }

    default void checkFoundById(T resource, I id) {

        /* for classes with names like OfferValidator it will work fine
         * for any other case, just overload default method. */
        String lameGenericClassName = this.getClass().getSimpleName();
        Matcher m = Pattern.compile("[A-Z][a-z]+").matcher(lameGenericClassName);
        if(m.find()) {
            lameGenericClassName = m.group();
        }
        checkNotNull(resource, lameGenericClassName + " with id=" + id + " is not found.");
    }

    void checkProperties(T resource);

    default void checkForPersist(T resource) {
        checkNotNull(resource);
        checkIdIsNull(extractId(resource));
        checkProperties(resource);
    }

    default void checkForUpdate(T resource) {
        checkNotNull(resource);
        checkIdIsNotNull(extractId(resource));
        checkProperties(resource);
    }
}
