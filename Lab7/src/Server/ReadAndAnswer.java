package Server;

import Default.Command;
import Default.Ship;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import static Server.SCP.select;

public class ReadAndAnswer extends Thread implements Runnable {

    private ObjectOutputStream out;
    private ObjectInputStream ois;
    private Socket socket;
    private Ship ship;
    private static boolean error = false;

    ReadAndAnswer(Socket socket, Ship ship) {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            this.socket = socket;
            this.ship = ship;
        } catch (IOException e){
            System.out.println("Error answering");
        }
    }
    @Override
    public void run() {
        try {

            List<Command> commands = new LinkedList<>();

            while (true) {
                Command cmd = (Command) ois.readObject();
                System.out.println("");
                commands.add(cmd);
                break;
            }

            if (commands.size() == 1) {
                Command cmd = commands.get(0);
                System.out.println("Request from " + socket.getInetAddress().toString().substring(1) + ": " + cmd.getName());
                answer(select(ship,cmd));
            }

        } catch (EOFException e) {
            System.out.println("The server found an unexpected end");
            answer("Cannot resolve request: the server found an unexpected end");
        } catch (IOException e) {
            System.out.println("Error executing request: " + e.toString());
            answer("Error: " + e.toString());
        } catch (ClassNotFoundException e) {
            answer("Client send data in wrong format");
        }
    }


    public void answer(String string){
        try {
            //out.writeObject(new Message(message, endFlag));
            //out.write(ByteBuffer.wrap(message.getBytes()));
            byte[] byteArray = string.getBytes();
            out.write(byteArray);
            out.close();
            System.out.println("Response was sent");
        } catch (IOException e) {
            System.out.println("Error sending data to client: " + e.getLocalizedMessage());
        }
    }

//        Command cmd = null;
//        if (!error) {
//            try {
//                List<Command> messages = new LinkedList<>();
//                while (true) {
//                    byte[] b = new byte[65535];
//                    sc.read(ByteBuffer.wrap(b));
//                    int l=0;
//                    for(int i=b.length-1;i>=0;i--){
//                        byte by=b[i];
//                        if(by!=0){
//                            l=i;
//                            break;
//                        }
//                    }
//                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(b,0,l+1));
//                    Object incoming = ois.readObject();
//                    if (incoming instanceof Command) {
//                        messages.add((Command) incoming);
//                        break;
//                    } else {
//                        //answer("Wrong format");
//                        return;
//                    }
//                }
//                if (messages.size() == 1) {
//                    Command message = messages.get(0);
//                    System.out.println("Получено сообщение от " + sc.getRemoteAddress() + ": " + message.getName());
//                    answer(SCP.select(ship, message));
//                } else {
//                    System.out.println("Получено " + messages.size() + " сообщений от " + sc.getRemoteAddress());
//                    for (int i = 0; i < messages.size(); i++)
//                        answer(SCP.select(ship, messages.get(i)));
//                }
////                System.out.println("\nReading...");
////                ByteBuffer buffer = ByteBuffer.allocate(10000);
////                //waiting for something to come
////                int read = sc.read(buffer);
////                while (read > 0) {
////                    read = sc.read(buffer);
////                }
////                buffer.rewind();
////                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
////                cmd = (Command) ois.readObject();
////                System.out.println("It's a command: " + cmd.getName());
//            } catch (IOException ex) {
//                System.out.println("Error reading: " + ex.getMessage());
//                error = true;
////                ex.printStackTrace();
//            } catch (ClassCastException ex) {
//                System.out.println("Wrong Object" + sc);
//            } catch (ClassNotFoundException ex) {
//                System.out.println(ex.getMessage() + sc);
//            }
//            //map.remove(sc.hashCode());

//        String response = "";
//        try {
//            response = SCP.select(ship, cmd);
//        } catch (Exception e) {
//            response="Cant execute this command.";
//            //e.printStackTrace();
//        }
//        if (response.isEmpty())
//            response="i did it!";
//
//        ByteBuffer bResponse = ByteBuffer.allocate(50000);
//        bResponse.put(response.getBytes());
//        bResponse.flip();
//        try {
//            if (!cmd.toString().equals("shutdown")) {
//                System.out.println("Responsing...");
//                try {
//                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                    ObjectOutputStream ois = new ObjectOutputStream(bytes);
//                    ois.writeObject(response);
//                    sc.write(ByteBuffer.wrap(bytes.toByteArray()));
//                    System.out.println("Response was sent\n");
//
//                } catch (IOException io) {
//                    System.out.println("Error while responsing" + sc + ":" + io.getMessage());
//                }
//            }
//        } catch (NullPointerException e){}

}
