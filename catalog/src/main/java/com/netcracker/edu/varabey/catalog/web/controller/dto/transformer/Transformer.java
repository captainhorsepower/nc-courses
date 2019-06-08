package com.netcracker.edu.varabey.catalog.web.controller.dto.transformer;

public interface Transformer<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
}
