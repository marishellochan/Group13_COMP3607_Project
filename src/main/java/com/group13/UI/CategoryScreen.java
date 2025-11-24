package com.group13.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import com.group13.Singelton.GameData;
import com.group13.Singelton.PlayerTurnManager;
import com.group13.Questions.Question;

public class CategoryScreen extends JPanel implements Screen {
    private MainFrame mainFrame;
    private JButton[] categoryButtons;

    public CategoryScreen(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(Color.BLUE);

        // // Top panel for quit button
        // JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // topPanel.setBackground(Color.BLUE);
        
        // JButton quitBtn = new JButton("QUIT");
        // quitBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
        // quitBtn.setBackground(Color.RED);
        // quitBtn.setForeground(Color.WHITE);
        // quitBtn.setPreferredSize(new Dimension(80, 30));
        // quitBtn.addActionListener(e -> System.exit(0));
        // topPanel.add(quitBtn);

        JPanel topPanel = QuitButtonFactory.createQuitButtonPanel(Color.RED);
        
        add(topPanel, BorderLayout.NORTH);

        // Center panel for category buttons
        JPanel centerPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        centerPanel.setBackground(Color.BLUE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GameData data = GameData.getInstance();
        List<String> categories = data.getCategories();

        categoryButtons = new JButton[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            String category = categories.get(i);

            JButton btn = new JButton(category);
            btn.setFont(new Font("Tahoma", Font.BOLD, 20));

            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (isCategoryEmpty(category)) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "All questions in this category have been answered. Please choose another category.",
                                "Category Empty",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        frame.showScreen(new QuestionValueScreen(frame, category));
                    }
                }
            });

            categoryButtons[i] = btn;
            centerPanel.add(btn);
        }

        add(centerPanel, BorderLayout.CENTER);
        
        updateCategoryButtons();
    }

    public boolean isCategoryEmpty(String category) {
        GameData data = GameData.getInstance();
        List<Question> qs = data.getQuestionsByCategory(category);

        for (Question q : qs) {
            if (!q.isAnswered()) {
                return false;  // still some remaining
            }
        }
        return true; // ALL answered
    }

    public void updateCategoryButtons() {
        for (JButton btn : categoryButtons) {
            String category = btn.getText();
            btn.setEnabled(!isCategoryEmpty(category));
        }
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}