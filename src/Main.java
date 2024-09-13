import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1500,800));
        Board board = new Board();
        G_Board gameBoard = new G_Board(board);
        gameBoard.setFocusable(true); // Set the component focusable

        gameBoard.addKeyListener(board);
        board.addListener(gameBoard);
        board.addSListener(gameBoard.g_score);
        board.addGOListener(gameBoard.file);

        frame.add(gameBoard);

        frame.pack(); // Adjusts the frame size to fit the components
        frame.setLocationRelativeTo(null); // Centers the frame on the screen
        frame.setVisible(true); // Makes the frame visible
    }
}

