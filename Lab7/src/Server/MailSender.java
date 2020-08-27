package Server;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class MailSender {
    public static void send(String email,String login, String pass) {
        File config = new File("config");
        try {
            Scanner configReader = new Scanner(config);
            final String username = configReader.nextLine();
            final String password = configReader.nextLine();

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() { return new PasswordAuthentication(username, password);
                                }
                            });
            MimeMessage message;
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tereshd00@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Password");
            message.setText("Your login to DB: " + login + "\nAnd your password: " + pass);
            Transport.send(message);
            System.out.println("Pisun was sent");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e){
            System.out.println("Config error");
        }
    }
}

