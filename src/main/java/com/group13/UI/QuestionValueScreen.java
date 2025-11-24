package com.group13.UI;

import java.util.List;
import com.group13.Singelton.GameData;
import com.group13.Questions.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class QuestionValueScreen extends JPanel implements Screen {
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
                    Question question = data.getQuestionByCategoryAndValue(category, value);
                    frame.showScreen(new QuestionandAnswerScreen(frame, question));
                }
            });

            valueButtons[i] = btn;
            add(btn);
        }

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