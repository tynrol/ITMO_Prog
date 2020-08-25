package Default;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Objects;
import java.time.ZonedDateTime;

public class Midget implements Comparable<Midget>, Serializable {

    private String name;
    private Location location;
    private ZonedDateTime date;
    private int age;
    private int tall;
    private int iq;

    public Midget(String name, int age, int tall, int iq, Location location) {
        this.age = age;
        this.tall = tall;
        this.name = name;
        this.location = location;
        this.iq = iq;
        this.date= ZonedDateTime.now();
    }
    public Midget(String name, int age, int tall, int iq, Location location,ZonedDateTime date) {
        this.age = age;
        this.tall = tall;
        this.name = name;
        this.location = location;
        this.iq = iq;
        this.date = date;
    }
    public Midget(JSONObject rawObject) {
        try {
            name = rawObject.getString("name");
            age = rawObject.getInt("age");
            tall = rawObject.getInt("tall");
            iq = rawObject.getInt("iq");
            JSONObject preLocation = rawObject.getJSONObject("location");
            location = new Location(preLocation.getInt("x"), preLocation.getInt("y"));
            date = ZonedDateTime.now();
        } catch (JSONException e) {
            throw new InvalidParameterException("Creature object cannot be created: invalid json structure\n");
        } catch (NullPointerException e) {
            System.out.println("Hey, that's a null");
        } catch (IllegalArgumentException e) {
            System.out.println("Ok, creation date is now");
        }
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public int getTall() {
        return tall;
    }
    public int getIq() {
        return iq;
    }
    public Location getLocation() {
        return location;
    }
    public ZonedDateTime getDate() {
        return date;
    }

    public int compareTo(Midget m) {
        return iq - m.iq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Midget)) return false;
        Midget midget = (Midget) o;
        return age == midget.age &&
                tall == midget.tall &&
                iq == midget.iq &&
                Objects.equals(name, midget.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, tall, iq);
    }

    @Override
    public String toString(){
        if (getDate() == null){
            date = ZonedDateTime.now();
        } else {
            date = getDate();
        }
        return "\nMidget{" +
                "name='" + getName() +  "'" +
                ", age=" + getAge() +
                ", tall=" + getTall() +
                ", iq=" + getIq() +
                ", location={" + getLocation().getX() + ", " + getLocation().getY() + "}" +
                ", date: " + date +
                '}';
    }
}