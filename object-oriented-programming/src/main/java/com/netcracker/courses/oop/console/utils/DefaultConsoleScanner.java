package com.netcracker.courses.oop.console.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class DefaultConsoleScanner implements ConsoleScanner {
    @Override
    public int readIntFromConsole(String varName, AdvancedConsolePrinter printer,
                                  IntegerBoundaryPredicate predicate) {
        int item;

        try(BufferedReader reader = new BufferedReader(
                new NeverClosingInputStreamReader(System.in))) {

            while (true) {

                try {
                    printer.printInputOneItemMessage(varName);
                    item = Integer.parseInt(reader.readLine());

                    if (!predicate.fitsIntBoundaries(item)) {
                        throw new NumberFormatException();
                    }

                    break;

                } catch (NumberFormatException e) {
                    printer.printInvalidInputItemMessage(varName);
                }

            }

        } catch (IOException e) {
            printer.printConsoleFailedMessage();
            item = -1;
        }

        return item;
    }

    @Override
    public <T> int selectIndFromList(List<T> allSth, AdvancedConsolePrinter printer) {

        if (allSth.isEmpty()) {
            printer.printSelectFromEmptyListMessage();
            return -1;
        }


        printer.printSelectFromListMessage();
        printer.printSelectionList(allSth);


        return readIntFromConsole("index", printer, (i) -> i < allSth.size() && i >= 0);
    }
}