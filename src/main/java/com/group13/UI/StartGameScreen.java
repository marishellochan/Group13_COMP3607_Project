package com.group13.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.group13.Players.Player;
import com.group13.Singelton.PlayerTurnManager;

public class StartGameScreen extends JPanel implements Screen {
    private static final int MAX_PLAYERS = 4;
    
    private JButton[] playerButtons = new JButton[MAX_PLAYERS];
    private MainFrame mainFrame;
    private PlayerTurnManager playerTurnManager;

    public StartGameScreen(MainFrame frame) {
        this.mainFrame = frame;
        this.playerTurnManager = PlayerTurnManager.getInstance();
        
        setLayout(new BorderLayout());
        setBackground(Color.RED);

        add(QuitButtonFactory.createQuitButtonPanel(Color.RED), BorderLayout.NORTH);
        add(createPlayerPanel(), BorderLayout.CENTER);
        
        updatePlayerButtons();
    }

    private JPanel createPlayerPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBackground(Color.RED);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Player> players = playerTurnManager.getPlayers();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            JButton btn = new JButton(player.getPlayerName());
            btn.setFont(new Font("Tahoma", Font.BOLD, 20));
            btn.setEnabled(false);
            btn.addActionListener(e -> handlePlayerClick(player));
            
            playerButtons[i] = btn;
            panel.add(btn);
        }
        
        return panel;
    }

    private void handlePlayerClick(Player player) {
        if (player != playerTurnManager.getCurrentPlayer()) {
            JOptionPane.showMessageDialog(mainFrame,
                "It is not your turn yet!",
                "Hold on!",
                JOptionPane.WARNING_MESSAGE);
        } else {
            mainFrame.showScreen(new CategoryScreen(mainFrame));
        }
    }

    private void updatePlayerButtons() {
        List<Player> players = playerTurnManager.getPlayers();
        int currentTurn = playerTurnManager.getCurrentTurn();
        
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (i < players.size()) {
                playerButtons[i].setVisible(true);
                playerButtons[i].setText(players.get(i).getPlayerName());
                playerButtons[i].setEnabled((i + 1) == currentTurn);
            } else {
                if (playerButtons[i] != null) {
                    playerButtons[i].setVisible(false);
                }
            }
        }
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}