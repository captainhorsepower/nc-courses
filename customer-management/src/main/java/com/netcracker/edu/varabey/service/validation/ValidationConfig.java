package com.netcracker.edu.varabey.service.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {
    public static final Integer DEFAULT_MIN_NAME_LENGTH = 3;
    public static final String DEFAULT_FORBIDDEN_SYMBOLS = "=<>?\"\'";
    public static final Integer DEFAULT_MIN_ALLOWED_AGE = 14;
    public static final Integer DEFAULT_MAX_ALLOWED_AGE = 150;


    @Bean
    public Integer minNameLength() {
        return DEFAULT_MIN_NAME_LENGTH;
    }

    @Bean
    public Integer minNameLengthCustomer() {
        return DEFAULT_MIN_NAME_LENGTH;
    }

    @Bean
    public String forbiddenSymbols() {
        return DEFAULT_FORBIDDEN_SYMBOLS;
    }

    @Bean
    public Integer minAllowedAge() {
        return DEFAULT_MIN_ALLOWED_AGE;
    }

    @Bean
    public Integer maxAllowedAge() {
        return DEFAULT_MAX_ALLOWED_AGE;
    }
}
