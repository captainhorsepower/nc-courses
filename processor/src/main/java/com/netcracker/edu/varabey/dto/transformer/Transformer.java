package com.netcracker.edu.varabey.dto.transformer;

public interface Transformer<T, U> {
    U convert(T input);
}
