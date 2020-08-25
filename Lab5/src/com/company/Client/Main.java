package com.company.Client;

import java.io.*;

import static com.company.Client.Command.*;


public class Main {
    //test
    private static final String FILENAME_VAR = "file.csv";
    private static String filename = null;
    public static final Ship ship = new Ship();
    public static String getFilename() {
        return filename;
    }

    public static void main(String[] args) {


        Runtime.getRuntime().addShutdownHook(new Thread(() -> Loading.save(ship)));

        filename = System.getenv().get("FILENAME_VAR");
        //test
        filename = FILENAME_VAR;

        System.out.println("Place \";\" at the end of the command \n");

        if (filename == null) {
            System.out.println("Environment variable was't set\"FILENAME_VAR\"");
            System.exit(-1);
        }
        try {
            String content = getFileContent(filename);
            //test
            System.out.println(content+"\n");
            if (!content.trim().equals("")) Loading.upload(content, ship);
            System.out.println(ship.getSize() + " midgets on board");
        } catch (FileNotFoundException e) {
            System.out.println("File wasn't found or user doesn't have access");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Input/Output error");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("File isn't correct");
        }

        try {
            while (true) {
                System.out.println(">");
                String response = commandProcess(getNextCommand());
                System.out.println(response);
            }
        } catch (IOException e) {
            System.out.println("Input/Output error");
        }
    }
}
