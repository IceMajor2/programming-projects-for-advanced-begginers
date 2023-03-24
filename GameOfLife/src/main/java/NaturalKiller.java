
import java.util.Random;

public class NaturalKiller extends Cell {
    
    private LifeBoard board;
    
    public NaturalKiller(int x, int y, int status) {
        super(x, y, status);
        this.board = GameOfLife.board;
    }
    
    public NaturalKiller(Cell cell) {
        super(cell);
    }
    
    public void act() {
        
    }
}
