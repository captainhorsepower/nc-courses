package com.netcracker.courses.oop.console.utils;

import java.util.List;

public interface ConsolePrinter {
    <T> void print(T sth);
    <T> void printAll(List<T> allSth);
    <T> void printSelectionList(List<T> allSth);
    String getInputOneItemMessage(String itemName);
    String getInvalidInputItemMessage(String itemName);
}
