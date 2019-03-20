package com.netcracker.courses.oop.console.utils;

import java.util.List;

public interface ConsoleScanner {

    interface IntegerBoundaryPredicate {
        boolean fitsIntBoundaries(int item);
    }

    <T> int selectIndFromList(List<T> allSth, ConsolePrinter consolePrinter);

    int readIntFromConsole(String varName, IntegerBoundaryPredicate predicate);

}
