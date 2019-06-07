package com.netcracker.edu.varabey.processor.controller.dto.transformer;

public interface Transformer<T, U> {
    U convert(T input);
}
