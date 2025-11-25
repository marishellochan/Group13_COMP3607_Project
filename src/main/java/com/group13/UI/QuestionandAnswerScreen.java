package com.group13.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group13.Singelton.Game;
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

    public QuestionandAnswerScreen(MainFrame frame, Question question) {
        this.currentQuestion = question;
        this.mainFrame = frame;
        
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

        String playerName = PlayerTurnManager.getInstance().getCurrentPlayer().getPlayerName();
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
        submitBtn.addActionListener(e -> handleSubmit());
        add(submitBtn);
    }

    private void handleSubmit() {
        String answer = getSelectedAnswer();
        if (answer == null) {
            JOptionPane.showMessageDialog(this, "Please select an answer!", 
                "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean correct = currentQuestion.checkAnswer(answer);
        Player player = PlayerTurnManager.getInstance().getCurrentPlayer();
        
        if (correct) {
            player.addPoints(currentQuestion.getValue());
            JOptionPane.showMessageDialog(this, "Correct!", "Nice!", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect!", "Oops!", 
                JOptionPane.ERROR_MESSAGE);
        }

        currentQuestion.setAnswered();
        logAnswer(player, answer, correct);
        
        PlayerTurnManager.getInstance().nextTurn();
        mainFrame.showScreen(new StartGameScreen(mainFrame));
    }

    private String getSelectedAnswer() {
        if (optA.isSelected()) return "A";
        if (optB.isSelected()) return "B";
        if (optC.isSelected()) return "C";
        if (optD.isSelected()) return "D";
        return null;
    }

    private void logAnswer(Player player, String answer, boolean correct) {
        LogEntry entry = LogEntry.createAnswerQuestionEvent(
            String.valueOf(player.getPlayerId()),
            currentQuestion.getCategory(),
            currentQuestion.getValue(),
            answer,
            correct ? "Correct" : "Incorrect",
            player.getScore()
        );
        Game.getInstance().notifyEventLogger(entry);
        
        if (correct) {
            LogEntry scoreEntry = LogEntry.scoreUpdatedEvent(
                String.valueOf(player.getPlayerId()), 
                player.getScore()
            );
            Game.getInstance().notifyEventLogger(scoreEntry);
        }
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}