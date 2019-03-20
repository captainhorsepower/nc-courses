package com.netcracker.courses.oop.console.utils;

import java.util.List;

public interface ConsolePrinter {

    String DEFAULT_CHOOSE_ITEM_MESSAGE = "choose ";
    String DEFAULT_INVALID_ITEM_MESSAGE = "\tplease, enter valid ";

    <T> void print(T sth);
    <T> void printAll(List<T> allSth);
    <T> void printSelectionList(List<T> allSth);

    void printInputOneItemMessage(String itemName);
    void printInvalidInputItemMessage(String itemName);
}
