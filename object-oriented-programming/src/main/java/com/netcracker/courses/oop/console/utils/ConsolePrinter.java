package com.netcracker.courses.oop.console.utils;

import java.util.List;

/**
 * for console View interface.
 * Helps to separate data from it's presentation
 */
public interface ConsolePrinter {

    <T> void print(T sth);
    <T> void printAll(List<T> allSth);
    <T> void printSelectionList(List<T> allSth);

    default void printMessage(String message) {
        throw new UnsupportedOperationException();
    }
}
