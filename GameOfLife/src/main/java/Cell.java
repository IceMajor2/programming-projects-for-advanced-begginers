
public class Cell {
    
    protected int status;
    protected int x;
    protected int y;
    
    public Cell(int x, int y, int status) {
        this.status = status;
        this.x = x;
        this.y = y;
    }
    
    public Cell(Cell cell) {
        this.x = cell.getX();
        this.y = cell.getY();
        this.status = cell.getStatus();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public String toString() {
        if(this.status == 1) {
            return "#";
        }
        return " ";
    }
}
