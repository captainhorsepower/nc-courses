package com.netcracker.courses.oop.console.utils;

public interface AdvancedConsolePrinter extends ConsolePrinter {

    String DEFAULT_CHOOSE_ITEM_MESSAGE = "choose ";
    String DEFAULT_INVALID_ITEM_MESSAGE = "\tplease, enter valid ";
    String DEFAULT_CONSOLE_FAILED_MESSAGE = "console input error. Aborting current task...";
    String DEFAULT_SELECT_FROM_LIST_MESSAGE = "select one of the following:";
    String DEFAULT_SELECTION_LIST_IS_EMPTY_MESSAGE = "there are no items to select from!";

    @Override
    default void printMessage(String message) {
        System.out.println(message);
    }

    default void printInputOneItemMessage(String itemName) {
        System.out.print(DEFAULT_CHOOSE_ITEM_MESSAGE + itemName);
    }

    default void printInvalidInputItemMessage(String itemName) {
        System.out.println(DEFAULT_INVALID_ITEM_MESSAGE + itemName);
    }

    default void printConsoleFailedMessage() {
        System.out.println(DEFAULT_CONSOLE_FAILED_MESSAGE);
    }
    default void printSelectFromListMessage() {
        System.out.println(DEFAULT_SELECT_FROM_LIST_MESSAGE);
    }
    default void printSelectFromEmptyListMessage() {
        System.out.println(DEFAULT_SELECTION_LIST_IS_EMPTY_MESSAGE);
    }
}