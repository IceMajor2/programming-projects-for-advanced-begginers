package snakegame;

import java.util.LinkedList;

public class Snake {

    private LinkedList<int[]> body;
    private Directions direction;

    public Snake(Directions dir, int[]... pos) {
        this.initBody(pos);
        this.initDirection(dir);
    }

    private void initBody(int[]... pos) {
        this.body = new LinkedList<>();
        for (int[] cords : pos) {
            body.addLast(cords);
        }
    }

    private void initDirection(Directions direction) {
        this.direction = direction;
    }

    public void takeStep() {
        int[] head = this.head();
        int[] newHeadPos = {head[0] + direction.getCords()[0],
            head[1] - direction.getCords()[1]};
        body.addFirst(newHeadPos);
        body.removeLast();
    }
    
    public void eatApple() {
        int[] last = this.tail();
        int[] secLast = body.get(body.size() - 2);
        int[] newLast = new int[2];
        
        int xDir = last[0] - secLast[0];
        int yDir = last[1] - secLast[1];
        
        if(xDir != 0) {
            newLast[0] = last[0] + xDir;
            newLast[1] = last[1];
        }
        if(yDir != 0) {
            newLast[0] = last[0];
            newLast[1] = last[1] + yDir;
        }
        if(newLast[0] >= Main.WIDTH) {
            newLast[0] = Main.WIDTH;
            newLast[1]--;
            if(newLast[1] < 0) {
                newLast[1] += 2;
            }
        }
        if(newLast[1] >= Main.HEIGHT) {
            newLast[1] = Main.HEIGHT;
            newLast[0]--;
            if(newLast[0] < 0) {
                newLast[0] += 2;
            }
        }
        body.addLast(newLast);
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public Directions getDirection() {
        return direction;
    }

    public int[] head() {
        return body.getFirst();
    }
    
    public int[] tail() {
        return body.getLast();
    }

    public LinkedList<int[]> getBody() {
        return body;
    }
}

enum Directions {

    UP(0, 1), DOWN(0, -1), RIGHT(1, 0), LEFT(-1, 0);

    private int[] cords;

    Directions(int x, int y) {
        this.cords = new int[2];
        this.cords[0] = x;
        this.cords[1] = y;
    }

    public int[] getCords() {
        return cords;
    }
    
    public boolean isOpposite(Directions other) {
        if((this == DOWN && other == UP) || (this == UP && other == DOWN)) {
            return true;
        }
        if((this == LEFT && other == RIGHT) || (this == RIGHT && other == LEFT)) {
            return true;
        }
        return false;
    }
}
