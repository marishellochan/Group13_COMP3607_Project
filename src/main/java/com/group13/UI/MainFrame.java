package com.group13.UI;

import javax.swing.*;
import java.awt.*;

// Main application frame
public class MainFrame extends JFrame {
    private JPanel contentPane;

    public MainFrame() {
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        showScreen(new LoadDataScreen(this));

        setVisible(true);
    }

    // Switch to Screen
    public void showScreen(Screen screen) {
        contentPane.removeAll();
        contentPane.add(screen.getPanel(), BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }

}

