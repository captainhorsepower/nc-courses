package com.netcracker.edu.varabey.catalog.data.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {
    public static final Integer DEFAULT_MIN_NAME_LENGTH = 3;
    public static final String DEFAULT_FORBIDDEN_SYMBOLS = "=<>?\"\'";

    @Bean
    public Integer minNameLength() {
        return DEFAULT_MIN_NAME_LENGTH;
    }

    @Bean
    public String forbiddenSymbols() {
        return DEFAULT_FORBIDDEN_SYMBOLS;
    }
}
