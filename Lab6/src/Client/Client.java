package Client;

import Help.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static Client.CCP.*;
import static Server.Server.readPort;

public class Client {

    public static boolean[] isAddShutDown = {false};
    public static Integer port;

    public static void main(String[] args) {
        Socket socket = null;

        Scanner scanner = new Scanner(System.in);

        //connection
        while (true) {
            port = readPort(port,scanner);
            while (true) {
                try {
                    socket = new Socket("localhost", port);
                    send(new Command("show", null, null),socket);
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    System.out.println("Response from server: " + read(ois));
                } catch (UnknownHostException ex) {
                    System.out.println(ex.getMessage());
                    break;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    break;
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                    break;
                }


                final Socket shutdown = socket;
                if (!isAddShutDown[0]) {
                    Runtime.getRuntime().addShutdownHook(new Thread(){
                        @Override
                        public void run() {
                            isAddShutDown[0] = true;
                            try {
                                //send(new Command("exit", null, null), shutdown);
                                try {
                                    System.out.println("Closing...");
                                    shutdown.close();
                                } catch (IOException ex) {
                                    System.out.println("Error of closing socket");
                                } catch (NoSuchElementException e){
                                    System.out.println("Just kill me.");
                                }
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    });
                }
                //reading string and sending it to GULAG
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                try {
                    socket.setSoTimeout(7000);
                    Command cmd;
                    while (true) {
                        try {
                            cmd = readCommand(br);
                            if (cmd != null) {
                                send(cmd, socket);
                                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                                System.out.println("Response from server: " + read(ois));
                                if (cmd.getName().equals("exit")) {System.exit(0);}
                            }
                        } catch (InvalidParameterException e) {
                            System.out.println("Wrong command: "+ e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Server isn't available: " + e.getMessage());
                    System.out.println("Trying");

                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
    }
}