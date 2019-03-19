package com.netcracker.courses.oop;

import com.google.gson.Gson;
import com.netcracker.courses.oop.console.ConsoleController;
import com.netcracker.courses.oop.music.MusicGenre;
import com.netcracker.courses.oop.music.digital.MusicCD;
import com.netcracker.courses.oop.music.digital.composition.AbstractDigitalComposition;
import com.netcracker.courses.oop.music.digital.composition.CompressedComposition;
import com.netcracker.courses.oop.music.digital.composition.DigitalCompositionFormat;
import com.netcracker.courses.oop.music.digital.composition.LossLessComposition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {


        ConsoleController cc = new ConsoleController();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            try {
                if (!cc.handleUserInput(reader.readLine())) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        AbstractDigitalComposition[] temp = new AbstractDigitalComposition[] {
                new CompressedComposition(
                        "Sad but true",
                        "Metallica",
                        MusicGenre.METAL,
                        1990,
                        320,
                        129,
                        DigitalCompositionFormat.MP3),
                new CompressedComposition(
                        "Enter Sandman",
                        "Metallica",
                        MusicGenre.METAL,
                        1990,
                        330,
                        CompressedComposition.MAX_BITRATE,
                        DigitalCompositionFormat.MP3),
                new LossLessComposition(
                        "My world",
                        "Metallica",
                        MusicGenre.METAL,
                        1990,
                        500,
                        DigitalCompositionFormat.FLAC
                ),
        };

//        Gson gson = new Gson();
//        String gsonInput = gson.toJson(temp);
//        System.out.println(gsonInput);

        LinkedList<AbstractDigitalComposition> compilation = new LinkedList<>(Arrays.asList(temp));
        for (AbstractDigitalComposition c : compilation) {
            System.out.println(c);
        }

        MusicCD cd = new MusicCD(100, "metallica");
        cd.addAllCompositions(compilation);
        System.out.println(cd.findSong(0, 1000, 1800, 2020));
    }
}