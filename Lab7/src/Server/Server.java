package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Default.Ship;

public class Server {

    public static void main(String[] args) {

        Ship ship = new Ship();
        DB db = new DB();

        Runtime.getRuntime().addShutdownHook(new Thread(()->  {
            System.out.println("What the");
        }));

        Integer port = 1599;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Rise and shine");
        } catch (IOException e) {
            System.out.println("Port locked: " + port);
            System.exit(-1);
        }
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new ReadAndAnswer(clientSocket,ship).start();
            } catch (IOException e) {
                System.out.println("Connection error: " + e.getMessage());
            }
        }
    }

}
