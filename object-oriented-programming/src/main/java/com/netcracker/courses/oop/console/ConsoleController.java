package com.netcracker.courses.oop.console;

import com.netcracker.courses.oop.music.digital.MusicCD;

/**
 * Console controller class.
 * Enables user to interact with console using keyword + (appropriate) command
 */
public class ConsoleController {

    // TODO: 3/19/2019 add descriptive HELP
    public final static String HELP = "";
    
    /* start-up messages */
    public final static String GREETINGS    = "Welcome to the Ultimate CD Burner!\n";
    public final static String INIT_LOADING = "loading songs data...\n";
    
//    /* keywords */
//    public final static String SELECTOR = "selector";
//
//
//   /* commands */
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
        System.out.println(GREETINGS);
        System.out.println(INIT_LOADING);
    }

}
