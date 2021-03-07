package com.netcracker.edu.varabey.inventory.web.controller.dto.transformer;

public interface Transformer<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
}
