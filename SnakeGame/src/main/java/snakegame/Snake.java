package snakegame;

import java.util.LinkedList;

public class Snake {

    private LinkedList<int[]> body;
    private Directions direction;

    public Snake(int x, int y, Directions dir) {
        this.initBody(x, y);
        this.initDirection(dir);
    }

    public void initBody(int x, int y) {
        this.body = new LinkedList<>();
        body.addFirst(new int[]{x, y});
    }
    
    public void initDirection(Directions direction) {
        this.direction = direction;
    }
    
    public void takeStep(int newX, int newY) {
        body.addFirst(new int[]{newX, newY});
        body.removeLast();
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }
    
    public int[] head() {
        return body.getFirst();
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
}
