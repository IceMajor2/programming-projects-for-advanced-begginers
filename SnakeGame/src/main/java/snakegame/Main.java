package snakegame;

public class Main {

    public static int WIDTH;
    public static int HEIGHT;
    
    public static void main(String[] args) {
        WIDTH = 20;
        HEIGHT = 10;
        Game game = new Game(HEIGHT, WIDTH);
        game.start();
    }
}
