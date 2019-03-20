package com.netcracker.courses.oop.console;

import com.netcracker.courses.oop.console.utils.NeverClosingInputStreamReader;
import com.netcracker.courses.oop.music.MusicGenre;
import com.netcracker.courses.oop.music.digital.MusicCD;
import com.netcracker.courses.oop.music.digital.composition.AbstractDigitalComposition;
import com.netcracker.courses.oop.music.digital.composition.CompressedComposition;
import com.netcracker.courses.oop.music.digital.composition.DigitalCompositionFormat;
import com.netcracker.courses.oop.music.digital.composition.LossLessComposition;

import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Console controller class.
 * Enables user to interact with console using keyword + (appropriate) command
 */
public class ConsoleController {

    /* messages */
    public final static String GREETINGS_MESSAGE    = "Welcome to the Ultimate CD Burner!";
    public static final String BYE_MESSAGE          = "Good bye!";

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


   /* commands */
    public static final String SELECT_COMMAND       = "select";
    public static final String PRINT_COMMAND        = "print";
    public static final String CREATE_COMMAND       = "create";
    public static final String SORT_COMMAND         = "sort";
    public static final String FIND_COMMAND         = "find";

    public static final String EXIT_COMMAND         = "exit";
    public static final String HELP_COMMAND         = "help";



    /* options */
    public static final String SONG_OPTION          = "song";
    public static final String CD_OPTION            = "cd";
    public static final String COMPILATION_OPTION   = "cmpl";


    /* arguments */
    public static final String ALL_ARG              = "--all";
    public static final String BY_NAME_ARG          = "byname";
    public static final String BY_ARTIST_ARG        = "byartist";
    public static final String BY_SIZE_ARG          = "bysize";
    public static final String BY_DURATION_ARG      = "bydur";
    public static final String BY_GENRE_ARG         = "bygenre";

    private static final int                        INIT_CAPACITY = 10;
    private ArrayList<AbstractDigitalComposition>   allSongs;
    private ArrayList<MusicCD>                      allCD;
    private List<AbstractDigitalComposition>        compilation;

    private int                                     selectedSongInd = -1;
    private int                                     selectedCDInd = -1;

    public ConsoleController() {
        System.out.println(GREETINGS_MESSAGE);

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
                        DigitalCompositionFormat.WAV
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

        MusicCD cd;

        cd = new MusicCD(70, "small disk");
        cd.addAllCompositions(allSongs);
        allCD.add(cd);

        cd = new MusicCD(300, "large disk");
        cd.addAllCompositions(allSongs);
        allCD.add(cd);
    }

    /**
     * handles provided user command
     * @param userInput command
     * @return false only in case input == "exit"
     */
    public boolean handleUserInput(String userInput) {

        if (userInput == null) {
            System.out.println("weird, but input == null");
            return true;
        }

        if (userInput.isEmpty()) {
            return true;
        }

        String input = userInput.toLowerCase();

        StringTokenizer st = new StringTokenizer(input);

        input = st.nextToken();

        if (st.hasMoreTokens()) {
            switch (input) {
                case SELECT_COMMAND: {
                    select(st);
                    break;
                }
                case PRINT_COMMAND: {
                    print(st);
                    break;
                }
                case CREATE_COMMAND: {
                    create(st);
                    break;
                }
                case SORT_COMMAND: {
                    sort(st);
                    break;
                }
                case FIND_COMMAND: {
                    find(st);
                    break;
                }
                default: {
                    System.out.println("\"" + userInput + "\" is unsupported command/keyword. "
                            + "use help to see the list of available commands");
                    break;
                }
            }
        } else {
            switch (input) {
                case HELP_COMMAND: {
                    System.out.println(HELP_MESSAGE);
                    break;
                }
                case EXIT_COMMAND: {
                    System.out.println(BYE_MESSAGE);
                    return false;
                }
                default: {
                    System.out.println("\"" + userInput + "\" is unsupported command/keyword. "
                            + "use help to see the list of available commands");
                    break;
                }
            }
        }
        return true;
    }



