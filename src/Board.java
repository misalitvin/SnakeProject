import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

class Board extends Thread implements KeyListener {
    public int[][] Board = new int[25][16];
    //1 = snake piece 2 = snake head 3 = apple
    Apple apple;
    Snake snake;
    int score;
    String direction;
    boolean isFinished;
    Person person;

    public Board() {
        Random random = new Random();
        apple = new Apple(random.nextInt(15), random.nextInt(24));
        Board[apple.y][apple.x] = 3;
        snake = new Snake();
        direction = "RIGHT";
        score = 0;
        isFinished = false;
        start();
    }

    private final java.util.List<TickListener> listeners = new ArrayList<>();
    private final java.util.List<ScoreListener> slisteners = new ArrayList<>();
    private final List<GameOverListener> golisteners = new ArrayList<>();

    public void addListener(TickListener listener) {
        listeners.add(listener);
    }

    public void addSListener(ScoreListener slistener) {
        slisteners.add(slistener);
    }

    public void addGOListener(GameOverListener golistener) {
        golisteners.add(golistener);
    }

    private void triggerEvent() {
        for (TickListener listener : listeners) {
            listener.repaintme(this);
        }
    }

    private void triggersEvent() {
        for (ScoreListener slistener : slisteners) {
            slistener.repaintme(score);
        }
    }

    private void triggerGOEvent() {
        for (GameOverListener golistener : golisteners) {
            golistener.printRes(person);
        }
    }

    @Override
    public void run() {
        Random random = new Random();
        while (!isFinished) {
            Board[snake.snake.get(snake.snake.size() - 1).y][snake.snake.get(snake.snake.size() - 1).x] = 0;
            snake.move(direction);
            if (snake.snake.get(0).x == apple.x && snake.snake.get(0).y == apple.y) {
                apple.x = random.nextInt(15);
                apple.y = random.nextInt(24);
                Board[apple.y][apple.x] = 3;
                snake.addPiece(direction);
                score++;
                triggersEvent();
            }

            if (snake.snake.get(0).x < 0 || snake.snake.get(0).x >= 16 || snake.snake.get(0).y < 0 || snake.snake.get(0).y >= 25) {
                isFinished = true;
                String name = JOptionPane.showInputDialog(null, "Enter a name:");
                person = new Person(name, score);
                triggerGOEvent();
                triggerEvent();
                break;
            }
            for (int i = 3; i < snake.snake.size(); i++) {
                if (snake.snake.get(0).x == snake.snake.get(i).x && snake.snake.get(0).y == snake.snake.get(i).y) {
                    isFinished = true;
                    String name = JOptionPane.showInputDialog(null, "Enter a name:");
                    person = new Person(name, score);
                    triggerGOEvent();
                    triggerEvent();
                    break;
                }
            }
            if (isFinished) break;


            for (SnakePiece c : snake.snake) {
                Board[c.y][c.x] = c.type;
            }

            triggerEvent();
            try {
                sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP -> {
                if (!Objects.equals(direction, "DOWN"))
                    direction = "UP";
            }
            case KeyEvent.VK_DOWN -> {
                if (!Objects.equals(direction, "UP"))
                    direction = "DOWN";
            }
            case KeyEvent.VK_LEFT -> {
                if (!Objects.equals(direction, "RIGHT"))
                    direction = "LEFT";
            }
            case KeyEvent.VK_RIGHT -> {
                if (!Objects.equals(direction, "LEFT"))
                    direction = "RIGHT";
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
