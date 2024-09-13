import javax.swing.*;
import java.awt.*;
import java.io.IOException;

class G_Board extends JTable implements TickListener {
    private final int row = 25;
    private final int column = 16;
    private final int squareSize = 20;

    private Board board;
    G_Score g_score = new G_Score(750, 100);
    Filek file = new Filek();

    public G_Board(Board board) throws IOException {
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int x = (width - column * squareSize) / 2;
        int y = (height - row * squareSize) / 2;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                int squareX = x + j * squareSize;
                int squareY = y + i * squareSize;

                switch (board.Board[i][j]) {
                    case 2 -> {
                        g.setColor(Color.MAGENTA);
                        g.fillRect(squareX, squareY, squareSize, squareSize);
                    }
                    case 1 -> {
                        g.setColor(Color.GREEN);
                        g.fillRect(squareX, squareY, squareSize, squareSize);
                    }
                    case 3 -> {
                        g.setColor(Color.RED);
                        g.fillOval(squareX, squareY, squareSize, squareSize);
                    }
                    default -> {
                        g.setColor(Color.BLACK);
                        g.drawRect(squareX, squareY, squareSize, squareSize);
                    }
                }
            }
        }
        g_score.paintComponent(g);
        file.paintComponent(g);
    }

    @Override
    public void repaintme(Board board) {
        this.board = board;
        repaint();
    }

}
