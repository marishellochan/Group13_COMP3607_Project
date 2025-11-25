package com.group13.UI;

import java.util.List;

import com.group13.Singelton.Game;
import com.group13.Singelton.GameController;
import com.group13.Singelton.GameData;
import com.group13.Singelton.PlayerTurnManager;
import com.group13.Logging.LogEntry;
import com.group13.Questions.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class QuestionValueScreen extends JPanel implements Screen {
    private MainFrame mainFrame;
    private JButton[] valueButtons;
    private GameData gameData;
    private GameController controller;
    private String selectedCategory;

    public QuestionValueScreen(MainFrame frame, String category) {
        this.mainFrame = frame;
        this.selectedCategory = category;
        this.gameData = GameData.getInstance();
        this.controller = GameController.getInstance();
        
        setLayout(new BorderLayout());
        setBackground(Color.GREEN);

        JPanel topPanel = QuitButtonFactory.createQuitButtonPanel(Color.RED);
        topPanel.setOpaque(false);
        add(topPanel, BorderLayout.NORTH);
        
        add(createValuePanel(), BorderLayout.CENTER);
        
        updateValueButtons();
    }

    private JPanel createValuePanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.GREEN);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Integer> values = gameData.getValues();
        valueButtons = new JButton[values.size()];

        for (int i = 0; i < values.size(); i++) {
            Integer value = values.get(i);
            JButton btn = new JButton(value.toString());
            btn.setFont(new Font("Tahoma", Font.BOLD, 20));
            btn.addActionListener( new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleValueSelection(value);
                }
            });
            
            valueButtons[i] = btn;
            panel.add(btn);
        }
        
        return panel;
    }

    private void handleValueSelection(Integer value) {
        Question question = controller.selectQuestion(selectedCategory, value);
        if (question != null) {
            mainFrame.showScreen(new QuestionandAnswerScreen(mainFrame, question));
        }
    }

    private void updateValueButtons() {
        List<Integer> values = controller.getQuestionValues();
        
        for (int i = 0; i < valueButtons.length; i++) {
            Integer value = values.get(i);
            boolean isAnswered = !controller.isQuestionAvailable(selectedCategory, value);
            valueButtons[i].setEnabled(!isAnswered);
        }
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}