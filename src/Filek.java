import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

class Filek extends JTable implements GameOverListener {
    ArrayList<Person> people = new ArrayList<>();

    public Filek() throws IOException {
        File f = new File("bin.bin");
        if (!f.exists()) {
            f.createNewFile();
        }
    }

    public void readPeople() {
        try (FileInputStream fileInputStream = new FileInputStream("bin.bin")) {
            for (int i = 0; i < 30; i++) {
                int length = fileInputStream.read();
                if (length == -1) {
                    break;
                }

                byte[] bytes = new byte[length];
                fileInputStream.read(bytes);
                String Name = new String(bytes);

                int score = (fileInputStream.read() << 24) | (fileInputStream.read() << 16) |
                        (fileInputStream.read() << 8) |
                        fileInputStream.read();

                people.add(new Person(Name, score));
            }

            people.sort((person1, person2) -> Integer.compare(person2.score, person1.score));
        } catch (IOException ignored) {
        }
    }

    public void writePeople(Person myperson) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("bin.bin", true)) {
            byte[] bytes = myperson.name.getBytes();
            int length = bytes.length;

            fileOutputStream.write((byte) length);

            fileOutputStream.write(bytes);

            fileOutputStream.write((myperson.score >> 24) & 0xFF);
            fileOutputStream.write((myperson.score >> 16) & 0xFF);
            fileOutputStream.write((myperson.score >> 8) & 0xFF);
            fileOutputStream.write(myperson.score & 0xFF);
        } catch (IOException ignored) {
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("RESULTS", 1300, 50);
        for (int i = 0; i < people.size(); i++) {
            if (i < 10)
                g.drawString(people.get(i).name + " score-  " + people.get(i).score, 1300, 100 + 50 * i);
        }
    }

    @Override
    public void printRes(Person person) {
        writePeople(person);
        readPeople();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 800); // Adjust the size as needed
    }
}
