package com.netcracker.courses.oop.console.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class DefaultConsoleScanner implements ConsoleScanner {
    @Override
    public int readIntFromConsole(String varName, IntegerBoundaryPredicate predicate) {
        int item;

        try(BufferedReader reader = new BufferedReader(
                new NeverClosingInputStreamReader(System.in))) {

            while (true) {

                try {
                    System.out.print("choose " + varName + " = ");
                    item = Integer.parseInt(reader.readLine());

                    if (!predicate.fitsIntBoundaries(item)) {
                        throw new NumberFormatException();
                    }

                    break;

                } catch (NumberFormatException e) {
                    System.out.println("\tplease, re-enter valid " + varName);
                }

            }

        } catch (IOException e) {
            System.out.println(
                    "console input failed, aborting request\n"
            );
            item = -1;
        }

        return item;
    }

    @Override
    public <T> int selectIndFromList(List<T> allSth, ConsolePrinter consolePrinter) {

        if (allSth.isEmpty()) {
            System.out.println("there are no items to select from!");
            return -1;
        }


        System.out.println("select one of the following:");
        consolePrinter.printSelectionList(allSth);


        return readIntFromConsole("index", (i) -> i > 0 && i <= allSth.size());
    }
}
