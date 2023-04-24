package snakegame;

import java.util.Map;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Random;

public class Game {

    private Map<Character, Directions> keyMap = Map.of('W', Directions.UP,
            'A', Directions.LEFT, 'S', Directions.DOWN, 'D', Directions.RIGHT);

    private Object[][] board;
    private Snake snake;
    private Apple apple;
    private int height;
    private int width;
    private Scanner scanner;

    public Game(int height, int width) {
        if (height < 8 || width < 8) {
            System.out.println("Board must be at least 8 x 8");
            throw new IllegalArgumentException();
        }
        this.initBoard(height, width);
        this.initSnake();
        this.initApple();

        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (!this.collided()) {
            this.render();
            Directions newDir = this.makeMove();
            this.updateBoard(newDir);
            if(snakeOnApple()) {
                snake.eatApple();
                this.initApple();
            }
        }
    }

    private void initBoard(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new Object[height][width];
    }

    private void updateBoard(Directions snakeMove) {
        var currHead = snake.head();
        var currTail = snake.tail();
        snake.setDirection(snakeMove);
        snake.takeStep();
        var nextHead = snake.head();

        board[currTail[1]][currTail[0]] = null;
        board[currHead[1]][currHead[0]] = 'O';
        board[nextHead[1]][nextHead[0]] = 'X';
    }

    private void initSnake() {
        Random rnd = new Random();
        int xHead = rnd.nextInt(width - 6) + 3;
        int yHead = rnd.nextInt(height - 6) + 3;
        int[] head = new int[]{xHead, yHead};

        int[][] body = new int[4][2];
        body[0] = head;
        // head generated

        int[] prevNode = head;
        Directions prevDir = null;
        // creating 3 more nodes - the tail
        for (int i = 1; i <= 3; i++) {
            Directions rndDir = Directions.values()[rnd.nextInt(4)];
            while (rndDir.isOpposite(prevDir)) {
                rndDir = Directions.values()[rnd.nextInt(4)];
            }
            int[] node = new int[]{prevNode[0] + rndDir.getCords()[0],
                prevNode[1] - rndDir.getCords()[1]};
            body[i] = node;

            prevNode = node;
            prevDir = rndDir;
        }
        // whole body is set
        // giving snake default direction
        Directions dir = Directions.values()[rnd.nextInt(4)];
        int[] hypoMove = new int[]{head[0] + dir.getCords()[0],
            head[1] - dir.getCords()[1]};
        this.snake = new Snake(dir, body);
        while (isSnakesBody(hypoMove)) {
            dir = Directions.values()[rnd.nextInt(4)];
            hypoMove = new int[]{head[0] + dir.getCords()[0],
                head[1] - dir.getCords()[1]};
            snake.setDirection(dir);
        }
        // snake all ready
        // now setting the board object
        board[head[1]][head[0]] = 'X';
        for (int i = 1; i < body.length; i++) {
            board[body[i][1]][body[i][0]] = 'O';
        }
    }

    private void initApple() {
        Random rnd = new Random();
        int appleX = rnd.nextInt(width);
        int appleY = rnd.nextInt(height);
        int[] apple = new int[]{appleX, appleY};
        while (isSnakesHead(apple) || isSnakesBody(apple)) {
            appleX = rnd.nextInt(width);
            appleY = rnd.nextInt(height);
            apple = new int[]{appleX, appleY};
        }
        this.apple = new Apple(appleX, appleY);
        board[appleY][appleX] = '*';
    }

    private void render() {
        String horBorder = this.getHorizontalBorder();
        System.out.println(horBorder);

        for (int i = 0; i < board.length; i++) {

            System.out.print("|");

            for (int y = 0; y < board[i].length; y++) {

                if (board[i][y] == null) {
                    System.out.print(" ");
                    continue;
                }
                System.out.print(board[i][y]);
            }

            System.out.println("|");
        }

        System.out.println(horBorder);
    }

    private Directions makeMove() {
        String usrInput = scanner.nextLine();
        if (usrInput.isEmpty()) {
            return snake.getDirection();
        }
        Directions prevDir = snake.getDirection();
        Directions newDir = keyMap.get(usrInput.charAt(0));
        return newDir.isOpposite(prevDir) ? prevDir : newDir;
    }

    private boolean collided() {
        int[] headPos = snake.head();
        if (isSnakesBody(headPos)) {
            return true;
        }
        return false;
    }
    
    private boolean snakeOnApple() {
        var snakeHead = snake.head();
        var applePos = apple.getPosition();
        return snakeHead[0] == applePos[0] && snakeHead[1] == applePos[1];
    }

    private boolean isSnakesHead(int[] cords) {
        int[] head = snake.head();
        return head[0] == cords[0] && head[1] == cords[1];
    }

    private boolean isSnakesBody(int[] cords) {
        var body = snake.getBody();
        for (int[] bodyPart : body) {
            if (bodyPart[0] == snake.head()[0] && bodyPart[1] == snake.head()[1]) {
                continue;
            }
            if (bodyPart[0] == cords[0] && bodyPart[1] == cords[1]) {
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
