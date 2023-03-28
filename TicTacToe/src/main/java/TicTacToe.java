
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class TicTacToe {

    public static void main(String[] args) {
        char[][] board = new char[3][3];
        char[] players = players();
        char player01 = players[0];
        char player02 = players[1];
        int times = times();
        if (times == 1) {
            char winner = play(board, player01, player02);
            printStatistics(winner);
        } else if (times >= 2) {
            int[] stats = play(board, player01, player02, times);
            printStatistics(stats[0], stats[1], stats[2]);
        }
    }

    public static char play(char[][] board, char player01, char player02) {
        render(board);
        char currentPlayer = 'X';
        while (winner(board) == '\0' && emptySlotsCount(board) != 0) {
            switch (currentPlayer) {
                case 'X':
                    switch (player01) {
                        case '1':
                            int[] cords = humanPlayer(board, 'X');
                            board = makeMove(board, cords, 'X');
                            break;
                        case '2':
                            cords = randomAI(board, 'X');
                            board = makeMove(board, cords, 'X');
                            break;
                        case '3':
                            cords = leastIntelligentAI(board, 'X');
                            board = makeMove(board, cords, 'X');
                            break;
                        case '4':
                            cords = quiteIntelligentAI(board, 'X');
                            board = makeMove(board, cords, 'X');
                            break;
                        case '5':
                            cords = minimaxAI(board, 'X');
                            board = makeMove(board, cords, 'X');
                            break;
                    }
                    break;
                case 'O':
                    switch (player02) {
                        case '1':
                            int[] cords = humanPlayer(board, 'O');
                            board = makeMove(board, cords, 'O');
                            break;
                        case '2':
                            cords = randomAI(board, 'O');
                            board = makeMove(board, cords, 'O');
                            break;
                        case '3':
                            cords = leastIntelligentAI(board, 'O');
                            board = makeMove(board, cords, 'O');
                            break;
                        case '4':
                            cords = quiteIntelligentAI(board, 'O');
                            board = makeMove(board, cords, 'O');
                            break;
                        case '5':
                            cords = minimaxAI(board, 'O');
                            board = makeMove(board, cords, 'O');
                            break;
                    }
                    break;
            }
            render(board);
            currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
        }
        return winner(board);
    }

    public static int[] play(char[][] board, char player01, char player02, int times) {
        int index = 1;
        int draws = 0;
        int xWins = 0;
        int oWins = 0;
        while (index <= times) {
            int winner = play(board, player01, player02);
            if (winner == 'X') {
                xWins++;
            } else if (winner == 'O') {
                oWins++;
            } else {
                draws++;
            }
            index++;
        }
        return new int[]{xWins, oWins, draws};
    }

    public static void printStatistics(int xWins, int oWins, int draws) {
        System.out.println("X won " + xWins + " times");
        System.out.println("O won " + oWins + " times");
        System.out.println("Draw happened " + draws + " times");
    }

    public static void printStatistics(char winner) {
        if (winner == '\0') {
            System.out.println("DRAW");
            return;
        }
        System.out.println(winner + " wins");
    }

    public static int times() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many times would you like to play?");
        int times = Integer.valueOf(scanner.nextLine());
        return times;
    }

    public static void printAllPlayers() {
        System.out.println("[1] Human player");
        System.out.println("[2] Random player");
        System.out.println("[3] AI that finds winning moves");
        System.out.println("[4] AI that finds winning and losing moves");
        System.out.println("[5] The Unbeateable MINIMAX");
    }

    public static char[] players() {
        Scanner scanner = new Scanner(System.in);
        char[] twoPlayers = new char[2];

        while (true) {
            System.out.println("What is 'X'?");
            printAllPlayers();
            String input = scanner.nextLine();
            if (input.equals("1") || input.equals("2")
                    || input.equals("3") || input.equals("4")
                    || input.equals("5")) {
                twoPlayers[0] = input.charAt(0);
                break;
            }
            System.out.println("Wrong input. Try again.");
        }
        while (true) {
            System.out.println("What is 'O'?");
            printAllPlayers();
            String input = scanner.nextLine();
            if (input.equals("1") || input.equals("2")
                    || input.equals("3") || input.equals("4")
                    || input.equals("5")) {
                twoPlayers[1] = input.charAt(0);
                break;
            }
            System.out.println("Wrong input. Try again.");
        }
        return twoPlayers;
    }

    public static int[] humanPlayer(char[][] board, char player) {
        System.out.print("Give your move [" + player + "]: ");
        int[] cords = getMove();
        while (!moveValid(board, cords)) {
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

    public static int[] minimaxAI(char[][] board, char player) {
        int maxScore = -99;
        int[] bestMove = null;

        for (int[] move : getEmptySlots(board)) {
            char[][] hypoBoard = makeMove(board, move, player);
            char opponent = player == 'X' ? 'O' : 'X';
            int minimaxScore = minimaxScore(hypoBoard, opponent, player);
            if (minimaxScore > maxScore) {
                maxScore = minimaxScore;
                bestMove = move;
            }
        }
        if (maxScore == 0) {
            
        }
        return bestMove;
    }

    public static int minimaxScore(char[][] board, char player, char AI) {
        char winner = winner(board);
        if (winner == AI) {
            return 10;
        } else if (winner != AI && winner != '\0') {
            return -10;
        } else if (winner == '\0' && emptySlotsCount(board) == 0) {
            return 0;
        }

        int[][] legalMoves = getEmptySlots(board);
        int[] scores = new int[legalMoves.length];

        int i = 0;
        for (int[] move : legalMoves) {
            char[][] hypoBoard = makeMove(board, move, player);
            char opponent = player == 'X' ? 'O' : 'X';
            int score = minimaxScore(hypoBoard, opponent, AI);
            if(inTheCorner(move)) {
                score += 1;
            }
            scores[i] = score;
            i++;
        }
        if (player == AI) {
            int max = Arrays.stream(scores).max().getAsInt();
            return max;
        }
        int min = Arrays.stream(scores).min().getAsInt();
        return min;
    }
    
    public static boolean inRow(char[][] board, char player, int[] move) {
        char[][] hypoBoard = makeMove(board, move, player);
        for(int i = 0; i < board.length; i++) {
            for(int y = 0; y < board[i].length; y++) {
                
            }
        }
        return false;
    }
    
    public static boolean inTheCorner(int[] move) {
        if(move[0] == 0 && move[1] == 0) {
            return true;
        }
        if(move[0] == 2 && move[1] == 0) {
            return true;
        }
        if(move[0] == 0 && move[1] == 2) {
            return true;
        }
        return move[0] == 2 && move[1] == 2;
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
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2]
                    && board[i][0] != '\0') {
                return board[i][0];
            }
        }
        return '\0';
    }

    public static char verticalWinner(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                if (board[0][y] == board[1][y] && board[0][y] == board[2][y]
                        && board[0][y] != '\0') {
                    return board[0][y];
                }
            }
        }
        return '\0';
    }

    public static char diagonalWinner(char[][] board) {
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2]
                && board[0][0] != '\0') {
            return board[0][0];
        }
        if (board[0][2] == board[1][1] && board[0][2] == board[2][0]
                && board[0][2] != '\0') {
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
