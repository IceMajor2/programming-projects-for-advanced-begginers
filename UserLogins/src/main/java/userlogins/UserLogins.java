package userlogins;

import userlogins.logic.Database;
import static java.io.File.separator;
import userlogins.logic.AppLogic;

public class UserLogins {
    
    public static final String DB_NAME = AppLogic.databaseFileName();
    public static String PATH_TO_DB = "database" + separator + DB_NAME;
    public static Database db;

    public static void main(String[] args) {
        db = new Database();
        UserInterface UI = new UserInterface();
        UI.run();
    }
}
