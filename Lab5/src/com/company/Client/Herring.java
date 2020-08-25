package com.company.Client;

import java.util.Date;
import java.util.Objects;

import static com.company.Client.Main.ship;

public class Herring extends Midget {
    private float sarcastic;
    private int weight;

    public Herring(String name, int age, int tall, int iq){super(name,age,tall,iq);}
    public Herring(String name, int iq, Location location, Date date){super(name,iq,location,date);}
    public Herring(String name, int age, int tall, int iq, float sarcastic, int weight){
        super(name,age,tall,iq);
        this.sarcastic = sarcastic;
        this.weight = weight;
    }

    public float getSarcastic(){
        return sarcastic;
    }
    public void setSarcastic(float sarcastic){
        this.sarcastic = sarcastic;
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
        if (!(o instanceof Herring)) return false;
        if (!super.equals(o)) return false;
        Herring herring = (Herring) o;
        return Float.compare(herring.sarcastic, sarcastic) == 0 &&
                weight == herring.weight;
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sarcastic, weight);
    }
    @Override
    public String toString(){
        Date date = new Date();
        if (getDate() == null){
            date = ship.getDateOfCreation();
        } else {
            date = getDate();
        }
        return "Herring{" +
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
