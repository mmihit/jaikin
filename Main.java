import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import Jaikin.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chaikin's Algorithm");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 700);

            DrawPanel drawPanel = new DrawPanel();
            frame.add(drawPanel);

            frame.setFocusable(true);
            frame.setVisible(true);
        });
    }
}

