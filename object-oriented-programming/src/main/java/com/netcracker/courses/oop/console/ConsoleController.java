package com.netcracker.courses.oop.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Console controller class.
 * Enables user to interact with console using keyword + (appropriate) command
 */
public class ConsoleController {

    /* messages */
    public final static String GREETINGS_MESSAGE = "Welcome to the Ultimate CD Burner!";
    public final static String INIT_LOADING_MESSAGE = "loading songs data...";


    public static final String BYE_MESSAGE = "Good bye!";

    // TODO: 3/19/2019 add descriptive HELP_MESSAGE
    public final static String HELP_MESSAGE;
    static {
//        System.out.println(ConsoleController.class.getResource("help.help"));
//        InputStream input = ConsoleController.class.getResourceAsStream("help.help");
//        System.out.println(input);
//        input = ConsoleController.class.getClassLoader().getResourceAsStream("/help.help");
//        System.out.println(input);
//        input = ConsoleController.class.getClassLoader().getResourceAsStream("help.help");
//        System.out.println(input);

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


    public ConsoleController() {
        System.out.println(GREETINGS_MESSAGE);
        System.out.println(INIT_LOADING_MESSAGE);
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