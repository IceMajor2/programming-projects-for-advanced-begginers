
import java.io.File;
import java.util.Scanner;
import java.io.IOException;

public class ProgramLogic {

    public Cell[][] loadStateFromTXT(String file) throws IOException {
        StringBuilder fileContent = new StringBuilder("");
        Scanner scanner = new Scanner(readFile(file));
        while (scanner.hasNextLine()) {
            fileContent.append(scanner.nextLine());
            fileContent.append("\n");
        }

        String[] strBoard = fileContent.toString().split("\n");
        Cell[][] board = new Cell[strBoard.length][strBoard[0].length()];

        for (int i = 0; i < strBoard.length; i++) {
            for (int y = 0; y < strBoard[i].length(); y++) {
                int cellStatus = strBoard[i].charAt(y) == '1' ? 1 : -1;
                Cell cell = new Cell(i, y, cellStatus);
                board[i][y] = cell;
            }
        }
        return board;
    }

    private void render(LifeBoard lBoard) {
        Cell[][] board = lBoard.getBoard();
        for (int i = 0; i < board[0].length * 2 + 3; i++) {
            System.out.print("-");
        }
        System.out.println("");
        for (Cell[] row : board) {
            System.out.print("| ");
            for (Cell cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println("|");
        }
        for (int i = 0; i < board[0].length * 2 + 3; i++) {
            System.out.print("-");
        }
        System.out.println("");
    }

    public void runForever(LifeBoard board, long delay) {
        if (board == null) {
            throw new NullPointerException();
        }
        while (true) {
            render(board);
            board.nextBoardState();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    public void runForever(LifeBoard board) {
        runForever(board, 1000);
    }

    private File readFile(String filename) {
        String targetClassesDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String rootDir = new File(targetClassesDir).getParentFile().getParentFile().getAbsolutePath();
        File boardsDir = new File(rootDir, "boards");
        String path = String.format("%s%s%s", boardsDir.getAbsolutePath(), File.separator, filename);
        System.out.println(path);
        return new File(path);
    }
}