package Help;

import Data.Location;
import Data.Midget;
import Data.Ship;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Iterator;

public class Loading {
    public static void upload(String content, Ship ship) {
        String[] line = content.split("\n");
        try {
            for (int i = 0; i < line.length; i++) {
                String[] parts = line[i].replace("\"", "").split(",");

                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                int tall = Integer.parseInt(parts[2]);
                int iq = Integer.parseInt(parts[3]);
                int x = Integer.parseInt(parts[4]);
                int y = Integer.parseInt(parts[5]);
                String GMTDate = parts[6];

                Location location = new Location(x,y);
                Date date = new Date(GMTDate);

                ship.add(new Midget(name,age, tall, iq,location,date));

            }
            System.out.println("File uploaded");
        } catch (NullPointerException e) {
            System.out.println("File is empty");
        } catch (Exception e){
            System.out.println("File error");
        }
    }
    public static void save(String fileName, Ship ship) {
        try {
            FileOutputStream outputStream =   new FileOutputStream(fileName);
            Iterator<Midget> iter = ship.getCollection().iterator();
            while (iter.hasNext()) {
                Midget mid = iter.next();
                outputStream.write((mid.getName() + "," + mid.getAge() + "," + mid.getTall() + "," + mid.getIq() + "," + mid.getLocation().getX() + "," + mid.getLocation().getY() + "," + mid.getDate().toGMTString() +"\n").getBytes());
            }
            System.out.println("Collection saved");
        } catch (Exception e) {
            System.out.println("Unable to save collection");
        }
    }
}
