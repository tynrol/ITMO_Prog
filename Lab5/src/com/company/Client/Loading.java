package com.company.Client;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.Iterator;

public class Loading {
    public static void upload(String content,Ship ship) {
        String[] line = content.split("\n");
        int count = 0;

        try {
            for (int i = 0; i < line.length; i++) {
                String[] parts = line[i].replace("\"", "").split(",");

                String type = parts[0];
                String name = parts[1];
                int iq = Integer.parseInt(parts[2]);
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);

                Location location = new Location(x,y);
                Date date = new Date();

                switch (type) {
                    case "Client.Moron":
                        ship.add(new Moron(name,iq,location,date));
                        break;
                    case "Client.Herring":
                        ship.add(new Herring(name,iq,location,date));
                        break;
                    case "Client.Fuchsia":
                        ship.add(new Fuchsia(name,iq,location,date));
                        break;
                    default:
                        count = count + 1;
                        break;
                }
            }
            System.out.println("File uploaded");
        } catch (Exception e) {
            System.out.println("File error");
        }
    }
    public static void save(Ship ship) {
        try {
            FileOutputStream outputStream = new FileOutputStream(Main.getFilename());
            Iterator<Midget> iter = ship.getCollection().iterator();
            while (iter.hasNext()) {
                Midget mid = iter.next();
                outputStream.write((mid.getClass().toString().replace("class com.company.", "") + "," +
                        mid.getName() + "," + mid.getAge() + "," + mid.getTall() + "," + mid.getIq() + "\n").getBytes());
            }
            System.out.println("Collection saved");
        } catch (Exception e) {
            System.out.println("Unable to save collection");
        }
    }
}