    private void select(StringTokenizer st) {

        String option = st.nextToken();

        switch (option) {
            case SONG_OPTION: {
                selectedSongInd = selectIndFromList(allSongs);
                break;
            }
            case CD_OPTION: {
                selectedCDInd = selectIndFromList(allCD);
                break;
            }
            default: {
                System.out.println("\"" + option + "\" is unsupported option for select. "
                        + "use help to see the list of available commands");
                break;
            }
        }
    }

    private <T> void printSelectionList(List<T> allSth) {
        for (int i = 0; i < allSth.size(); i++) {
            System.out.print("\t" + String.format("%2d) ", i));
            print(allSth.get(i));
        }
    }

    private <T> int selectIndFromList(List<T> allSth) {

        if (allSth.isEmpty()) {
            System.out.println("there are no items to select from!");
            return -1;
        }



        System.out.println("select one of the following:");
        printSelectionList(allSth);



        int selectedInd;

        try(BufferedReader reader = new BufferedReader(
                new NeverClosingInputStreamReader(System.in))) {

            while (true) {

                try {
                    selectedInd = Integer.parseInt(reader.readLine());

                    if (selectedInd < 0 || selectedInd >= allSth.size()) {
                        throw new NumberFormatException();
                    }

                    break;

                } catch (NumberFormatException e) {
                    System.out.println("please, re-enter valid index");
                }

            }

        } catch (IOException e) {
            System.out.println(
                    "console input failed, aborting select request\n"
                    + "selection is cleared."
            );
            selectedInd = -1;
        }



        return selectedInd;
    }



    private void print(StringTokenizer st) {

        String option = st.nextToken();

        String arg = "";
        if (st.hasMoreTokens()) {
            arg = st.nextToken();
        }

        switch (option) {
            case SONG_OPTION: {
                if (ALL_ARG.equals(arg)) printAll(allSongs);
                else {
                    if (selectedSongInd == -1) System.out.println("no song is selected");
                    else {
                        System.out.print("\t");
                        print(allSongs.get(selectedSongInd));
                    }
                }
                break;
            }
            case CD_OPTION: {
                if (ALL_ARG.equals(arg)) printAll(allCD);
                else {
                    if (selectedCDInd == -1) System.out.println("no CD is selected");
                    else {
                        System.out.print("\t");
                        print(allCD.get(selectedCDInd));
                    }
                }
                break;
            }
            case COMPILATION_OPTION: {
                printAll(compilation);
                break;
            }
            default: {
                System.out.println("\"" + option + "\" is unsupported option for print. "
                        + "use help to see the list of available commands");
                break;
            }
        }
    }

    private <T> void printAll(List<T> allSth) {
        for (T t : allSth) {
            System.out.print("\t");
            print(t);
        }
    }

    private <T> void print(T sth) {
        if (sth instanceof MusicCD) {
            MusicCD cd = (MusicCD) sth;
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

        } else {
            System.out.println(sth);
        }
    }



    private void create(StringTokenizer st) {

        String option = st.nextToken();

        switch (option) {
            case COMPILATION_OPTION: {
                createCompilation();
                break;
            }
            case CD_OPTION: {
                createCD(st);
                break;
            }
            default: {
                System.out.println("\"" + option + "\" is unsupported option for create. "
                        + "use help to see the list of available commands");
                break;
            }
        }
    }

    private void createCompilation() {

        System.out.println("choose [l, r] boundaries for compilation:");
        printSelectionList(allSongs);

        int l;
        int r;

        try(BufferedReader reader = new BufferedReader(
                new NeverClosingInputStreamReader(System.in))) {


            while (true) {

                try {
                    System.out.print("choose l: ");
                    l = Integer.parseInt(reader.readLine());

                    if (l < 0 || l >= allSongs.size()) {
                        throw new NumberFormatException();
                    }

                    break;

                } catch (NumberFormatException e) {
                    System.out.println("no, enter valid l");
                }

            }

            while (true) {

                try {
                    System.out.print("choose r: ");
                    r = Integer.parseInt(reader.readLine());

                    if (r < l || r >= allSongs.size()) {
                        throw new NumberFormatException();
                    }

                    break;

                } catch (NumberFormatException e) {
                    System.out.println("no, enter valid r");
                }

            }

        } catch (IOException e) {
            System.out.println(
                    "console input failed, aborting create request\n"
            );
            compilation = null;
            return;
        }

        compilation = allSongs.subList(l, r + 1);
    }

