
import java.util.Random;
import java.io.IOException;

public class GameOfLife {

    public static void main(String[] args) {
        int[][] board = randomState(21, 15);
        while(true) {
            render(board);
            board = nextBoardState(board);
            try {
                Thread.sleep(300);
                clearConsole();
            } catch(InterruptedException e) {
                System.out.println(e);
            } catch(IOException e) {
                System.out.println(e);
            }
            
        }
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
    
    public static void clearConsole(String ... args) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
    
}
