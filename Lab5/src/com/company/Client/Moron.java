package com.company.Client;

import java.util.Date;
import java.util.Objects;

import static com.company.Client.Main.ship;

public class Moron extends Midget {
    private boolean awakeness;
    private int headDamageCount; //jbanDamageCount
    private float talkative; // The more talkative is the more
    private int headAngle; //jbanAngle, in deg, 0 is straight

    public Moron(String name, int age, int tall, int iq){super(name,age,tall,iq);}
    public Moron(String name, int iq, Location location, Date date){super(name,iq,location,date);}
    public Moron(String name, int age, int tall, int iq, boolean awakeness,int headDamageCount, float talkative, int headAngle){
        super(name, age, tall, iq);
        this.awakeness = awakeness;
        this.headDamageCount = headDamageCount;
        this.talkative = talkative;
        this.headAngle = headAngle;
    }

    public boolean getAwakeness(){
        return awakeness;
    }
    public void setAwakeness(boolean awakeness){
        this.awakeness = awakeness;
    }
    public int getHeadDamageCount(){
        return headDamageCount;
    }
    public void setHeadDamageCount(int headDamageCount){
        this.headDamageCount = headDamageCount;
    }
    public float getTalkative(){
        return talkative;
    }
    public void setTalkative(float talkative){
        this.talkative = talkative;
    }
    public int getHeadAngle(){
        return headAngle;
    }
    public void setHeadAngle(int headAngle){
        this.headAngle = headAngle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Moron)) return false;
        if (!super.equals(o)) return false;
        Moron moron = (Moron) o;
        return awakeness == moron.awakeness &&
                headDamageCount == moron.headDamageCount &&
                Float.compare(moron.talkative, talkative) == 0 &&
                headAngle == moron.headAngle;
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), awakeness, headDamageCount, talkative, headAngle);
    }
    @Override
    public String toString(){
        Date date = new Date();
        if (getDate() == null){
            date = ship.getDateOfCreation();
        } else {
            date = getDate();
        }
        return "Moron{" +
                "name='" + getName() +  "'" +
                ", iq=" + getIq() +
                ", location={" + getLocation().getX() + ", " + getLocation().getY() + "}" +
                ", date: " + date +
                '}';
   }
    @Override
    public void justMakingNoises(){
        System.out.println("YAYAYAYYAYAYAY");
    }
}