package com.netcracker.edu.varabey.customers.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
public class ValidationConfig {
    public static final Integer DEFAULT_MIN_NAME_LENGTH = 3;
    public static final String DEFAULT_FORBIDDEN_SYMBOLS = "=<>?\";";
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

    @Bean
    public Pattern emailPattern() {
        final int minUserNameLength = 3;
        return Pattern.compile("([a-z0-9]([._\\-]?[a-z0-9]+)*){" + minUserNameLength + "}[@][a-z][a-z]*[.]((org)|(net)|(ru)|(com)|(by))", Pattern.CASE_INSENSITIVE);
    }
}
