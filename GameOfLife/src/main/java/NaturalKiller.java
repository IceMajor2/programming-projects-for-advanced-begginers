
import java.util.Random;

public class NaturalKiller extends Cell {

    private LifeBoard board;
    private Cell[] neighbors;
    private double killingPower;

    public NaturalKiller(int x, int y, int status) {
        super(x, y, status);
        this.killingPower = 0.03;
        this.board = GameOfLife.board;
        this.neighbors = board.directNeighbors(this);
    }

    public NaturalKiller(Cell cell) {
        super(cell);
    }

    public void act() {
        if (isFriendlyCellNeighbor()) {
            attack();
            return;
        }
        move();
    }

    public void attack() {
        Random random = new Random();
        int cellIndex = random.nextInt(neighbors.length);
        Cell attacked = neighbors[cellIndex];
        if (random.nextDouble() <= 0.03) {
            attacked.setStatus(-1);
        }
    }

    public void move() {
        Cell[] slots = this.freeCells();
        Random random = new Random();
        Cell moveTo = slots[random.nextInt(slots.length)];
        int x = moveTo.x;
        int y = moveTo.y;
        board.put(this, x, y);
    }

    private Cell[] freeCells() {
        int counter = 0;
        Cell[] freeCells = new Cell[4];
        try {
            if(board.get(x + 1, y).getStatus() == -1) {
                freeCells[counter] = board.get(x + 1, y);
                counter++;
            }
        } catch(ArrayIndexOutOfBoundsException e) {}
        try {
            if(board.get(x - 1, y).getStatus() == -1) {
                freeCells[counter] = board.get(x - 1, y);
                counter++;
            }
        } catch(ArrayIndexOutOfBoundsException e) {}
        try {
            if(board.get(x, y + 1).getStatus() == -1) {
                freeCells[counter] = board.get(x, y + 1);
                counter++;
            }
        } catch(ArrayIndexOutOfBoundsException e) {}
        try {
            if(board.get(x, y - 1).getStatus() == -1) {
                freeCells[counter] = board.get(x, y - 1);
                counter++;
            }
        } catch(ArrayIndexOutOfBoundsException e) {}
        Cell[] newCells = new Cell[freeCells.length];
        System.arraycopy(freeCells, 0, newCells, 0, counter);
        return newCells;
    }

    private boolean isFriendlyCellNeighbor() {
        for (Cell neighbor : neighbors) {
            if (neighbor instanceof Cell && !(neighbor instanceof NaturalKiller)) {
                return true;
            }
        }
        return false;
    }
}
