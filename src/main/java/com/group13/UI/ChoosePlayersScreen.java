package com.group13.UI;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.group13.Logging.LogEntry;
import com.group13.Players.Player;
import com.group13.Singelton.Game;
import com.group13.Singelton.GameController;
import com.group13.Singelton.PlayerTurnManager;

public class ChoosePlayersScreen extends JPanel implements Screen {
    private JPanel playerFieldsPanel;
    private MainFrame mainFrame;
    private GameController controller = GameController.getInstance();

    public ChoosePlayersScreen(MainFrame frame) {
        this.mainFrame = frame;
        this.controller = GameController.getInstance();
        setLayout(new BorderLayout());
        setBackground(Color.RED);

        add(createTopSection(), BorderLayout.NORTH);
        add(createCenterSection(), BorderLayout.CENTER);
        add(createBottomSection(), BorderLayout.SOUTH);
    }

    private JPanel createTopSection() {
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);

        // Quit button
        JPanel quitPanel = QuitButtonFactory.createQuitButtonPanel(Color.WHITE);
        quitPanel.setOpaque(false);
        topSection.add(quitPanel, BorderLayout.NORTH);

        // Title and spinner
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(new EmptyBorder(15, 10, 10, 10));

        JLabel label = new JLabel("CHOOSE PLAYER COUNT");
        label.setFont(new Font("Tahoma", Font.BOLD, 18));
        label.setAlignmentX(CENTER_ALIGNMENT);

        SpinnerNumberModel model = new SpinnerNumberModel(2, 2, 4, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setFont(new Font("Tahoma", Font.BOLD, 16));
        spinner.setMaximumSize(new Dimension(80, 40));
        spinner.setAlignmentX(CENTER_ALIGNMENT);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updatePlayerFields((int) spinner.getValue());
            }
        });

        titlePanel.add(label);
        titlePanel.add(Box.createVerticalStrut(15));
        titlePanel.add(spinner);
        topSection.add(titlePanel, BorderLayout.CENTER);

        return topSection;
    }

    private JPanel createCenterSection() {
        playerFieldsPanel = new JPanel();
        playerFieldsPanel.setLayout(new BoxLayout(playerFieldsPanel, BoxLayout.Y_AXIS));
        playerFieldsPanel.setOpaque(false);
        playerFieldsPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        updatePlayerFields(2);
        return playerFieldsPanel;
    }

    private JPanel createBottomSection() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(10, 10, 20, 10));

        JButton btnStart = new JButton("Start Game!");
        btnStart.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnStart.setPreferredSize(new Dimension(180, 50));
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStartGame();
            }
        });
        bottomPanel.add(btnStart);

        return bottomPanel;
    }

    private void updatePlayerFields(int count) {
        playerFieldsPanel.removeAll();

        for (int i = 1; i <= count; i++) {
            JTextField tf = new JTextField();
            tf.setFont(new Font("Tahoma", Font.PLAIN, 14));
            tf.setMaximumSize(new Dimension(250, 40));
            tf.setBorder(BorderFactory.createTitledBorder("Player " + i + " Name"));
            
            playerFieldsPanel.add(tf);
            playerFieldsPanel.add(Box.createVerticalStrut(15));
        }

        playerFieldsPanel.revalidate();
        playerFieldsPanel.repaint();
    }

    private void handleStartGame() {
        int playerCount = playerFieldsPanel.getComponentCount() / 2;
        
        if (hasEmptyFields(playerCount)) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter names for all players.",
                "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<String> playerNames = getPlayerNames(playerCount);
        controller.initializePlayers(playerNames);
        mainFrame.showScreen(new StartGameScreen(mainFrame));
    }

    private ArrayList<String> getPlayerNames(int count) {
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            JTextField tf = (JTextField) playerFieldsPanel.getComponent(i * 2);
            names.add(tf.getText().trim());
        }
        return names;
    }

    private boolean hasEmptyFields(int playerCount) {
        for (int i = 0; i < playerCount; i++) {
            JTextField tf = (JTextField) playerFieldsPanel.getComponent(i * 2);
            if (tf.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public JPanel getPanel() {
        return this;
    }
}