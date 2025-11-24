package com.group13.UI;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.ArrayList;
import com.group13.Players.Player;
import com.group13.Singelton.Game;
import com.group13.Singelton.PlayerTurnManager;
import com.group13.State.AnswerState;
import com.group13.State.WaitingState;

public class ChoosePlayersScreen extends JPanel {
    private JPanel playerFieldsPanel; // panel to hold player textfields

    public ChoosePlayersScreen(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(Color.RED);

        // this part shows the title and spinner to choose number of players
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(new EmptyBorder(15, 10, 10, 10));

        JLabel lblNewLabel = new JLabel("CHOOSE PLAYER COUNT");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel.setAlignmentX(CENTER_ALIGNMENT);

        SpinnerNumberModel model = new SpinnerNumberModel(2, 2, 4, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setFont(new Font("Tahoma", Font.BOLD, 16));
        spinner.setMaximumSize(new Dimension(80, 40));
        spinner.setAlignmentX(CENTER_ALIGNMENT);

        topPanel.add(lblNewLabel);
        topPanel.add(Box.createVerticalStrut(15));
        topPanel.add(spinner);

        add(topPanel, BorderLayout.NORTH);

        // this is where the text fields will go 
        playerFieldsPanel = new JPanel();
        playerFieldsPanel.setLayout(new BoxLayout(playerFieldsPanel, BoxLayout.Y_AXIS));
        playerFieldsPanel.setOpaque(false);
        playerFieldsPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        add(playerFieldsPanel, BorderLayout.CENTER);

        // make sure we have a minimum of 2 player fields at start
        updatePlayerFields(2);

        // Spinner listener which update and give the text fields for the player names 
        spinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updatePlayerFields((int) spinner.getValue());
            }
        });

       // this is the bottom section panel that holds the start game button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(10, 10, 20, 10));

        JButton btnStart = new JButton("Start Game!");
        btnStart.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnStart.setPreferredSize(new Dimension(180, 50));
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleStartButtonClick(frame);
            }
        });

        bottomPanel.add(btnStart);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // this method updates the text fields for player names based on the selected count
    private void updatePlayerFields(int count) {
        playerFieldsPanel.removeAll();

        for (int i = 1; i <= count; i++) {
            JTextField tf = new JTextField();
            tf.setFont(new Font("Tahoma", Font.PLAIN, 14));
            tf.setMaximumSize(new Dimension(250, 40));

            tf.setBorder(BorderFactory.createTitledBorder("Player " + i + " Name"));

            playerFieldsPanel.add(tf);
            playerFieldsPanel.add(Box.createVerticalStrut(15)); // spacing
        }

        playerFieldsPanel.revalidate();
        playerFieldsPanel.repaint();
    }

    private void handleStartButtonClick(MainFrame frame) {
        // Logic to start the game with entered player names
        int playerCount = playerFieldsPanel.getComponentCount() / 2; // each field + spacer
        ArrayList<Player> players = new ArrayList<>();
        Game game = Game.getInstance();
        PlayerTurnManager ptm = PlayerTurnManager.getInstance();

        if(fieldsAreEmpty(playerCount)) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Please enter names for all players.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        for (int i = 0; i < playerCount; i++) {
            JTextField tf = (JTextField) playerFieldsPanel.getComponent(i * 2);
            Player player = new Player(tf.getText().trim());
            players.add(player);
            if(i ==0){
                ptm.setCurrentPlayer(players.get(0));
                player.setState(new AnswerState());
            }
            else{
                player.setState(new WaitingState());
            }
        }
        ptm.set_Players(players);
        game.start();

        ptm.getPlayers().forEach(p -> System.out.println("Player added: " + p.getPlayerName()));
        frame.showStartGameScreen();

        game.startGameSession("Game" + System.currentTimeMillis()); //unique id- start session while tracking
        
    }

    private boolean fieldsAreEmpty(int playerCount) {
        for (int i = 0; i < playerCount; i++) {
            JTextField tf = (JTextField) playerFieldsPanel.getComponent(i * 2);
            if (tf.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}

