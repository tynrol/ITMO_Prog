package Server;

import Client.Client;
import Data.Ship;
import Help.Loading;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Server {

    public static final String[] fileName = new String[]{null};
    public static String savefile;

    public static int readPort(Integer port, Scanner scanner){
        System.out.println("Enter port");
        while (true) {
            try {
                port = Integer.valueOf(scanner.nextLine());
                if (port > 1024 && port < 65535)
                    break;
                else
                    throw new NumberFormatException(" ");
            } catch (NumberFormatException ex) {
                System.out.println("Wrong, try again");
            } catch (NoSuchElementException e) {
                System.out.println("Emergency exit");
            }
        }
        return port;
    }

    public static void main(String[] args) {

        //getting filename
//        try {
//            Map<String, String> env = System.getenv();
//            fileName[0] = env.get("laba5");
//            //test
//            //fileName[0] = "C:/Users/PK/Desktop/Code/Java/Lab6/file.csv";
//            if (fileName[0] == null) {
//                System.out.println("Set an environment variable named \"laba5\"");
//                System.exit(-1);
//            }
//        } catch (Exception e){
//            System.out.println("Something's wrong with the file");
//        }

        //creating
        Ship ship = new Ship();
        try{
            //filling
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName[0]));
            String content = fileReader.lines().collect(Collectors.joining("\n"));
            fileReader.close();
            Loading.upload(content, ship);
        } catch (FileNotFoundException e){
            System.out.println("File is unreachable");
        } catch (Exception e){
            System.out.println("Something's wrong with the file");
        }

        //closing
        Runtime.getRuntime().addShutdownHook(new Thread(()->  {
            ship.save(fileName[0]);
        }));

        //initialization of COOL GUYS
        Scanner scanner = new Scanner(System.in);
        ServerSocketChannel ssc = null;
        Selector selector = null;
        Integer port = null;

        //waiting for port to be entered
        port = readPort(port,scanner);

        savefile = fileName[0];
//        System.out.println("Enter a filename, where to save");
//        System.out.println("Write \"ABIBASS\" to save to default file (file.csv)");
//        while (true){
//            try{
//                savefile = scanner.nextLine().trim();
//                if (savefile=="ABIBASS") {
//                    savefile = fileName[0];
//                }
//                break;
//            } catch (Exception e){
//                System.out.println("Wrong, try again");
//            }
//        }

        //starting server, getting ready to connect, creating selector
        Map<Integer, SocketChannel> readQueueSet = new ConcurrentHashMap<>();
        try{

            ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(port));
            selector = Selector.open();
            ssc.configureBlocking(false);
            ssc.register(selector,SelectionKey.OP_ACCEPT);

            System.out.println("Server is alive....finally\n");
        } catch (IOException e){
            System.exit(-1);
        }

        //some cool network sex B)
        while (true) {
            try {
                if (selector.select() == 0){
                    continue;
                }
                Set keys = selector.selectedKeys();
                Iterator it = keys.iterator();
                //waiting for input
                while (it.hasNext()) {
                    SelectionKey sKey = (SelectionKey) it.next();

                    if (sKey.isAcceptable()) {
                        try {
                            SocketChannel sc = ssc.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                            System.out.println("Client connected");
                        } catch (IOException e) {
                            System.out.println("Unable to accept server: " + e.getMessage());
                            sKey.cancel();
                        }
                        it.remove();
                    }
                    if (sKey.isReadable()) {
                        if (sKey.channel() instanceof SocketChannel) {
                            SocketChannel readChannel = (SocketChannel) sKey.channel();
                            //checking if the channel is already taken
                            if (!readQueueSet.containsKey(readChannel.hashCode())) {
                                readQueueSet.put(readChannel.hashCode(), readChannel);
                                Thread thread = new Thread(new ReadClient(readChannel,ship,readQueueSet));
                                thread.start();
                                it.remove();
                                if (ReadClient.getError() || ReadClient.getClientDead()) {
                                    thread.interrupt();
                                    sKey.cancel();
                                    ReadClient.setError(false);
                                    ReadClient.setClientDead(false);
                                }

                            }
                        }
                    }
                }
                keys.clear();
            } catch (IOException e) {
                System.out.println("Connection error");
            }
        }

    }
}
