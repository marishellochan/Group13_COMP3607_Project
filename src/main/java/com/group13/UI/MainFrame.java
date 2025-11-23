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

        // Use BorderLayout for flexibility
        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Start with LoadDataScreen
        showLoadDataScreen();

        setVisible(true);
    }

    // Switch to LoadDataScreen
    public void showLoadDataScreen() {
        contentPane.removeAll();
        contentPane.add(new LoadDataScreen(this), BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Switch to ChoosePlayersScreen
    public void showChoosePlayersScreen() {
        contentPane.removeAll();
        contentPane.add(new ChoosePlayersScreen(this), BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}

