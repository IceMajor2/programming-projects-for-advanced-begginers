package userlogins;

import userlogins.logic.Database;

import static java.io.File.separator;

import userlogins.logic.AppLogic;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserLogins {

    public static final String DB_NAME = AppLogic.databaseFileName();
    public static String PATH_TO_DB = "database" + separator + DB_NAME;
    public static Database db;

    public static void main(String[] args) {
        AppLogic.createDatabaseDirIfNotExists();
        db = new Database();
        UserInterface UI = new UserInterface();
        UI.run();
    }
}
