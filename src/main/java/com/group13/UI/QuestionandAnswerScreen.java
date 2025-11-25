package com.group13.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group13.Singelton.Game;
import com.group13.Singelton.GameController;
import com.group13.Singelton.GameData;
import com.group13.Singelton.PlayerTurnManager;
import com.group13.Questions.Question;
import com.group13.Logging.LogEntry;
import com.group13.Players.Player;

public class QuestionandAnswerScreen extends JPanel implements Screen {
    private JRadioButton optA, optB, optC, optD;
    private ButtonGroup optionGroup;
    private Question currentQuestion;
    private MainFrame mainFrame;
    private GameController controller;

    public QuestionandAnswerScreen(MainFrame frame, Question question) {
        this.currentQuestion = question;
        this.mainFrame = frame;
        this.controller = GameController.getInstance();
        
        setupPanel();
        addPlayerInfo();
        addQuestionDisplay();
        addAnswerOptions();
        addSubmitButton();
    }

    private void setupPanel() {
        setSize(500, 450);
        setBackground(Color.YELLOW);
        setLayout(null);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Quit button
        JButton quitBtn = QuitButtonFactory.createQuitButton(Color.RED);
        quitBtn.setBounds(390, 20, 80, 25);
        add(quitBtn);
    }

    private void addPlayerInfo() {
        JLabel lblPlayerTxt = new JLabel("Currently Player: ");
        lblPlayerTxt.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblPlayerTxt.setBounds(20, 20, 130, 20);
        add(lblPlayerTxt);

        String playerName = controller.getCurrentPlayer().getPlayerName();
        JLabel lblPlayerName = new JLabel(playerName);
        lblPlayerName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPlayerName.setBounds(150, 20, 150, 20);
        add(lblPlayerName);
    }

    private void addQuestionDisplay() {
        JLabel lblTitle = new JLabel("QUESTION");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setBounds(200, 60, 150, 25);
        add(lblTitle);

        JLabel lblQuestion = new JLabel("<html>" + currentQuestion.getQuestionText() + "</html>");
        lblQuestion.setBounds(60, 100, 380, 80);
        lblQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(lblQuestion);
    }

    private void addAnswerOptions() {
        optionGroup = new ButtonGroup();
        
        optA = addOption("A", currentQuestion.getOptionA(), 60, 90, 210);
        optB = addOption("B", currentQuestion.getOptionB(), 60, 90, 250);
        optC = addOption("C", currentQuestion.getOptionC(), 260, 290, 210);
        optD = addOption("D", currentQuestion.getOptionD(), 260, 290, 250);
    }

    private JRadioButton addOption(String label, String text, int labelX, int buttonX, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(labelX, y, 20, 20);
        add(lbl);

        JRadioButton radio = new JRadioButton(text);
        radio.setBounds(buttonX, y, 150, 20);
        add(radio);
        optionGroup.add(radio);
        return radio;
    }

    private void addSubmitButton() {
        JButton submitBtn = new JButton("SUBMIT");
        submitBtn.setBounds(200, 320, 100, 30);
        submitBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });
        add(submitBtn);
    }

    private void handleSubmit() {
        String answer = getSelectedAnswer();
        if (answer == null) {
            JOptionPane.showMessageDialog(this, "Please select an answer!", 
                "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean correct = controller.submitAnswer(currentQuestion, answer);
        
        if (correct) {
            JOptionPane.showMessageDialog(this, "Correct!", "Nice!", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect!", "Oops!", 
                JOptionPane.ERROR_MESSAGE);
        }

        // Check if game is over
        if (controller.isGameOver()) {
            mainFrame.showScreen(new GameOverScreen(mainFrame));
        } else {
            controller.nextTurn();
            mainFrame.showScreen(new StartGameScreen(mainFrame));
        }
    }

    private String getSelectedAnswer() {
        if (optA.isSelected()) return "A";
        if (optB.isSelected()) return "B";
        if (optC.isSelected()) return "C";
        if (optD.isSelected()) return "D";
        return null;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}