package com.group13.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group13.Singelton.GameController;

public class GameOverScreen extends JPanel implements Screen {
    private MainFrame mainFrame;
    private GameController controller;

    public GameOverScreen(MainFrame frame) {
        this.mainFrame = frame;
        this.controller = GameController.getInstance();
        
        setSize(500, 450);
        setBackground(new Color(25, 25, 112)); // Midnight blue
        setLayout(null);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addComponents();
    }

    private void addComponents() {
        // Game Over title
        JLabel titleLabel = new JLabel("GAME OVER!");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(50, 100, 400, 60);
        add(titleLabel);
        
        // Generate Report Button
        JButton reportBtn = new JButton("Generate Report");
        reportBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
        reportBtn.setBounds(150, 250, 200, 50);
        reportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showScreen(new GenerateReportScreen(mainFrame));
            }
        });
        add(reportBtn);
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}