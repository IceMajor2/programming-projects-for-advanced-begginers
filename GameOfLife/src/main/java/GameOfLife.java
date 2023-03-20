
import java.util.Random;

public class GameOfLife {

    public static void main(String[] args) {
        int[][] board = randomState(6, 3);
    }
    
    public static int[][] randomState(int height, int width) {
        int[][] board = new int[height][width];
        Random random = new Random();
        for(int[] row : board) {
            for(int cell : row) {
                cell = random.nextInt(2);
                System.out.println(cell);
            }
        }
        return board;
    }
}
