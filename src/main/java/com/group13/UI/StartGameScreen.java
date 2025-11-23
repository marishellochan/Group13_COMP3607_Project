package com.group13.UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;

import java.awt.Font;

import com.group13.State.*;
import com.group13.Singelton.*;
import com.group13.TemplatePattern_LoadData.*;

public class StartGameScreen extends JFrame {

	private JPanel contentPane;
   
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartGameScreen frame = new StartGameScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	

	
	public StartGameScreen() {

        setSize(new Dimension(483, 341));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150, 150, 500, 500);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JButton btnLoad = new JButton("Start Game !");
        btnLoad.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnLoad.setBounds(172, 180, 150, 50);
        btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleLoadButtonClick();
			}
		});
        contentPane.add(btnLoad);
    }

    private void handleLoadButtonClick() {
        
    }



}