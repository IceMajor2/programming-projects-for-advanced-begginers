package snakegame;

public class Apple {

    private int[] position;

    public Apple(int x, int y) {
        position = new int[]{x, y};
    }

    public int[] getPosition() {
        return position;
    }
}
