package com.netcracker.edu.varabey.customers.controller.dto.transformer;

public interface Transformer<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
}
