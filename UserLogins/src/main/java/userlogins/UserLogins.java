package userlogins;

import logic.Database;

public class UserLogins {

    public static Database db = new Database();

    public static void main(String[] args) {

        UserInterface UI = new UserInterface();
        UI.run();
    }
}
