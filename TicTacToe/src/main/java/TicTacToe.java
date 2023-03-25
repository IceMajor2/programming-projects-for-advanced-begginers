
import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        char[][] board = new char[3][3];
        render(board);

        char player = 'X';
        int moves = 0;
        while (moves != 9 && winner(board, moves) == '\0') {
            makeMove(board, player);
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

    public static void makeMove(char[][] board, char player) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(player + "'s move: ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            scanner.nextLine();
            if (x < 0 || x > 2 || y < 0 || y > 2) {
                System.out.println("Stick within the board.");
                continue;
            }
            if (board[x][y] != '\0') {
                System.out.println("Place is occupied.");
                continue;
            }
            board[x][y] = player;
            break;
        }
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
        if(moves < 5) {
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
