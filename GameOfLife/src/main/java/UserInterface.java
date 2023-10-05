
import java.util.Scanner;
import java.io.IOException;

public class UserInterface {

    private Scanner scanner;
    private ProgramLogic logic;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.logic = new ProgramLogic();
    }

    public void run() throws IOException {
        System.out.print("Open LIFE from file? (hit ENTER for random) ");
        String file = scanner.nextLine();
        LifeBoard board = null;
        if (file.isEmpty()) {
            System.out.println("Dimensions of the board? (ex: 64 32) ");
            String dimensions = scanner.nextLine();
            int height = Integer.valueOf(dimensions.split(" ")[0]);
            int width = Integer.valueOf(dimensions.split(" ")[1]);
            board = new LifeBoard(height, width);
        } else {
            try {
                board = new LifeBoard(logic.loadStateFromTXT(file));
            } catch (IOException e) {
                System.out.println("Did not read file.");
            }
        }
        System.out.print("How often should board be updated? (in ms) ");
        long delay = scanner.nextLong();
        logic.runForever(board, delay);
    }
}