    private void createCD(StringTokenizer st) {
        if (compilation == null) {
            System.out.println("create compilation before creating disk");
            return;
        }

        int sizeArg;
        String nameArg;

        try {
            sizeArg = Integer.parseInt(st.nextToken());
            nameArg = st.nextToken();
        } catch (Exception e) {
            System.out.println("invalid arguments for create cd. Use help to learn how to crate CD");
            return;
        }

        MusicCD cd = new MusicCD(sizeArg, nameArg);

        List<AbstractDigitalComposition> temp = cd.addAllCompositions(compilation);

        if (!temp.isEmpty()) {
            System.out.println("some compositions from compilation didn't fit on cd."
                    + "select them as compilation? y / n"
            );
        }

        try {
            int ans = System.in.read();
            if (ans == (int) 'y') compilation = temp;
        } catch (IOException e) {
            System.out.println("sth wrong with your answer");
        }

        allCD.add(cd);
    }



    private void sort(StringTokenizer st) {

        if (selectedCDInd == -1) {
            System.out.println("select CD before sorting it, please");
            return;
        }

        MusicCD selectedCD = allCD.get(selectedCDInd);

        String arg = st.nextToken();

        switch (arg) {
            case BY_NAME_ARG: {
                selectedCD.sort(MusicCD.SORT_BY_NAME);
                break;
            }
            case BY_ARTIST_ARG: {
                selectedCD.sort(MusicCD.SORT_BY_ARTIST);
                break;
            }
            case BY_DURATION_ARG: {
                selectedCD.sort(MusicCD.SORT_BY_DURATION);
                break;
            }
            case BY_GENRE_ARG: {
                selectedCD.sort(MusicCD.SORT_BY_GENRE);
                break;
            }
            case BY_SIZE_ARG: {
                selectedCD.sort(MusicCD.SORT_BY_SIZE);
                break;
            }
            default: {
                /* why not */
                selectedCD.sort(arg.hashCode());
                break;
            }
        }
    }



    private void find(StringTokenizer st) {

        if (!st.hasMoreTokens() || !st.nextToken().equals(SONG_OPTION)) {
            System.out.println("You have to specify \"song\" option for find command!");
            return;
        }

        if (selectedCDInd == -1) {
            System.out.println("first, select cd");
            return;
        }

        int minYear;
        int maxYear;
        int minSize;
        int maxSize;
        try(BufferedReader reader = new BufferedReader(
                new NeverClosingInputStreamReader(System.in))) {
            System.out.println("specify ranges [minSize, maxSize] and [minYear, maxYear]");

            while (true) {

                try {
                    System.out.print("minSize =  ");
                    minSize = Integer.parseInt(reader.readLine());

                    if (minSize < 0) throw new NumberFormatException();

                    break;

                } catch (NumberFormatException e) {
                    System.out.println("no, enter valid minSize");
                }

            }

            while (true) {

                try {
                    System.out.print("maxSize = ");
                    maxSize = Integer.parseInt(reader.readLine());

                    if (maxSize < minSize) throw new NumberFormatException();

                    break;

                } catch (NumberFormatException e) {
                    System.out.println("no, enter valid maxSize");
                }

            }

            while (true) {

                try {
                    System.out.print("minYear = ");
                    minYear = Integer.parseInt(reader.readLine());

                    if (minYear < 0) throw new NumberFormatException();

                    break;

                } catch (NumberFormatException e) {
                    System.out.println("no, enter valid minYear");
                }

            }

            while (true) {

                try {
                    System.out.print("maxYear = ");
                    maxYear = Integer.parseInt(reader.readLine());

                    if (maxYear < minYear) throw new NumberFormatException();

                    break;

                } catch (NumberFormatException e) {
                    System.out.println("no, enter valid maxYear");
                }

            }

        } catch (IOException e) {
            System.out.println(
                    "console input failed, aborting find request\n"
            );
            return;
        }

        AbstractDigitalComposition result =
                allCD.get(selectedCDInd).findSong(minSize, maxSize, minYear, maxYear);

        if (result == null) {
            System.out.println("nothing found.");
        } else {
            System.out.println("found song :");
            System.out.print("\t");
            print(result);
        }
    }
}
