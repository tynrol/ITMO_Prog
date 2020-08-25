package com.company.Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

import static com.company.Client.Main.ship;

public class Command {
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(Midget.class, new GsonConfig()).create();

    private String name;
    private String argument;

    public Command(String name, String argument) {
        this.name = name;
        this.argument = argument;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getArgument() {
        return argument;
    }
    public void setArgument(String argument) {
        this.argument = argument;
    }

    public boolean isIndex() {
        try {
            Integer.parseInt(argument);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static String getFileContent(String filename) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filename))) {

            StringBuilder fileContent = new StringBuilder();
            int current;
            do {
                current = reader.read();
                if (current != -1)
                    fileContent.append((char)current);
            } while (current != -1);
            return fileContent.toString();
        }
    }
    public static String getNextCommand() throws IOException{
        InputStreamReader reader = new InputStreamReader(System.in);
        StringBuilder builder = new StringBuilder();
        char currentChar;
        boolean inString = false;
        do {
            int k = reader.read();
            if (k == -1) {
                System.out.println("The end of the input");
                System.exit(0);
            }
            currentChar = (char) k;
            if (currentChar != ';' || inString)
                builder.append(currentChar);
            if (currentChar == '"')
                inString = !inString;
        } while (currentChar != ';' || inString);
        return builder.toString();
    }
    public static Command extractCommand(String request){
        int spaceI = request.indexOf(' ');
        if (spaceI == -1){
            return new Command(request, null);
        } else {
            return new Command(request.substring(0,spaceI),request.substring(spaceI+1));
        }
    }
    public static String commandProcess(String request){
        try {
            request = request.trim();
            Command command = extractCommand(request);
            if (command.getName().isEmpty()){
                return "Enter a command";
            }
            switch (command.getName()){
                case "remove_last":
                    return ship.removeLast() ? "Last midget was deleted" : "Unable to delete a midget";
                case "remove":
                    if (command.getArgument() != null) {
                        if (command.isIndex()) {
                            return ship.remove(Integer.parseInt(command.getArgument())) ? "Midget deleted" : "Unable to delete a midget";
                        } else {
                            Midget m = gson.fromJson(command.getArgument(), Midget.class);
                            if (m != null) {
                                return ship.remove(m) ? "Midget deleted" : "Unable to delete a midget";
                            }
                        }
                    } else return "Argument missing";
                case "exit":
                    System.exit(0);
                case "info":
                    return ship.info();
                case "show":
                    return ship.show();
                case "add_if_max":
                    if (command.getArgument() != null) {
                        Midget mid = gson.fromJson(command.getArgument(), Midget.class);
                        if (mid != null) {
                            if (ship.isMax(mid)) {
                                ship.add(mid);
                                return "Midget is added";
                            } else return "Midget isn't intelligent enough";
                        } else return "Parse error";
                    } else return "Argument missing";
                case "add":
                    if (command.getArgument() != null) {
                        Midget mid = gson.fromJson(command.getArgument(), Midget.class);
                        if (mid != null) {
                            ship.add(mid);
                            return "Midget is added";
                        } else return "Parse error";
                    } else return "Argument missing";
                case "help":
                    return(" \n" +
                            " add {Midget} Adds a passenger\n" +
                            " add_if_max {Midget} Adds a passenger if he's the smartest\n" +
                            " remove {Midget} Deletes a certain passenger\n" +
                            " remove {Index} Deletes a passenger by his enter number\n" +
                            " remove_last Deletes last passenger\n" +
                            " info Shows collection info\n" +
                            " show Shows all the passengers \n" +
                            " exit Exits with save\n" +
                            "Midget example:\n{\"type\": \"Moron\",\n\"name\": \"Malak\",\n\"stats\":{\n\t\"age\": 21,\n\t\"iq\": 66,\n\t\"tall\": 101\n\t}\n}");
                default:
                    return "Unknown command: " + command.getName();
            } } catch (Exception e){
            return "Parse error";
        }


    }
}
