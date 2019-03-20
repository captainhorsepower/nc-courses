package com.netcracker.courses.oop.console.utils;

import com.netcracker.courses.oop.music.digital.MusicCD;
import com.netcracker.courses.oop.music.digital.composition.AbstractDigitalComposition;

import java.util.List;

public class DefaultMusicPrinter implements ConsolePrinter {

    @Override
    public <T> void print(T sth) {
        if (sth instanceof MusicCD) {
            MusicCD cd = (MusicCD) sth;
            printCD(cd);
        } else {
            System.out.println(sth);
        }
    }

    private void printCD(MusicCD cd) {
        String output = String.format(
                "CD: %-10s %5d MB total %6.1f MB free %20s %2d:%02d"
                , cd.getCDName()
                , cd.getTotalFreeSpaceMB()
                , cd.getFreeSpaceMB()
                , "total duration"
                , (cd.getDurationSeconds() / 60)
                , (cd.getDurationSeconds() % 60)
        );

        System.out.println(output);

        System.out.println("\tsongs on cd:");
        for (AbstractDigitalComposition song : cd.getSongs()) {
            System.out.print("\t\t");
            print(song);
        }
    }

    @Override
    public <T> void printAll(List<T> allSth) {
        for (T t : allSth) {
            System.out.print("\t");
            print(t);
        }
    }

    @Override
    public <T> void printSelectionList(List<T> allSth) {
        for (int i = 0; i < allSth.size(); i++) {
            System.out.print("\t" + String.format("%2d) ", i));
            print(allSth.get(i));
        }
    }

    @Override
    public void printInputOneItemMessage(String itemName) {
        System.out.print(DEFAULT_CHOOSE_ITEM_MESSAGE + itemName);
    }

    @Override
    public void printInvalidInputItemMessage(String itemName) {
        System.out.println(DEFAULT_INVALID_ITEM_MESSAGE + itemName);
    }
}
