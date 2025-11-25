package com.group13.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import com.group13.Singelton.Game;
import com.group13.Singelton.GameController;
import com.group13.Singelton.GameData;
import com.group13.Singelton.PlayerTurnManager;
import com.group13.Logging.LogEntry;
import com.group13.Questions.Question;

public class CategoryScreen extends JPanel implements Screen {
    private MainFrame mainFrame;
    private JButton[] categoryButtons;
    private GameController controller;

    public CategoryScreen(MainFrame frame) {
        this.mainFrame = frame;
        this.controller = GameController.getInstance();
        
        setLayout(new BorderLayout());
        setBackground(Color.BLUE);

        add(QuitButtonFactory.createQuitButtonPanel(Color.RED), BorderLayout.NORTH);
        add(createCategoryPanel(), BorderLayout.CENTER);
        
        updateCategoryButtons();
    }

    private JPanel createCategoryPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.BLUE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        List<String> categories = controller.getAvailableCategories();
        categoryButtons = new JButton[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            String category = categories.get(i);
            JButton btn = new JButton(category);
            btn.setFont(new Font("Tahoma", Font.BOLD, 20));
            btn.addActionListener( new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleCategorySelection(category);
                }
            });
            
            categoryButtons[i] = btn;
            panel.add(btn);
        }
        
        return panel;
    }

    private void handleCategorySelection(String category) {
        if (!controller.isCategoryAvailable(category)) {
            JOptionPane.showMessageDialog(mainFrame,
                "All questions in this category have been answered. Please choose another category.",
                "Category Empty", JOptionPane.INFORMATION_MESSAGE);
        } else {
            controller.selectCategory(category);
            mainFrame.showScreen(new QuestionValueScreen(mainFrame, category));
        }
    }

    private void updateCategoryButtons() {
        for (JButton btn : categoryButtons) {
            String category = btn.getText();
            btn.setEnabled(controller.isCategoryAvailable(category));
        }
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}