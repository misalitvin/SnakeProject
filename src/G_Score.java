import javax.swing.*;
import java.awt.*;

class G_Score extends JTable implements ScoreListener {
    int x;
    int y;
    int score;

    public G_Score(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("My Score " + score, x, y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 20);
    }

    @Override
    public void repaintme(int score) {
        this.score = score;
        repaint();
    }
}
