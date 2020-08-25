package com.company.Client;


import java.util.Date;
import java.util.Objects;

public abstract class Midget implements Creature, Comparable<Midget> {

    private String name;
    private Location location;
    private Date date;
    private int age;
    private int tall;
    private int iq;

    public Midget(String name, int age, int tall, int iq){
        this.name = name;
        this.age = age;
        this.iq = iq;
        this.tall = tall;
    }
    public Midget(String name, int iq, Location location, Date date){
        this.name = name;
        this.location = location;
        this.iq = iq;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }
    public int getTall(){
        return tall;
    }
    public void setTall(int tall){
        this.tall = tall;
    }
    public int getIq(){
        return iq;
    }
    public void setIq(int iq){
        this.iq = iq;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
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

    public int compareTo(Midget m) {
        return iq - m.iq;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, tall, iq);
    }
}
