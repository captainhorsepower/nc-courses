package com.netcracker.edu.varabey.service.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class ValidationConfig {
    public static final Integer DEFAULT_MIN_NAME_LENGTH = 3;
    public static final String DEFAULT_FORBIDDEN_SYMBOLS = "=<>?\"\'";
    public static final LocalDateTime DEFAULT_EARLIEST_ALLOWED_DATE_TIME = LocalDateTime.of(2019, 1, 1, 0, 0);
    public static final LocalDateTime DEFAULT_LATEST_ALLOWED_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);

    @Bean
    public Integer minNameLength() {
        return DEFAULT_MIN_NAME_LENGTH;
    }

    @Bean
    public String forbiddenSymbols() {
        return DEFAULT_FORBIDDEN_SYMBOLS;
    }

    @Bean
    public LocalDateTime earliestAllowedDateTime() {
        return DEFAULT_EARLIEST_ALLOWED_DATE_TIME;
    }

    @Bean
    public LocalDateTime latestAllowedDateTime() {
        return DEFAULT_LATEST_ALLOWED_DATE_TIME;
    }
}
