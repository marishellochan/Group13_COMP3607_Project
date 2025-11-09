package com.group13.Questions;

public interface Question {
    String getQuestionText();
    String getAnswer();
    int getValue();
    boolean checkAnswer(String answer);
    boolean isCorrect();
    boolean isAnswered();
}