
import java.util.Random;

public class GameOfLife {

    public static void main(String[] args) {
        render(randomState(7, 4));
    }

    public static int[][] randomState(int height, int width) {
        int[][] board = new int[height][width];
        Random random = new Random();
        for(int i = 0; i < board.length; i++) {
            for(int y = 0; y < board[i].length; y++) {
                board[i][y] = random.nextInt(2);
            }
        }
        return board;
    }

    public static void render(int[][] board) {
        for(int i = 0; i < board[0].length * 2 + 3; i++) {
            System.out.print("-");
        }
        System.out.println("");
        for (int[] row : board) {
            System.out.print("| ");
            for (int cell : row) {
                if (cell == 1) {
                    System.out.print("# ");
                    continue;
                }
                System.out.print("  ");
            }
            System.out.println("|");
        }
        
        for(int i = 0; i < board[0].length * 2 + 3; i++) {
            System.out.print("-");
        }
    }
}
