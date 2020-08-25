package Client;

import Default.Command;
import Default.MD2;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static Client.CCP.*;

public class Client {


    private static String host = "localhost";
    private static Integer port = 1599;
    private static String login;
    private static String password;
    private static String email;

    public static void main(String[] args) {
        System.out.println("Hello there! c:");

        try {
            while (true) {
                System.out.println("Write \"login\" to log in, or if you haven't got registered write \"register\".");
                Scanner scanner = new Scanner(System.in);
                String string = scanner.nextLine();
                if (string.equals("register")) {
                    System.out.println("Enter login");
                    login = scanner.nextLine();
                    System.out.println("Enter your email address. Password will be sent there.");
                    email = scanner.nextLine();
                    if (email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
                        Command cmd = new Command("register", null, null, login, null, email);
                        if ((send(cmd).substring(0,2).equals("OK"))) {
                            System.out.println("You're registered, now you may login");
                            continue;
                        } else System.out.println("Something went wrong, try again");
                    } else System.out.println("Incorrect email");
                } else if (string.equals("login")) {
                    System.out.println("Enter login");
                    login = scanner.nextLine();
                    System.out.println("Enter password");
                    password = scanner.nextLine();
                    Command cmd = new Command("login", null, null, login, password, null);
                    if ((send(cmd).substring(0,2).equals("OK"))) {
                        System.out.println("You're authorized");
                        break;
                    } else
                        System.out.println("Incorrect login or password");
                }
            }
        } catch (IOException e) {
            System.out.println("I/O Error");
        } catch (InvalidParameterException e){
            System.out.println("Invalid parameter: " + e.getMessage());
            e.printStackTrace();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(() ->
                    System.out.println("Closing...")
            ));
        } catch(Exception e){
            System.out.println("How");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Place \";\" at the end of the command to complete it\nType help; to see all commands");
            Command cmd;
            while (true) {
                try {
                    cmd = readCommand(br);
                    if (cmd != null) {
                        if (cmd.getName().equals("exit")) {
                            System.out.println("Bye then..");
                            System.exit(0);
                        }
                        if (cmd.getName().equals("login") || cmd.getName().equals("register")) {
                             System.out.println("God blames you, because you are already logged in");
                            continue;
                        }
                    cmd.setLogin(login);
                    cmd.setPassword(password);
                    //send(new Command("info",null,null,null,null,null));
                    send(cmd);
                    }
                } catch (IOException e) {
                    System.out.println("Server isn't available: " + e.getMessage());
                    System.out.println("Trying\n");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid parameter: " + e.getMessage());
                } catch (Exception e) {
                }
            }
        }

    public static String send(Command cmd) throws IOException{
        String res = "";
        try (SocketChannel channel = SocketChannel.open()) {
            channel.connect(new InetSocketAddress(host, port));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(cmd);

            ByteBuffer sendingBuffer = ByteBuffer.allocate(baos.size());
            sendingBuffer.put(baos.toByteArray());
            sendingBuffer.flip();
            channel.write(sendingBuffer);
            new Thread(() -> {
                try {
                    channel.write(sendingBuffer);
                } catch (IOException ignored) {
                }
            }).start();

            ByteBuffer buf = ByteBuffer.allocate(1024);
            channel.read(buf);
            buf.flip();
            channel.read(buf);
            buf.flip();
            buf.clear();
            while (channel.read(buf) > 0) {
                buf.flip();
                res = new String(buf.array());
                System.out.println(res);
                buf.clear();
            }
        } catch (UnresolvedAddressException e) {
            return "Cannot resolve address";
        } catch (UnknownHostException e) {
            return "Unknown host";
        } catch (SecurityException e) {
            return "Dont have access to server";
        } catch (ConnectException e) {
            return "Cannot connect to server";
        } catch (IOException e) {
            return "I/o error: " + e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String getLogin() {
        return login;
    }
    public static String getPassword() {
        return password;
    }
    public static String getEmail() {
        return email;
    }
}
