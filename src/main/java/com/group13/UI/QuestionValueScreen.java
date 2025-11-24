package com.group13.UI;

import java.util.List;
import com.group13.Singelton.GameData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class QuestionValueScreen extends JPanel{
    private MainFrame mainFrame;
    private JButton[] valueButtons;

    public QuestionValueScreen(MainFrame frame, String category) {
    
        setLayout(new GridLayout(0, 2, 10, 10)); // dynamic rows, 2 columns
        setBackground(Color.GREEN);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GameData data = GameData.getInstance();
        List<Integer> values = data.getValues();

        valueButtons = new JButton[values.size()];

        for (int i = 0; i < values.size(); i++) {
            Integer value = values.get(i);

            JButton btn = new JButton(value.toString());
            btn.setFont(new Font("Tahoma", Font.BOLD, 20));

            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // add functionality to handle category selection and show question value and then show question 
                }
            });

            valueButtons[i] = btn;
            add(btn);
        }
    }
}