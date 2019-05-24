package com.netcracker.edu.varabey.inventory.validation;

public interface ServiceValidator<T, I> {
    void checkNotNull(T resource);

    void checkIdIsNull(I id);

    void checkIdIsNotNull(I id);

    T checkFoundById(T resource, I id);

    T checkFound(T resource, String notFoundMessage);

    void checkAllProperties(T resource);
}
