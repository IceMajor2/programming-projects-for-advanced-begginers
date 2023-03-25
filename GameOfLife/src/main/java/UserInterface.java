
import java.util.Scanner;
import java.io.IOException;

public class UserInterface {

    private LifeBoard board;
    private Scanner scanner;
    private ProgramLogic logic;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.logic = new ProgramLogic();
    }

    public void run() {
        System.out.print("Open LIFE from file? (hit ENTER for random) ");
        String file = scanner.nextLine();
        if(file.equals("t")) {
            logic.runTest();
        }
        if (file.isEmpty()) {
            System.out.println("Dimensions of the board? (ex: 64 32) ");
            String dimensions = scanner.nextLine();
            int height = Integer.valueOf(dimensions.split(" ")[0]);
            int width = Integer.valueOf(dimensions.split(" ")[1]);
            GameOfLife.board = new LifeBoard(height, width);
            this.board = GameOfLife.board;
        } else {
            try {
                board = new LifeBoard(logic.loadStateFromTXT(file));
            } catch (IOException e) {
                System.out.println("Did not read file.");
                return;
            }
        }
        System.out.print("How often should board be updated? (in ms) ");
        long delay = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Chances for a random revival of a cell? [0-1] ");
        String revivalInput = scanner.nextLine();
        if (!revivalInput.isEmpty()) {
            board.setRevivalChance(Double.valueOf(revivalInput));
        }
        logic.runForever(board, delay);
        
    }
}
