package com.group13.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import com.group13.Singelton.GameData;
import com.group13.Singelton.PlayerTurnManager;

public class CategoryScreen extends JPanel {
    private MainFrame mainFrame;
    private JButton[] categoryButtons;

    public CategoryScreen(MainFrame frame) {
    
        setLayout(new GridLayout(0, 2, 10, 10)); // dynamic rows, 2 columns
        setBackground(Color.BLUE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GameData data = GameData.getInstance();
        List<String> categories = data.getCategories();

        categoryButtons = new JButton[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            String category = categories.get(i);

            JButton btn = new JButton(category);
            btn.setFont(new Font("Tahoma", Font.BOLD, 20));

            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // add functionality to handle category selection and show question value and then show question 
                }
            });

            categoryButtons[i] = btn;
            add(btn);
        }
    }
    
}
