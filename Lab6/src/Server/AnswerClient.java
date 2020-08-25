package Server;

import Data.Ship;
import Help.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static Server.Server.savefile;

public class AnswerClient implements Runnable{
    protected SocketChannel channel;
    protected Ship ship;
    protected Command command;

    public AnswerClient(SocketChannel channel, Ship ship, Command command) {
        this.channel = channel;
        this.ship = ship;
        this.command = command;
    }

    @Override
    public void run() {
        String response = "";
        try {
            response = SCP.select(ship, command, savefile);
        } catch (Exception e) {
            response="Cant execute this command. " + e.getMessage();
        }
        if (response.isEmpty())
            response="i did it!";

        ByteBuffer bResponse = ByteBuffer.allocate(50000);
        bResponse.put(response.getBytes());
        bResponse.flip();
        try {
            if (!command.toString().equals("shutdown")) {
                System.out.println("Responsing...");
                try {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    ObjectOutputStream ois = new ObjectOutputStream(bytes);
                    ois.writeObject(response);
                    channel.write(ByteBuffer.wrap(bytes.toByteArray()));
                    System.out.println("Response was sent\n");

                } catch (IOException io) {
                    System.out.println("Error while responsing" + channel + ":" + io.getMessage());
                }
            }
        } catch (NullPointerException e){}
    }
}
