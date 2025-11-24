package com.group13.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.group13.Players.Player;
import com.group13.Singelton.PlayerTurnManager;

public class StartGameScreen extends JPanel {

    private JButton[] playerButtons = new JButton[4];
    private int playerButtonCount = 0;

    public StartGameScreen(MainFrame frame) {

        setLayout(new GridLayout(2, 2, 10, 10));
        setBackground(Color.RED);

        PlayerTurnManager ptm = PlayerTurnManager.getInstance();

        // Create the buttons
        for (Player player : ptm.getPlayers()) {

            JButton btn = new JButton(player.getPlayerName());
            btn.setFont(new Font("Tahoma", Font.BOLD, 20));
            btn.setEnabled(false);

            int index = playerButtonCount;   // store current index

            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (player != ptm.getCurrentPlayer()) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "It is not your turn yet!",
                                "Hold on!",
                            JOptionPane.WARNING_MESSAGE
                    );
                } else {
                    frame.showCategoryScreen();
                }
             }
            });

            playerButtons[playerButtonCount] = btn;
            playerButtonCount++;

            add(btn);
        }

        updateVisiblePlayers(ptm.getPlayers().size(), ptm.getPlayers());
        updateTurnUI(ptm.getPlayers().size());
    }

    private void updateVisiblePlayers(int playerCount, List<Player> players) {
        for (int i = 0; i < 4; i++) {
            if (i < playerCount) {
                playerButtons[i].setVisible(true);
                playerButtons[i].setText(players.get(i).getPlayerName());
            } else {
                if (playerButtons[i] != null)
                    playerButtons[i].setVisible(false);
            }
        }
    }

    private void updateTurnUI(int playerCount) {
        PlayerTurnManager ptm = PlayerTurnManager.getInstance();
        for (int i = 0; i < playerCount; i++) {
            playerButtons[i].setEnabled(i + 1 == ptm.getCurrentTurn());
        }
    }
}
