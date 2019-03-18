package com.netcracker.courses.oop;

import com.netcracker.courses.oop.music.MusicGenre;
import com.netcracker.courses.oop.music.digital.MusicCD;
import com.netcracker.courses.oop.music.digital.composition.AbstractDigitalComposition;
import com.netcracker.courses.oop.music.digital.composition.CompressedComposition;
import com.netcracker.courses.oop.music.digital.composition.DigitalCompositionFormat;

import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        AbstractDigitalComposition[] temp = new AbstractDigitalComposition[] {
                new CompressedComposition("Sad but true",
                        "Metallica",
                        MusicGenre.METAL,
                        1990,
                        320,
                        129,
                        DigitalCompositionFormat.MP3),

        };

        LinkedList<AbstractDigitalComposition> compilation = new LinkedList<>(Arrays.asList(temp));
        for (AbstractDigitalComposition c : compilation) {
            System.out.println(c);
        }

        MusicCD cd = new MusicCD(100, "metallica");
        cd.addAllCompositions(compilation);
        System.out.println(cd.findSong(0, 1000, 1800, 2020));
    }
}