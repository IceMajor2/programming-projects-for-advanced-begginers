package snakegame;

import java.util.LinkedList;

public class Snake {

    private LinkedList<int[]> body;
    private Directions direction;

    public Snake(Directions dir, int[]... pos) {
        this.initBody(pos);
        this.initDirection(dir);
    }

    public void initBody(int[]... pos) {
        this.body = new LinkedList<>();
        for (int[] cords : pos) {
            body.addLast(cords);
        }
    }

    public void initDirection(Directions direction) {
        this.direction = direction;
    }

    public void takeStep() {
        int[] head = this.head();
        int[] newHeadPos = {head[0] + direction.getCords()[0],
            head[1] - direction.getCords()[1]};
        body.addFirst(newHeadPos);
        body.removeLast();
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public int[] head() {
        return body.getFirst();
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
}
