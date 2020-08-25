package com.company.Client;

import java.util.Date;
import java.util.Objects;

import static com.company.Client.Main.ship;

public class Fuchsia extends Midget {
    private float joking; // (0,1)
    private int weight;

    public Fuchsia(String name, int age, int tall, int iq){super(name,age,tall,iq);}
    public Fuchsia(String name, int iq, Location location, Date date){super(name,iq,location,date);}
    public Fuchsia(String name, int age, int tall, float headDiameter, int iq, float weakness, float joking, int weight){
        super(name,age,tall,iq);
        this.joking = joking;
        this.weight = weight;
    }

    public float getJoking(){
        return joking;
    }
    public void setJoking(float joking){
        this.joking = joking;
    }
    public int getWeight(){
        return weight;
    }
    public void setWeight(int weight){
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fuchsia)) return false;
        if (!super.equals(o)) return false;
        Fuchsia fuchsia = (Fuchsia) o;
        return Float.compare(fuchsia.joking, joking) == 0 &&
                weight == fuchsia.weight;
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), joking, weight);
    }
    @Override
    public String toString(){
        Date date;
        if (getDate() == null){
            date = ship.getDateOfCreation();
        } else {
            date = getDate();
        }
        return "Fuchsia{" +
                "name='" + getName() +  "'" +
                ", iq=" + getIq() +
                ", location={" + getLocation().getX() + ", " + getLocation().getY() + "}" +
                ", date: " + date +
                '}';
    }
    @Override
    public void justMakingNoises() {
        System.out.println("YAYAYAYYAYAYAY");
    }
}
