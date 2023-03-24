
import java.util.Random;

public class LifeBoard {

    private Cell[][] board;
    private double revivalChance;

    public LifeBoard(int height, int width) {
        this.board = this.randomState(height, width);
        this.revivalChance = 1.01;
    }

    public LifeBoard(Cell[][] board) {
        this.board = board;
        this.revivalChance = 1.01;
    }

    public Cell[] aliveNeighbors(Cell cell) {
        Cell[] neighbors = new Cell[8];
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
                    if (neighbor.getStatus() != 1) {
                        continue;
                    }
                    if (neighbor instanceof NaturalKiller) {
                        if (!isDirectlyNextTo(cell, neighbor)) {
                            continue;
                        }
                    }
                    neighbors[aliveNeighbors] = neighbor;
                    aliveNeighbors++; 
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        if(neighbors[0] == null) {
            return new Cell[] {};
        }
        Cell[] noNulls = new Cell[aliveNeighbors];
        System.arraycopy(neighbors, 0, noNulls, 0, aliveNeighbors);
        return noNulls;
    }
    
    public Cell[] directNeighbors(Cell cell) {
        int counter = 0;
        Cell[] totalNeighbors = this.aliveNeighbors(cell);
        Cell[] onlyFriends = new Cell[totalNeighbors.length];
        for(Cell c : totalNeighbors) {
            onlyFriends[counter] = c;
            counter++;
        }
        if(onlyFriends[0] == null) {
            return new Cell[] {};
        }
        Cell[] onlyNotNullFriends = new Cell[counter];
        System.arraycopy(onlyFriends, 0, onlyNotNullFriends, 0, counter);
        return onlyFriends;
    }

    private boolean isDirectlyNextTo(Cell c1, Cell c2) {
        int x1 = c1.getX();
        int y1 = c1.getY();
        int x2 = c2.getX();
        int y2 = c2.getY();

        if (x1 == x2 && (y1 == y2 + 1 || y1 == y2 - 1)
                || y1 == y2 && (x1 == x2 + 1 || x1 == x2 - 1)) {
            return true;
        }
        return false;
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

    public void setRevivalChance(double revivalChance) {
        this.revivalChance = revivalChance;
    }

    public Cell[][] nextBoardState() {
        Cell[][] nextBoard = new Cell[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                Cell cell = new Cell(board[i][y]);
                int aliveNeighbors = aliveNeighbors(cell).length;
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
                if (cell.getStatus() == -1) {
                    tryRevive(cell);
                }
                nextBoard[i][y] = cell;
            }
        }
        setBoard(nextBoard);
        return nextBoard;
    }

    public Cell get(int x, int y) {
        return this.board[x][y];
    }

    private void tryRevive(Cell cell) {
        Random random = new Random();
        if (random.nextDouble() <= revivalChance) {
            cell.setStatus(1);
        }
    }
    
    public void delete(int x, int y) {
        board[x][y].setStatus(-1);
    }
    
    public void put(Cell cell, int x, int y) {
        board[x][y] = cell;
    }
}
