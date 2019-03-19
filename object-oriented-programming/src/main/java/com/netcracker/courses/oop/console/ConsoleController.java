package com.netcracker.courses.oop.console;

import com.netcracker.courses.oop.music.MusicGenre;
import com.netcracker.courses.oop.music.digital.MusicCD;
import com.netcracker.courses.oop.music.digital.composition.AbstractDigitalComposition;
import com.netcracker.courses.oop.music.digital.composition.CompressedComposition;
import com.netcracker.courses.oop.music.digital.composition.DigitalCompositionFormat;
import com.netcracker.courses.oop.music.digital.composition.LossLessComposition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Console controller class.
 * Enables user to interact with console using keyword + (appropriate) command
 */
public class ConsoleController {

    /* messages */
    public final static String GREETINGS_MESSAGE = "Welcome to the Ultimate CD Burner!";
    public final static String INIT_LOADING_MESSAGE = "loading songs data...";
    public final static String INIT_LOADING_FINISHED_MESSAGE = "finished!";

    public static final String BYE_MESSAGE = "Good bye!";

    public final static String HELP_MESSAGE;

    /* initializes HELP_MESSAGE */
    static {

        InputStream inputStream = ConsoleController.class.getResourceAsStream("/help.help");

        if (inputStream == null) {
            HELP_MESSAGE = "help will be added with future releases";
        } else {
            StringBuilder sb = new StringBuilder();

            try (BufferedReader r = new BufferedReader(new InputStreamReader(inputStream))) {
                String line = r.readLine();

                while (line != null) {
                    sb.append(line).append("\n");
                    line = r.readLine();
                }

                sb.setLength(sb.length() - 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            HELP_MESSAGE = sb.toString();
        }
    }


//    /* keywords */
//    public final static String SELECTOR = "selector";
//
//
   /* commands */

    /* quit the app */
    public static final String EXIT = "exit";
    /* get help */
    public static final String HELP = "help";

//
//   /* creating compilation and saving it to CD */
//
//   /**
//    * console command to create a new CD.
//    * can be followed with 2 params: CD name, totalFreeSpaceMB
//    * if no params provided, program will ask for them;
//    */
//   public final static String CREATE_CD = "create-cd";


//    /**
//     * lists all songs with numbers at the left side for selecting range for compilation
//     * then compilation is written to new CD
//     */
//    public final static String LIST_ALL_SONGS      = "ls-songs";
//    public final static String LIST_ALL_DISKS = "ls-disks";
//    /* print */
//    public final static String PRINT_ITEM  = "print";
//    public final static String PRINT_ALL   = "print-all";
   
   
//    /* select */
//    public final static String SELECT_DISK = "select-cd";
//    public final static String SELECT_SONG = "select-song";


//    public static MusicCD createCD() {
//        return null;
//    }

    private static final int                        INIT_CAPACITY = 10;
    private ArrayList<AbstractDigitalComposition>   allSongs;
    private ArrayList<MusicCD>                      allCD;

    private int                                     selectedSongInd;
    private int                                     selectedCDInd;

    public ConsoleController() {
        System.out.println(GREETINGS_MESSAGE);

        /*
         * all available songs are loaded fast enough,
         * so may skip this message for now
         */
//        System.out.println(INIT_LOADING_MESSAGE);

        allCD = new ArrayList<>(INIT_CAPACITY);

        /* i might replace it with reading from file */
        AbstractDigitalComposition[] temp = new AbstractDigitalComposition[] {
                new CompressedComposition(
                        "Sad but True",
                        "Metallica",
                        MusicGenre.METAL,
                        1993,
                        324,
                        CompressedComposition.MAX_BITRATE,
                        DigitalCompositionFormat.MP3),
                new CompressedComposition(
                        "Enter Sandman",
                        "Metallica",
                        MusicGenre.METAL,
                        1991,
                        352,
                        CompressedComposition.MAX_BITRATE,
                        DigitalCompositionFormat.MP3),
                new LossLessComposition(
                        "My World",
                        "Metallica",
                        MusicGenre.METAL,
                        2003,
                        346,
                        DigitalCompositionFormat.FLAC
                ),
                new LossLessComposition(
                        "St. Anger",
                        "Metallica",
                        MusicGenre.METAL,
                        2003,
                        441,
                        DigitalCompositionFormat.FLAC
                ),
                new LossLessComposition(
                        "Sweet Amber",
                        "Metallica",
                        MusicGenre.METAL,
                        2003,
                        428,
                        DigitalCompositionFormat.FLAC
                ),
                new CompressedComposition(
                        "The Emperor",
                        "Paint The Future Black",
                        MusicGenre.ALTERANTIVE,
                        2017,
                        200,
                        320,
                        DigitalCompositionFormat.MP3),
                new CompressedComposition(
                        "Scars on the Face",
                        "Paint The Future Black",
                        MusicGenre.ALTERANTIVE,
                        2017,
                        205,
                        320,
                        DigitalCompositionFormat.MP3),
                new CompressedComposition(
                        "Less Than Three",
                        "Paint The Future Black",
                        MusicGenre.ALTERANTIVE,
                        2017,
                        242,
                        320,
                        DigitalCompositionFormat.MP3),
                new LossLessComposition(
                        "Let's Go",
                        "Stuck in the Sound",
                        MusicGenre.INDIE,
                        2012,
                        271,
                        DigitalCompositionFormat.FLAC
                ),
                new CompressedComposition(
                        "Move",
                        "Saint Motel",
                        MusicGenre.INDIE,
                        2016,
                        307,
                        240,
                        DigitalCompositionFormat.MP3),
                new CompressedComposition(
                        "Slow Motion",
                        "Saint Motel",
                        MusicGenre.INDIE,
                        2016,
                        236,
                        240,
                        DigitalCompositionFormat.MP3),
                new CompressedComposition(
                        "You Can Be You",
                        "Saint Motel",
                        MusicGenre.INDIE,
                        2016,
                        237,
                        240,
                        DigitalCompositionFormat.MP3),

        };

        allSongs = new ArrayList<>(Arrays.asList(temp));

//        System.out.println(INIT_LOADING_FINISHED_MESSAGE);
    }

    /**
     * handles provided user command
     * @param input command
     * @return false only in case input == "exit"
     */
    public boolean handleUserInput(String input) {

        if (input == null) {
            System.out.println("weird, but input == null");
            return true;
        }

        input = input.toLowerCase();

        /* get keyword or command */
        StringTokenizer st = new StringTokenizer(input);
        input = st.nextToken();

        switch (input) {
            case HELP: {
                System.out.println(HELP_MESSAGE);
                break;
            }
            case EXIT: {
                System.out.println(BYE_MESSAGE);
                return false;
            }
            default: {
                System.out.println("\"" + input + "\" is unsupported command/keyword. "
                        + "use help to see the list of available commands");
                break;
            }
        }
        return true;
    }


}
