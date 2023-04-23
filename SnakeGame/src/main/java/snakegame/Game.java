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

    private void initBoard(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new Object[height][width];
    }

    private void initSnake() {
        Random rnd = new Random();
        int xHead = rnd.nextInt(width - 6) + 3;
        int yHead = rnd.nextInt(height - 6) + 3;
        int[] head = new int[]{xHead, yHead};

        int[][] body = new int[4][2];
        body[0] = head;

        int[] prevNode = head;
        Directions prevDir = null;
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
    }

    private void initApple() {

    }

    public void render() {
        String horBorder = this.getHorizontalBorder();
        System.out.println(horBorder);

        for (int i = 0; i < board.length; i++) {

            System.out.print("|");

            for (int y = 0; y < board[i].length; y++) {

                int[] currCords = new int[]{y, i};
                if (isSnakesBody(currCords)) {
                    if (isSnakesHead(currCords)) {
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

    public void makeMove() {
        String usrInput = scanner.nextLine();
        if (usrInput.isEmpty()) {
            snake.takeStep();
            return;
        }
        snake.setDirection(keyMap.get(Character.valueOf(usrInput.charAt(0))));
        snake.takeStep();
    }

    private boolean isSnakesHead(int[] cords) {
        int[] head = snake.head();
        return head[0] == cords[0] && head[1] == cords[1];
    }

    private boolean isSnakesBody(int[] cords) {
        var body = snake.getBody();
        for (int[] bodyPart : body) {
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
