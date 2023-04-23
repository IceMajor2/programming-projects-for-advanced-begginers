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
        String horBorder = this.getHorizontalBorder();
        System.out.println(horBorder);
        
        for (int i = 0; i < board.length; i++) {
            
            System.out.print("|");
            
            for (int y = 0; y < board[i].length; y++) {

                int[] currCords = new int[] {y, i};
                if(isSnakesBody(currCords)) {
                    if(isSnakesHead(currCords)) {
                        System.out.print('X');
                        continue;
                    }
                    System.out.print('O');
                    continue;
                }
                
                System.out.print(" ");
            }
            
            System.out.println("|");
        }

        System.out.println(horBorder);
    }
    
    private boolean isSnakesHead(int[] cords) {
        int[] head = snake.head();
        return head[0] == cords[0] && head[1] == cords[1];
    }

    private boolean isSnakesBody(int[] cords) {
        var body = snake.getBody();
        for(int[] bodyPart : body) {
            if(bodyPart[0] == cords[0] && bodyPart[1] == cords[1]) {
                return true;
            }
        }
        return false;
    }

    private String getHorizontalBorder() {
        StringBuilder horizontalBorder = new StringBuilder("");
        horizontalBorder.append('|');
        for (int i = 0; i < board[0].length; i++) {
            horizontalBorder.append('=');
        }
        horizontalBorder.append('|');
        return horizontalBorder.toString();
    }
}
