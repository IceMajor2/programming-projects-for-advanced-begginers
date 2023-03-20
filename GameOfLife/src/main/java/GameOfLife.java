
import java.util.Random;

public class GameOfLife {

    public static void main(String[] args) {
        
    }

    public static int[][] randomState(int height, int width) {
        int[][] board = new int[height][width];
        Random random = new Random();
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                board[i][y] = random.nextInt(2) == 0 ? -1 : 1;
            }
        }
        return board;
    }

    public static void render(int[][] board) {
        for (int i = 0; i < board[0].length * 2 + 3; i++) {
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

        for (int i = 0; i < board[0].length * 2 + 3; i++) {
            System.out.print("-");
        }
        System.out.println("");
    }

    public static int[][] nextBoardState(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                int cell = board[i][y];
                int aliveNeighbors = aliveNeighbors(board, i, y);
                if(cell == 1) {
                    if(aliveNeighbors <= 1 || aliveNeighbors > 3) {
                        cell = 0;
                        board[i][y] = cell;
                    }
                    continue;
                }
                if(aliveNeighbors == 3) {
                    cell = 1;
                    board[i][y] = cell;
                }
            }
        }
        return board;
    }

    public static int aliveNeighbors(int[][] board, int x, int y) {
        int cell = board[x][y];
        //int[] neighbors = new int[8];
        int aliveNeighbors = 0;
        for (int pointer = 0, a = x - 1; a < x + 2; a++) {
            for (int b = y - 1; b < y + 2; b++, pointer++) {
                if(a == x && b == y) {
                    continue;
                }
                try {
                    int neighbor = board[a][b];
                    if(neighbor == 1) {
                        aliveNeighbors++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        return aliveNeighbors;
    }
    
    /*private static int nextFreeIndex(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == 0) {
                return i;
            }
        }
        return -1;
    }*/
}
