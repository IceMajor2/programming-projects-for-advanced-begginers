package userlogins;

import java.util.Scanner;
import java.util.*;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import org.yaml.snakeyaml.Yaml;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class UserLogins {

    public static List<LinkedHashMap> credentials = loadConfig();

    public static void main(String[] args) {
        
        // runUI();
    }

    public static List<LinkedHashMap> loadConfig() {
        try {
            InputStream inputStream = new FileInputStream(new File("config.yaml"));
            Yaml yaml = new Yaml();
            var credentials = (List<LinkedHashMap>) yaml.load(inputStream);
            return credentials;
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    public static void runUI() {
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
        return false;
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

        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}
