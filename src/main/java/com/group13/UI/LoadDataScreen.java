package com.group13.UI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.group13.Singelton.*;
import com.group13.TemplatePattern_LoadData.*;

public class LoadDataScreen extends JPanel implements Screen {
    private JComboBox<String> dataTypeCombo;
    private MainFrame mainFrame;
    private GameController controller = GameController.getInstance();

    public LoadDataScreen(MainFrame frame) {
        this.mainFrame = frame;
        this.controller = GameController.getInstance();
        
        setBackground(new Color(0, 128, 255));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        addComponents();
    }

    private void addComponents() {
        JLabel titleLabel = new JLabel("MULTIPLAYER JEOPARDY GAME!");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(60, 50, 400, 40);
        add(titleLabel);

        JButton btnLoad = new JButton("Load Game Data");
        btnLoad.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnLoad.setBounds(172, 180, 150, 50);
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLoadData();
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

    private void handleLoadData() {
        String selectedType = (String) dataTypeCombo.getSelectedItem();
        TemplateLoadData template = createLoader(selectedType);

        if (template == null) {
            System.out.println("Invalid selection");
            return;
        }

        controller.loadGame(template);
        mainFrame.showScreen(new ChoosePlayersScreen(mainFrame));
    }

    private TemplateLoadData createLoader(String type) {
        switch (type) {
            case "XML":  return new LoadDataXML("DataFiles\\sample_game_XML.xml");
            case "CSV":  return new LoadDataCSV("DataFiles\\sample_game_CSV.csv");
            case "JSON": return new LoadDataJSON("DataFiles\\sample_game_JSON.json");
            default:     return null;
        }
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
