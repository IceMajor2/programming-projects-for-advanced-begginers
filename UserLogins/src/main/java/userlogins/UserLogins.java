package userlogins;

import logic.Database;

public class UserLogins {

    public static Database db;

    public static void main(String[] args) {
        db = new Database();
        UserInterface UI = new UserInterface();
        UI.run();
    }
}
