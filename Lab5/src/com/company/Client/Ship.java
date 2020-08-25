package com.company.Client;

import java.util.Date;
import java.util.Iterator;
import java.util.Stack;

public class Ship {
     private Stack<Midget> stack = new Stack<>();
     private Date dateOfCreation = new Date();

    /**
     * <p>Добавляет пассажира в коллекцию</p>
     * @param midget Midget
     */
    public void add(Midget midget){
          stack.push(midget);
     }

    /**
     * <p>Удаляет последнего пассажира из коллекции</p>
     * @return boolean
     */
     public boolean removeLast(){
         if (stack.empty()){
             System.out.println("Stack's empty");
             return false;
         } else {
             System.out.println(stack.pop() + " is deleted");
             return true;
         }
     }

    /**
     * <p>Удаляет указанного пассажира из коллекции</p>
     * @param midget Midget
     * @return boolean
     */
     public boolean remove(Midget midget){
         return stack.remove(midget);
     }

    /**
     * <p>Удаляет пассажира по его индексу</p>
     * @param index int
     * @return boolean
     */
     public boolean remove(int index){
         if (stack.size() > index){
             stack.remove(index);
             return true;
         } else {
             return false;
         }
     }

     public boolean isMax(Midget m) {
         Iterator<Midget> iter = stack.iterator();
         while (iter.hasNext()) {
             Midget mid = iter.next();
             if (m.compareTo(mid) <= 0) return false;
         }
         return true;
     }

    /**
     * <p>Добавляет пассажира в коллекцию, если его ICQ больше всех остальных</p>
     * @param midget Midget
     */
     public void addIfMax(Midget midget){
     }

    /**
     * <p>Показывает всех пассажиров коллекции</p>
     * @return String
     */
     public String show(){
         return ("Passengers: " + stack);
     }

    /**
     * <p>Выводит информацию о текущей коллекции</p>
     * @return String
     */
     public String info(){
         return "Collection type: " + stack.getClass().getName() + ",\n" +
                 "Ship was built " + dateOfCreation + ",\n" +
                 + stack.size() + " midgets on board";
                 

     }
    public int getSize() {
        return stack.size();
    }
    public Stack<Midget> getCollection(){
         return stack;
    }
    public Date getDateOfCreation() {
        return dateOfCreation;
    }
    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
