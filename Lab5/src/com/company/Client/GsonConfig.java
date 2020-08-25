package com.company.Client;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

import static com.company.Client.Main.ship;

public class GsonConfig implements JsonDeserializer<Midget> {
    public Midget deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject object = json.getAsJsonObject();
            String typeE = object.get("type").getAsString();
            String name = object.get("name").getAsString();
            int iq = object.get("iq").getAsInt();
            JsonObject coordinates = object.get("location").getAsJsonObject();
            int x = coordinates.get("age").getAsInt();
            int y = coordinates.get("tall").getAsInt();

            Location location = new Location(x,y);
            Date date = new Date();
            switch (typeE) {
                case "Moron":
                    return new Moron(name, iq, location, date);
                case "Herring":
                    return new Herring(name, iq, location, date);
                case "Fuchsia":
                    return new Fuchsia(name, iq, location, date);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
