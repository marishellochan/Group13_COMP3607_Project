package com.group13.Questions;

public class ControlStructureQuestion implements Question {
    private static int value = 0;

    @Override
    public String getQuestionText() {
        return null;
    }

    @Override
    public String getAnswer() {
        return null;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public boolean checkAnswer(String answer) {
        return false;
    }

    @Override
    public boolean isCorrect() {
        return false;
    }

    @Override
    public boolean isAnswered() {
        return false;
    }
}