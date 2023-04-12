package userlogins;

import java.util.Scanner;

public class UserLogins {

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
        return usr.equals("ping") && pass.equals("pong");
    }
}
