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

public class LoadDataScreen extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> dataTypeCombo;
   
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoadDataScreen frame = new LoadDataScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoadDataScreen() {

        setSize(new Dimension(483, 341));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150, 150, 500, 500);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 128, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JButton btnLoad = new JButton("Load Game Data");
        btnLoad.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnLoad.setBounds(172, 180, 150, 50);
        btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleLoadButtonClick();
			}
		});
        contentPane.add(btnLoad);

        JLabel label = new JLabel("Select File to Load Game Data from:");
        label.setBounds(172, 250, 200, 25);
        contentPane.add(label);

        dataTypeCombo = new JComboBox<>(new String[]{"XML", "CSV", "JSON"});
        dataTypeCombo.setBounds(172, 280, 150, 25);
        contentPane.add(dataTypeCombo);
    }

    private void handleLoadButtonClick() {
        String selectedType = (String) dataTypeCombo.getSelectedItem();
		TemplateLoadData template = null;
		switch(selectedType) {
			case "XML": template = new LoadDataXML(); break;
			case "CSV": template = new LoadDataCSV(); break;
			case "JSON": template = new LoadDataJSON(); break;
			default: System.out.println("Invalid selection"); return;
		}
        Game game = Game.getInstance();
		GameData gameData = GameData.getInstance();
		game.load(template);
		gameData.printQuestions();
    }



}
