package com.netcracker.edu.varabey.controller.dto.transformator;

public interface Transformer<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
}
