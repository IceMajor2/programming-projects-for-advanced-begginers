package photomosaics;
/*
TODO:
    - The database should be loading and/or deleting only those files that need to be. There's no point in writing the whole yml file.
 */
public class Photomosaics {

    public static void main(String[] args) {
        DataHandler.load();
        UserInterface UI = new UserInterface();
        UI.run();
    }
}
