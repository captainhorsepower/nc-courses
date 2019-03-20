package com.netcracker.courses.oop.console.utils;

import java.util.List;

public interface ConsolePrinter {

    String DEFAULT_CHOOSE_ITEM_MESSAGE = "choose ";
    String DEFAULT_INVALID_ITEM_MESSAGE = "\tplease, enter valid ";
    String DEFAULT_CONSOLE_FAILDE_MESSAGE = "console input error. Aborting current task...";
    String DEFAULT_SELECT_FROM_LIST_MESSAGE = "select one of the following:";
    String DEFAULT_SELECTION_LIST_IS_EMPTY_MESSAGE = "there are no items to select from!";

    <T> void print(T sth);
    <T> void printAll(List<T> allSth);
    <T> void printSelectionList(List<T> allSth);

    default void printMessage(String message) {
        System.out.println(message);
    }

    void printInputOneItemMessage(String itemName);
    void printInvalidInputItemMessage(String itemName);

    default void printConsoleFailedMessage() {
        System.out.println(DEFAULT_CONSOLE_FAILDE_MESSAGE);
    }
    default void printSelectFromListMessage() {
        System.out.println(DEFAULT_SELECT_FROM_LIST_MESSAGE);
    }
    default void printSelectFromEmptyListMessage() {
        System.out.println(DEFAULT_SELECTION_LIST_IS_EMPTY_MESSAGE);
    }
}
