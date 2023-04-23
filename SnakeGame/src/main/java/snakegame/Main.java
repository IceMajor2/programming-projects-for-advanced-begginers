package snakegame;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(10, 20);
        while (true) {
            game.render();
            game.makeMove();
        }
    }
}
