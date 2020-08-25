package Default;

import java.time.ZonedDateTime;
import java.util.Stack;

public class Ship {
    private Stack<Midget> stack = new Stack<>();
    private ZonedDateTime dateOfCreation = ZonedDateTime.now();

    public void add(Midget midget) {
        stack.add(midget);
    }

    public boolean removeLast() {
        if (stack.isEmpty()) {
            System.out.println("Stack's empty");
            return false;
        } else {
            stack.remove(stack.size());
            return true;
        }
    }

    public boolean save() {
        try {
            System.out.println("Я должен сохранять эту жопу в бд, наверное");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean remove(Midget midget) {
        return stack.remove(midget);
    }

    public boolean remove(int index) {
        if (stack.size() > index) {
            stack.remove(index);
            return true;
        } else {
            return false;
        }
    }

    public boolean addIfMax(Midget midget) {
        if (stack.stream().max(Midget::compareTo).get().compareTo(midget) < 0) {
            stack.add(midget);
            return true;
        }
        return false;
    }

    public String show() {
        String[] result = {stack.isEmpty() ? "Collection is empty" : "Passengers: "};
        if (!stack.isEmpty())
            stack.stream().forEach(c -> result[0] += c.toString());
        return result[0];
    }

    public String info() {
        return "Collection type: " + stack.getClass().getName() + ",\n" +
                "Default.Ship was built " + dateOfCreation + ",\n" +
                + stack.size() + " midgets on board";
    }

    public int getSize() {
        return stack.size();
    }

    public Stack<Midget> getCollection() {
        return stack;
    }

    public ZonedDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
