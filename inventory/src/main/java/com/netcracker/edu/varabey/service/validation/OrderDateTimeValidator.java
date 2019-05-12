package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.spring.Validator;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Validator
public class OrderDateTimeValidator implements DateTimeValidator {
    private final LocalDateTime earliestAllowedDateTime;
    private final LocalDateTime latestAllowedDateTime;

    public OrderDateTimeValidator(LocalDateTime earliestAllowedDateTime, LocalDateTime latestAllowedDateTime) {
        this.earliestAllowedDateTime = earliestAllowedDateTime;
        this.latestAllowedDateTime = latestAllowedDateTime;
    }

    @Override
    public LocalDateTime getLatestAllowedDateTime() {
        return LocalDateTime.now();
    }
}
