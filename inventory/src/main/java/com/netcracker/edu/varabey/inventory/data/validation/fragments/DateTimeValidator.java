package com.netcracker.edu.varabey.inventory.data.validation.fragments;

import com.netcracker.edu.varabey.inventory.data.validation.exceptions.InvalidDateTimeException;

import java.time.LocalDateTime;

public interface DateTimeValidator {
    LocalDateTime getEarliestAllowedDateTime();
    LocalDateTime getLatestAllowedDateTime();

    default void check(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new InvalidDateTimeException("date-time must not be null");
        }
        if (dateTime.isBefore(getEarliestAllowedDateTime()) || dateTime.isAfter(getLatestAllowedDateTime())) {
            throw new InvalidDateTimeException("date-time " + dateTime + " must be within bounds " +
                    "[" + getEarliestAllowedDateTime() + ", " + getLatestAllowedDateTime() + "]");
        }
    }

}
