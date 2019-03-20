package com.netcracker.courses.oop;

import com.netcracker.courses.oop.console.ConsoleController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        ConsoleController cc = new ConsoleController();


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            while(cc.handleUserInput(reader.readLine()));

        } catch (IOException e) {
            System.out.println(e.getMessage() + ". Aborting...");
        }

    }
}