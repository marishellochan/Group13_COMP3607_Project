package com.group13.UI;

import java.util.List;

import com.group13.Singelton.Game;
import com.group13.Singelton.GameData;
import com.group13.Singelton.PlayerTurnManager;
import com.group13.Logging.LogEntry;
import com.group13.Questions.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class QuestionValueScreen extends JPanel implements Screen {
    private JButton[] valueButtons;

    public QuestionValueScreen(MainFrame frame, String category) {
        setLayout(new BorderLayout());
        setBackground(Color.GREEN);

        // // quit button
        JPanel topPanel = QuitButtonFactory.createQuitButtonPanel(Color.RED);
        topPanel.setOpaque(false);
        add(topPanel, BorderLayout.NORTH);

        // value buttons
        JPanel centerPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        centerPanel.setBackground(Color.GREEN);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GameData data = GameData.getInstance();
        List<Integer> values = data.getValues();

        valueButtons = new JButton[values.size()];

        for (int i = 0; i < values.size(); i++) {
            Integer value = values.get(i);

            JButton btn = new JButton(value.toString());
            btn.setFont(new Font("Tahoma", Font.BOLD, 20));

            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Question question = data.getQuestionByCategoryAndValue(category, value);
                    PlayerTurnManager ptm = PlayerTurnManager.getInstance();
                    LogEntry entry = LogEntry.createSelectQuestionEvent(
                        String.valueOf(ptm.getCurrentPlayer().getPlayerId()),category, value);
                    Game.getInstance().notifyEventLogger(entry);
                    frame.showScreen(new QuestionandAnswerScreen(frame, question));
                }
            });

            valueButtons[i] = btn;
            centerPanel.add(btn);
        }

        add(centerPanel, BorderLayout.CENTER);

        updateValueButtons(category);
    }

    public void updateValueButtons(String category) {
        GameData data = GameData.getInstance();

        for (int i = 0; i < valueButtons.length; i++) {
            Integer value = data.getValues().get(i);
            Question question = data.getQuestionByCategoryAndValue(category, value);
            boolean isAnswered = question.isAnswered();
            valueButtons[i].setEnabled(!isAnswered);
        }
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}