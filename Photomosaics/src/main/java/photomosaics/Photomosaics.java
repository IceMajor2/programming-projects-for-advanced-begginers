package photomosaics;
/*
TODO:
    - small and simple UI
    - there's no point in having more than 60x60 source images, think about sizing them down
    - The database should be loading and/or deleting only those files that need to be. There's no point in writing the whole yml file.
 */
public class Photomosaics {

    public static void main(String[] args) {
        DataHandler.load();
        UserInterface UI = new UserInterface();
        UI.run();
    }
}
