
import java.util.Scanner;
import java.util.Random;

public class TicTacToe {

    public static void main(String[] args) {
        char[][] board = new char[3][3];
        char player01 = 'X';
        char player02 = 'O';
        render(board);

        char currPlayer = 'X';
        while (winner(board) == '\0' && emptySlotsCount(board) != 0) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (currPlayer == player01) {
                int[] cords = quiteIntelligentAI(board, currPlayer);
                board = makeMove(board, cords, currPlayer);
            } else if (currPlayer == player02) {
                int[] cords = humanPlayer(board, currPlayer);
                board = makeMove(board, cords, currPlayer);
            }
            render(board);
            currPlayer = currPlayer == player01 ? player02 : player01;
        }
        char winner = winner(board);
        if (winner == '\0') {
            System.out.println("DRAW");
        } else {
            System.out.println(winner + " WINS");
        }
    }

    public static int[] humanPlayer(char[][] board, char player) {
        System.out.print("Give your move [" + player + "]: ");
        int[] cords = getMove();
        while(!moveValid(board, cords)) {
            System.out.print("Try again: ");
            cords = getMove();
        }
        return cords;
    }

    public static int[] randomAI(char[][] board, char player) {
        int[][] emptySlots = getEmptySlots(board);
        Random random = new Random();
        int[] cords = emptySlots[random.nextInt(emptySlots.length)];
        return cords;
    }
    
    public static int[] leastIntelligentAI(char[][] board, char player) {
        int[] winningMove = winningMove(board, player);
        if (winningMove != null) {
            return winningMove;
        }
        return randomAI(board, player);
    }

    public static int[] quiteIntelligentAI(char[][] board, char player) {
        int[] winningMove = winningMove(board, player);
        if (winningMove != null) {
            return winningMove;
        }
        char opponent = player == 'X' ? 'O' : 'X';
        int[] losingMove = winningMove(board, opponent);
        if (losingMove != null) {
            return losingMove;
        }
        return randomAI(board, player);
    }
    
    public static int[] winningMove(char[][] board, char player) {
        int[] closeHorizontal = winningHorizontalMove(board, player);
        if (closeHorizontal != null) {
            return closeHorizontal;
        }
        int[] closeVertical = winningVerticalMove(board, player);
        if (closeVertical != null) {
            return closeVertical;
        }
        int[] closeDiagonal = winningDiagonalMove(board, player);
        if (closeDiagonal != null) {
            return closeDiagonal;
        }
        return null;
    }

    public static int[] getMove() {
        Scanner scanner = new Scanner(System.in);
        int[] cords = new int[2];
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        cords[0] = y;
        cords[1] = x;
        return cords;
    }

    public static boolean moveValid(char[][] board, int[] cords) {
        int x = cords[0];
        int y = cords[1];

        if (x < 0 || x > 2 || y < 0 || y > 2) {
            System.out.println("Stick within the board.");
            return false;
        }
        if (board[x][y] != '\0') {
            System.out.println("Place is occupied.");
            return false;
        }
        return true;
    }

    public static char[][] makeMove(char[][] board, int[] cords, char player) {
        char[][] updatedBoard = new char[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                if (i == cords[0] && y == cords[1]) {
                    updatedBoard[i][y] = player;
                    continue;
                }
                updatedBoard[i][y] = board[i][y];
            }
        }
        return updatedBoard;
    }

    public static void render(char[][] board) {
        System.out.println("   0  1  2");
        System.out.println("   - - - -");

        for (int i = 0; i < board.length; i++) {
            System.out.print(i + " | ");
            for (int y = 0; y < board[i].length; y++) {
                if (board[i][y] == '\0') {
                    System.out.print("  ");
                    continue;
                }
                System.out.print(board[i][y] + " ");
            }
            System.out.println("|");
        }
        System.out.println("   - - - -");
    }

    public static char winner(char[][] board) {
        if (emptySlotsCount(board) > 4) {
            return '\0';
        }
        char horizontalWinner = horizontalWinner(board);
        if (horizontalWinner != '\0') {
            return horizontalWinner;
        }
        char verticalWinner = verticalWinner(board);
        if (verticalWinner != '\0') {
            return verticalWinner;
        }
        char diagonalWinner = diagonalWinner(board);
        if (diagonalWinner != '\0') {
            return diagonalWinner;
        }
        return '\0';
    }

    public static char horizontalWinner(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return board[i][0];
            }
        }
        return '\0';
    }

    public static char verticalWinner(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                if (board[0][y] == board[1][y] && board[0][y] == board[2][y]) {
                    return board[0][y];
                }
            }
        }
        return '\0';
    }

    public static char diagonalWinner(char[][] board) {
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return board[0][2];
        }
        return '\0';
    }

    public static int[] winningHorizontalMove(char[][] board, char player) {
        for (int i = 0; i < board.length; i++) {
            int amountOfSame = 0;
            int empty = 0;
            int[] emptyCords = null;
            for (int y = 0; y < board.length; y++) {
                if (board[i][y] == '\0') {
                    emptyCords = new int[]{i, y};
                    empty++;
                    continue;
                }
                if (empty == 2) {
                    break;
                }
                if (board[i][y] == player) {
                    amountOfSame++;
                }
            }
            if (amountOfSame + empty == 3 && empty == 1) {
                return emptyCords;
            }
        }
        return null;
    }

    public static int[] winningVerticalMove(char[][] board, char player) {
        for (int i = 0; i < board.length; i++) {
            int amountOfSame = 0;
            int empty = 0;
            int[] emptyCords = null;
            for (int y = 0; y < board.length; y++) {
                if (board[y][i] == '\0') {
                    emptyCords = new int[]{y, i};
                    empty++;
                    continue;
                }
                if (empty == 2) {
                    break;
                }
                if (board[y][i] == player) {
                    amountOfSame++;
                }
            }
            if (amountOfSame + empty == 3 && empty == 1) {
                return emptyCords;
            }
        }
        return null;
    }

    public static int[] winningDiagonalMove(char[][] board, char player) {
        int empty = 0;
        int amountOfSame = 0;
        int[] emptyCords = null;
        for (int i = 0, y = 0; i < 3 && y < 3; i++, y++) {
            if (board[i][y] == '\0') {
                emptyCords = new int[]{i, y};
                empty++;
                continue;
            }
            if (empty == 2) {
                break;
            }
            if (board[i][y] == player) {
                amountOfSame++;
            }
        }
        if (amountOfSame + empty == 3 && empty == 1) {
            return emptyCords;
        }
        empty = 0;
        amountOfSame = 0;
        emptyCords = null;
        for (int i = 0, y = 2; i < 3 && y < 3; i++, y--) {
            if (board[i][y] == '\0') {
                emptyCords = new int[]{i, y};
                empty++;
                continue;
            }
            if (empty == 2) {
                break;
            }
            if (board[i][y] == player) {
                amountOfSame++;
            }
        }
        if (amountOfSame + empty == 3 && empty == 1) {
            return emptyCords;
        }
        return null;
    }

    public static int[][] getEmptySlots(char[][] board) {
        int[][] emptySlots = new int[emptySlotsCount(board)][2];
        int pointer = 0;
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                char slot = board[i][y];
                if (slot == '\0') {
                    emptySlots[pointer][0] = i;
                    emptySlots[pointer][1] = y;
                    pointer++;
                }
            }
        }
        return emptySlots;
    }

    public static int emptySlotsCount(char[][] board) {
        int count = 0;
        for (char[] row : board) {
            for (char spot : row) {
                if (spot == '\0') {
                    count++;
                }
            }
        }
        return count;
    }
}
