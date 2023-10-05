
import java.util.Random;

public class LifeBoard {

    private Cell[][] board;

    public LifeBoard(int height, int width) {
        this.board = this.randomState(height, width);
    }

    public LifeBoard(Cell[][] board) {
        this.board = board;
    }

    public int aliveNeighbors(Cell cell) {
        int aliveNeighbors = 0;
        int x = cell.getX();
        int y = cell.getY();
        for (int a = x - 1; a < x + 2; a++) {
            for (int b = y - 1; b < y + 2; b++) {
                if (a == x && b == y) {
                    continue;
                }
                try {
                    Cell neighbor = board[a][b];
                    if (neighbor.getStatus() == 1) {
                        aliveNeighbors++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        return aliveNeighbors;
    }

    private Cell[][] randomState(int height, int width) {
        Cell[][] board = new Cell[height][width];
        Random random = new Random();
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                int cellStatus = random.nextInt(2) == 0 ? -1 : 1;
                Cell cell = new Cell(i, y, cellStatus);
                board[i][y] = cell;
            }
        }
        return board;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public Cell[][] nextBoardState() {
        Cell[][] nextBoard = new Cell[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                Cell cell = new Cell(board[i][y]);
                int aliveNeighbors = aliveNeighbors(cell);
                if (cell.getStatus() == 1) {
                    if (aliveNeighbors <= 1 || aliveNeighbors > 3) {
                        cell.setStatus(-1);
                        nextBoard[i][y] = cell;
                    }
                    nextBoard[i][y] = cell;
                    continue;
                }
                if (aliveNeighbors == 3) {
                    cell.setStatus(1);
                }
                nextBoard[i][y] = cell;
            }
        }
        setBoard(nextBoard);
        return nextBoard;
    }

    public Cell get(int i, int y) {
        return this.board[i][y];
    }
}