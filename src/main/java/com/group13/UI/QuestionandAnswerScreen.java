package com.group13.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import com.group13.Singelton.GameData;
import com.group13.Singelton.PlayerTurnManager;
import com.group13.Questions.Question;
import com.group13.Players.Player;

public class QuestionandAnswerScreen extends JPanel implements Screen {
    private JLabel lblPlayerName;
    private JLabel lblQuestion;
    private JRadioButton optA, optB, optC, optD;
    private ButtonGroup optionGroup;
    private Question currentQuestion;
    private MainFrame mainFrame;

    public QuestionandAnswerScreen(MainFrame frame, Question question) {
        this.currentQuestion = question;
        this.mainFrame = frame;

        setSize(500, 450);
        setBackground(Color.YELLOW);
        setLayout(null);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //this shows the current player on top of the screen
        JLabel lblPlayerTxt = new JLabel("Currently Player: ");
        lblPlayerTxt.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblPlayerTxt.setBounds(20, 20, 130, 20);
        add(lblPlayerTxt);

        lblPlayerName = new JLabel(PlayerTurnManager.getInstance().getCurrentPlayer().getPlayerName());
        lblPlayerName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPlayerName.setBounds(150, 20, 150, 20);
        add(lblPlayerName);


        // // Quit Button
        // JButton quitBtn = new JButton("QUIT");
        // quitBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
        // quitBtn.setBackground(Color.WHITE);
        // quitBtn.setForeground(Color.RED);
        // quitBtn.setBounds(390, 20, 80, 25);
        // quitBtn.addActionListener(e -> System.exit(0));
        // add(quitBtn);

        JButton quitBtn = QuitButtonFactory.createQuitButton(Color.RED); // Default white bg, red text
        quitBtn.setBounds(390, 20, 80, 25);
        add(quitBtn);

        // displays the question
        JLabel lblQuestionTitle = new JLabel("QUESTION");
        lblQuestionTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblQuestionTitle.setBounds(200, 60, 150, 25);
        add(lblQuestionTitle);

        lblQuestion = new JLabel("<html>" + question.getQuestionText() + "</html>");
        lblQuestion.setBounds(60, 100, 380, 80);
        lblQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(lblQuestion);

        
        //answer options
        optionGroup = new ButtonGroup();

        JLabel lblA = new JLabel("A");
        lblA.setBounds(60, 210, 20, 20);
        add(lblA);

        optA = new JRadioButton(question.getOptionA());
        optA.setBounds(90, 210, 150, 20);
        add(optA);
        optionGroup.add(optA);

        JLabel lblB = new JLabel("B");
        lblB.setBounds(60, 250, 20, 20);
        add(lblB);

        optB = new JRadioButton(question.getOptionB());
        optB.setBounds(90, 250, 150, 20);
        add(optB);
        optionGroup.add(optB);

        JLabel lblC = new JLabel("C");
        lblC.setBounds(260, 210, 20, 20);
        add(lblC);

        optC = new JRadioButton(question.getOptionC());
        optC.setBounds(290, 210, 150, 20);
        add(optC);
        optionGroup.add(optC);

        JLabel lblD = new JLabel("D");
        lblD.setBounds(260, 250, 20, 20);
        add(lblD);

        optD = new JRadioButton(question.getOptionD());
        optD.setBounds(290, 250, 150, 20);
        add(optD);
        optionGroup.add(optD);

        //submit button 
        JButton submitBtn = new JButton("SUBMIT");
        submitBtn.setBounds(200, 320, 100, 30);
        submitBtn.setFont(new Font("Tahoma", Font.BOLD, 14));

        submitBtn.addActionListener((ActionEvent e) -> handleSubmit());

        add(submitBtn);
    }

    
    private void handleSubmit() {

        String chosen = null;

        if (optA.isSelected()) chosen = "A";
        if (optB.isSelected()) chosen = "B";
        if (optC.isSelected()) chosen = "C";
        if (optD.isSelected()) chosen = "D";

        if (chosen == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an answer!",
                    "No selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Check answer
        boolean correct = currentQuestion.checkAnswer(chosen);

        PlayerTurnManager ptm = PlayerTurnManager.getInstance();
        Player currentPlayer = ptm.getCurrentPlayer();

        if (correct) {
            currentPlayer.addPoints(currentQuestion.getValue());
            JOptionPane.showMessageDialog(this, "Correct!", "Nice!", JOptionPane.INFORMATION_MESSAGE);
             // Mark answered
             currentQuestion.setAnswered();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect!", "Oops!", JOptionPane.ERROR_MESSAGE);
        }

       

        // Move to next player
        ptm.nextTurn();

        // Go back to start game screen and shows next players turn
        mainFrame.showScreen(new StartGameScreen(mainFrame));
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}