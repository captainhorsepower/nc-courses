package com.netcracker.edu.varabey.service.validation;

import java.time.LocalDateTime;

public interface DateTimeValidator {
    LocalDateTime getEarliestAllowedDateTime();
    LocalDateTime getLatestAllowedDateTime();

    default void check(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("date-time must not be null");
        }
        if (dateTime.isBefore(getEarliestAllowedDateTime()) || dateTime.isAfter(getLatestAllowedDateTime())) {
            throw new IllegalArgumentException("date-time " + dateTime + " must be within bounds " +
                    "[" + getEarliestAllowedDateTime() + ", " + getLatestAllowedDateTime() + "]");
        }
    }

}
