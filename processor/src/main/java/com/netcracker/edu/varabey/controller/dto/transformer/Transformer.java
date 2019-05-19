package com.netcracker.edu.varabey.controller.dto.transformer;

public interface Transformer<T, U> {
    U convert(T input);
}
