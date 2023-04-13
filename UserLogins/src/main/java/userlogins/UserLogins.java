package userlogins;

import logic.Database;
import static java.io.File.separator;

public class UserLogins {

    public static String PATH_TO_DB = "database" + separator + "users.db";
    public static Database db;

    public static void main(String[] args) {
        db = new Database();
        UserInterface UI = new UserInterface();
        UI.run();
    }
}
