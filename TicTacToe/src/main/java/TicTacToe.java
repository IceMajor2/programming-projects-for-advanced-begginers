import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        char[][] board = new char[3][3];
        render(board);
        
        char player = 'X';
        while(true) {
            makeMove(board, player);
            render(board);
            player = (player == 'X') ? 'O' : 'X';
        }
    }
    
    public static void makeMove(char[][] board, char player) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("Your move (ex: 1 2): ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            scanner.nextLine();
            if(x < 0 || x > 2 || y < 0 || y > 2) {
                System.out.println("Stick within the board.");
                continue;
            }
            if(board[x][y] != '\0') {
                System.out.println("Place is occupied.");
                continue;
            }
            board[x][y] = player;
            break;
        }
    }
    
    public static void render(char[][] board) {
        System.out.println("---------");
        for(char[] row : board) {
            System.out.print("| ");
            for(char place : row) {
                if(place == '\0') {
                    System.out.print("  ");
                    continue;
                }
                System.out.print(place + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }
}
