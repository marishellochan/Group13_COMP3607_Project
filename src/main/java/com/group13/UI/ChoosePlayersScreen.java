package com.group13.UI;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ChoosePlayersScreen extends JFrame {

    private JPanel contentPane;
    private JPanel playerFieldsPanel; // panel to hold player textfields

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ChoosePlayersScreen frame = new ChoosePlayersScreen();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ChoosePlayersScreen() {

        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // MAIN CONTAINER — uses BorderLayout to avoid layout issues
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.RED);
        setContentPane(contentPane);

        // ================= TOP PANEL =================
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

        contentPane.add(topPanel, BorderLayout.NORTH);

        // ================= CENTER PANEL (SCROLLABLE) =================
        playerFieldsPanel = new JPanel();
        playerFieldsPanel.setLayout(new BoxLayout(playerFieldsPanel, BoxLayout.Y_AXIS));
        playerFieldsPanel.setOpaque(false);
        playerFieldsPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // JScrollPane scrollPane = new JScrollPane(playerFieldsPanel);
        // scrollPane.setBorder(null);
        // scrollPane.setOpaque(false);
        // scrollPane.getViewport().setOpaque(false);

        contentPane.add(playerFieldsPanel, BorderLayout.CENTER);

        // Initialize 2 fields
        updatePlayerFields(2);

        // Spinner listener
        spinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updatePlayerFields((int) spinner.getValue());
            }
        });

        // ================= BOTTOM BUTTON PANEL =================
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(10, 10, 20, 10));

        JButton btnStart = new JButton("Start Game!");
        btnStart.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnStart.setPreferredSize(new Dimension(180, 50));

        bottomPanel.add(btnStart);

        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }

    // ================= UPDATE TEXT FIELDS =================
    private void updatePlayerFields(int count) {

        playerFieldsPanel.removeAll();

        for (int i = 1; i <= count; i++) {
            JTextField tf = new JTextField();
            tf.setFont(new Font("Tahoma", Font.PLAIN, 14));
            tf.setMaximumSize(new Dimension(250, 40));

            tf.setBorder(BorderFactory.createTitledBorder(
                    "Player " + i + " Name"));

            playerFieldsPanel.add(tf);
            playerFieldsPanel.add(Box.createVerticalStrut(15)); // spacing
        }

        playerFieldsPanel.revalidate();
        playerFieldsPanel.repaint();
    }
}
