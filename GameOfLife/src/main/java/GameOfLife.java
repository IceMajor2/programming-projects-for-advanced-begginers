
import java.util.Random;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class GameOfLife {

    public static int[][] BOARD = null;

    public static void main(String[] args) {
        System.out.print("Open LIFE from file? (hit ENTER for random) ");
        Scanner scanner = new Scanner(System.in);
        String file = scanner.nextLine();
        if (file.isEmpty()) {
            System.out.println("Dimensions of the board? (ex: 64 32) ");
            String dimensions = scanner.nextLine();
            int height = Integer.valueOf(dimensions.split(" ")[0]);
            int width = Integer.valueOf(dimensions.split(" ")[1]);
            BOARD = randomState(height, width);
        } else {
            try {
                BOARD = loadStateFromTXT(file);
            } catch (IOException e) {
                System.out.println("Did not read file.");
            }
        }
        if (BOARD != null) {
            System.out.print("How often should board be updated? (in ms) ");
            long delay = scanner.nextLong();
            runForever(delay);
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
        int[][] nextBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                int cell = board[i][y];
                int aliveNeighbors = aliveNeighbors(board, i, y);
                if (cell == 1) {
                    if (aliveNeighbors <= 1 || aliveNeighbors > 3) {
                        cell = -1;
                        nextBoard[i][y] = cell;
                    }
                    nextBoard[i][y] = cell;
                    continue;
                }
                if (aliveNeighbors == 3) {
                    cell = 1;
                    nextBoard[i][y] = cell;
                }
            }
        }
        return nextBoard;
    }

    public static int aliveNeighbors(int[][] board, int x, int y) {
        int cell = board[x][y];
        int aliveNeighbors = 0;
        for (int pointer = 0, a = x - 1; a < x + 2; a++) {
            for (int b = y - 1; b < y + 2; b++, pointer++) {
                if (a == x && b == y) {
                    continue;
                }
                try {
                    int neighbor = board[a][b];
                    if (neighbor == 1) {
                        aliveNeighbors++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        return aliveNeighbors;
    }

    public static void clearConsole(String... args) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // this lags as shit
    }

    public static int[][] loadStateFromTXT(String file) throws IOException {
        StringBuilder fileContent = new StringBuilder("");
        Scanner scanner = new Scanner(new File(
                "E:\\Dokumenty\\Projekty obecne\\[CODE]\\Programy\\PPfAB\\GameOfLife\\"
                + file + ".txt"));
        while (scanner.hasNextLine()) {
            fileContent.append(scanner.nextLine());
            fileContent.append("\n");
        }

        String[] strBoard = fileContent.toString().split("\n");
        int[][] board = new int[strBoard.length][strBoard[0].length()];

        for (int i = 0; i < strBoard.length; i++) {
            for (int y = 0; y < strBoard[i].length(); y++) {
                board[i][y] = strBoard[i].charAt(y) == '1' ? 1 : -1;
            }
        }
        return board;
    }

    public static void runForever(long delay) {
        while (true) {
            render(BOARD);
            BOARD = nextBoardState(BOARD);
            try {
                Thread.sleep(delay);
                //clearConsole();
            } catch (InterruptedException e) {
                System.out.println(e);
            } //catch(IOException e) {
            //  System.out.println(e);
            //}
        }
    }

    public static void runForever() {
        runForever(1000);
    }

}
