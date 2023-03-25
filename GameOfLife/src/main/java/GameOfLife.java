/*
TODO:
- enable user to determine the game of life's rules
- create killer cell (natural killers? ^^)
- actually it makes more sense for THE CELL to become Jesus,
  the revival thus should not come from LifeBoard
*/

public class GameOfLife {

    public static LifeBoard board;
    
    public static void main(String[] args) {
        UserInterface UI = new UserInterface();
        UI.run();
    }
}
