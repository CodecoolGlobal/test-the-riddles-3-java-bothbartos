package tests.Utils;

import java.util.Random;

public class Utils {
    private static final Random rand = new Random();


    public static String createRandomUsername(){
        return "username" + rand.nextInt(10000) ;
    }

    public static String createRandomEmail(){
        return "email" + rand.nextInt(10000) + "@gmail.com";
    }

    public static String createRandomPassword(){
        return "password" + rand.nextInt(10000) ;
    }

    public static String getRandomNum(){return Integer.toString(rand.nextInt(10000));}
}
