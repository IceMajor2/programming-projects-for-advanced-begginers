package userlogins;

import java.util.Scanner;
import java.util.Map;

public class UserLogins {

    public static Map<String, String> credentialsDb
            = Map.of("ping", "pong", "ching", "chang");

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
                && credentialsDb.containsValue(pass);
    }
}
