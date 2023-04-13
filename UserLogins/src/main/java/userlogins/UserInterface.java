package userlogins;

import static userlogins.UserLogins.db;
import java.util.Scanner;
import logic.AppLogic;

public class UserInterface {

    private Scanner scanner;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        if (db.exists() && db.allRows() != 0) {
            databaseDeleteChoice();
        }
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
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
            if(beenDeleted) {
                System.out.println("Database successfully deleted.");
            } else {
                System.out.println("Couldn't delete database.");
            }
        }
        System.out.println();
    }
}
