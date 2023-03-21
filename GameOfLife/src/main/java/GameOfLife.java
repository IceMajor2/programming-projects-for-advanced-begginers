import java.io.IOException;

/*
TODO:
- enable user to determine the game of life's rules
- create killer cell (natural killers? ^^)
- cell can resurrect itself
*/

public class GameOfLife {

    public static void main(String[] args) throws InterruptedException {
        UserInterface UI = new UserInterface();
        try {
           UI.run(); 
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
