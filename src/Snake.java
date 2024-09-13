import java.util.ArrayList;

class Snake {
    ArrayList<SnakePiece> snake;

    public Snake() {
        this.snake = new ArrayList<>();
        snake.add(new SnakePiece(2, 8, 13));
    }

    void addPiece(String direction) {
        int myX = snake.get(snake.size() - 1).x;
        int myY = snake.get(snake.size() - 1).y;
        snake.add(new SnakePiece(1, myX, myY));
    }

    void move(String direction) {
        int prevX = snake.get(0).x;
        int prevY = snake.get(0).y;

        switch (direction) {
            case "UP":
                snake.get(0).y--;
                break;
            case "DOWN":
                snake.get(0).y++;
                break;
            case "RIGHT":
                snake.get(0).x++;
                break;
            case "LEFT":
                snake.get(0).x--;
                break;
        }

        for (int i = 1; i < snake.size(); i++) {
            int tempX = snake.get(i).x;
            int tempY = snake.get(i).y;
            snake.get(i).x = prevX;
            snake.get(i).y = prevY;
            prevX = tempX;
            prevY = tempY;
        }
    }

}
