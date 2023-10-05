import java.io.IOException;

public class GameOfLife {

    public static void main(String[] args) {
        UserInterface UI = new UserInterface();
        try {
            UI.run();
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }
}