package Server;

import Data.Ship;
import Help.Command;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReadClient implements Runnable {

    private Ship ship;
    private SocketChannel sc;
    private static boolean error = false;
    private static boolean clientDead = false;
    Map<Integer,SocketChannel> map=new ConcurrentHashMap();

    ReadClient(SocketChannel sc, Ship ship, Map map) {
        this.sc = sc;
        this.map=map;
        this.ship = ship;
    }

    @Override
    public void run() {
        Command cmd = null;
        if (!error && !clientDead) {
            try {
                System.out.println("Reading...");
                ByteBuffer buffer = ByteBuffer.allocate(10000);
                //waiting for something to come
                while (sc.read(buffer) > 0) {
                }
                buffer.rewind();
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
                cmd = (Command) ois.readObject();
                if (cmd.getName().equals("exit")) clientDead = true;
                System.out.println("It's a command: " + cmd.getName());

            } catch (IOException ex) {
                System.out.println("Error reading: " + ex.getMessage());
                error = true;
            } catch (ClassCastException ex) {
                System.out.println("Wrong Object" + sc);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage() + sc);
            }
            map.remove(sc.hashCode());
        }
        if (!error) {
            Thread thread = new Thread(new AnswerClient(sc, ship, cmd));
            thread.start();
        }
    }
    public static boolean getError(){
        return error;
    }
    public static void setError(boolean b){
        error = b;
    }
    public static boolean getClientDead(){
        return clientDead;
    }
    public static void setClientDead(boolean b){
        clientDead = b;
    }
}



