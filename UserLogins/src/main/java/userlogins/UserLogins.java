package userlogins;

import java.util.Scanner;
import java.util.Map;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class UserLogins {

    public static Map<String, String> credentialsDb
            = Map.of("ping", getSHA256("pong"), "ching", getSHA256("chang"));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (credentialsValid(username, password)) {
            System.out.println("Secret world accessed");
        } else {
            System.out.println("Fuck off you");
        }
    }

    public static boolean credentialsValid(String usr, String pass) {
        return credentialsDb.containsKey(usr)
                && credentialsDb.containsValue(getSHA256(pass));
    }

    public static byte[] bytesSHA256(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA-256");
            return md5.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String getSHA256(String password) {
        byte[] bytesSHA = bytesSHA256(password);
        BigInteger number = new BigInteger(1, bytesSHA);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        
        while(hexString.length() < 64) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}
