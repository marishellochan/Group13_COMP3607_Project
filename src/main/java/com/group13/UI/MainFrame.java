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

    // Switch to StartGameScreen
    public void showStartGameScreen() {
        contentPane.removeAll();
        contentPane.add(new StartGameScreen(this), BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }

    public void showCategoryScreen() {
        contentPane.removeAll();
        contentPane.add(new CategoryScreen(this), BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }

    public void showQuestionValueScreen(String category) {
        contentPane.removeAll();
        contentPane.add(new QuestionValueScreen(this, category), BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}

