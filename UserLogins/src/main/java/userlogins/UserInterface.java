package userlogins;

import static userlogins.UserLogins.db;
import java.util.Scanner;
import userlogins.logic.AppLogic;

public class UserInterface {

    private Scanner scanner;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        if (db.exists() && db.allRows() != 0) {
            databaseDeleteChoice();
        }
        System.out.println("1. Log in");
        System.out.println("2. Create new user");
        System.out.println("0. Exit");
        String choice = scanner.nextLine();
        if(choice.equals("0")) {
            return;
        }
        if (choice.equals("1")) {

        }
        if (choice.equals("2")) {
            String[] credentials = getCredentials();
            db.addUser(credentials[0], credentials[1]);
        }
    }

    public String[] getCredentials() {
        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            if(db.isUsernameTaken(username)) {
                System.out.println("The username is already taken!");
                continue;
            }
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            return new String[]{username, AppLogic.getSHA256(password)};
        }
    }

    public void databaseDeleteChoice() {
        System.out.println(String.format("Detected already existing database"
                + " of %d tables.", db.tablesCount()));
        for (String table : db.tableNames()) {
            System.out.print(String.format("%s (%d rows)\t",
                    table, db.rowCount(table)));
        }
        System.out.println();
        System.out.print("Delete database? [y/n] ");
        String input = scanner.nextLine();
        if ("y".equals(input.toLowerCase())) {
            boolean beenDeleted = db.recreateDatabase();
            if (beenDeleted) {
                System.out.println("Database successfully deleted.");
            } else {
                System.out.println("Couldn't delete database.");
            }
        }
        System.out.println();
    }
}
