package Server;

import java.util.Random;

public class Password {
    public static String generate(int lenght){
        String all="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String s="";
        Random random=new Random();
        for (int i=0; i<lenght; i++)
            s=s+all.charAt(random.nextInt(all.length()));
        return s;
    }
}
