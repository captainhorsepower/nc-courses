package com.netcracker.edu.varabey.inventory.web.validation.fragments;

import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.inventory.web.validation.exceptions.OrderException;
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

    @Override
    public void check(LocalDateTime dateTime) {
        try {
            DateTimeValidator.super.check(dateTime);
        } catch (Exception e) {
            throw new OrderException(e);
        }
    }
}
