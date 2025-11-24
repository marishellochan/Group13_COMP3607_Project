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
import com.group13.Singelton.*;
import com.group13.TemplatePattern_LoadData.*;

public class LoadDataScreen extends JPanel implements Screen {

	private JComboBox<String> dataTypeCombo;
	
	public LoadDataScreen(MainFrame frame) {
        setBackground(new Color(0, 128, 255));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        JButton btnLoad = new JButton("Load Game Data");
        btnLoad.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnLoad.setBounds(172, 180, 150, 50);
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLoadButtonClick(frame);
            }
        });
        add(btnLoad);

        JLabel label = new JLabel("Select File to Load Game Data from:");
        label.setBounds(172, 250, 250, 25);
        add(label);

        dataTypeCombo = new JComboBox<>(new String[]{"XML", "CSV", "JSON"});
        dataTypeCombo.setBounds(172, 280, 150, 25);
        add(dataTypeCombo);
    }

    private void handleLoadButtonClick(MainFrame frame) {
        String selectedType = (String) dataTypeCombo.getSelectedItem();
        TemplateLoadData template = null;

        switch (selectedType) {
            case "XML": template = new LoadDataXML(); break;
            case "CSV": template = new LoadDataCSV(); break;
            case "JSON": template = new LoadDataJSON(); break;
            default: System.out.println("Invalid selection"); return;
        }

        Game game = Game.getInstance();
        GameData gameData = GameData.getInstance();
        game.load(template);
        gameData.printQuestions();

        // Switch to ChoosePlayersScreen inside the same MainFrame
        frame.showScreen(new ChoosePlayersScreen(frame));
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

	




}
