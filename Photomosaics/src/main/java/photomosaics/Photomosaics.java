package photomosaics;
/*
TODO:
    - The database should be loading and/or deleting only those files that need to be. There's no point in writing the whole yml file.
    - IF I want to speed up making a photomosaic process, I could - when the user enters pixel group size - recalculate the avg RGB color for each image in a given size.
 */
public class Photomosaics {

    public static void main(String[] args) {
        DataHandler.load();
        UserInterface UI = new UserInterface();
        UI.run();
    }
}
