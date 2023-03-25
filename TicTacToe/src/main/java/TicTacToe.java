
import java.util.Scanner;
import java.util.Random;

public class TicTacToe {

    public static void main(String[] args) {
        char[][] board = new char[3][3];
        char player = readPlayer();
        render(board);
        
        int moves = 0;
        while (moves != 9 && winner(board, moves) == '\0') {
            System.out.print(player + "'s move: ");
            int[] cords = getMove();
            while(!moveValid(board, cords)) {
                System.out.print("Input again: ");
                cords = getMove();
            }
            board = makeMove(board, player, cords);
            moves++;
            render(board);
            player = (player == 'X') ? 'O' : 'X';
        }
        char winner = winner(board, moves);
        if (winner == '\0') {
            System.out.println("DRAW");
        } else {
            System.out.println(winner + " WINS");
        }
    }
    
    public static char readPlayer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Play as [def: X]: ");
        char input = scanner.next().charAt(0);
        char player = input == '\0' ? 'X' : input;
        return player;
    }

    public static int[] getMove() {
        Scanner scanner = new Scanner(System.in);
        int[] cords = new int[2];
        while (true) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            cords[0] = x;
            cords[1] = y;
            scanner.nextLine();
            break;
        }
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

    public static char[][] makeMove(char[][] board, char player, int[] cords) {
        char[][] updatedBoard = new char[3][3];
        for(int i = 0; i < board.length; i++) {
            for(int y = 0; y < board[i].length; y++) {
                if(i == cords[0] && y == cords[1]) {
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

    public static char winner(char[][] board, int moves) {
        if (moves < 5) {
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
}
