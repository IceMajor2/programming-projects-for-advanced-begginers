package snakegame;

public class Game {
    
    private Object[][] board;
    private Snake snake;
    private int height;
    private int width;
    
    public Game(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new Object[height][width];
        int[][] cords = {{0, 0}, {1, 0}, {2, 0}, {3, 0}};
        this.snake = new Snake(Directions.UP, cords);
    }
    
    public void render() {
        StringBuilder horizontalBorder = new StringBuilder("");
        horizontalBorder.append('|');
        for(int i = 0; i < board[0].length; i++) {
            horizontalBorder.append('=');
        }
        horizontalBorder.append('|');
        System.out.println(horizontalBorder);
        for(Object[] row: board) {
            System.out.print("|");
            for(Object place : row) {
                if(place == null) {
                    System.out.print(" ");
                }
            }
            System.out.println("|");
        }
        System.out.println(horizontalBorder);
    }
    
}
