package com.group13.Questions;

public class Question {
    private String questionText;
    private String answer;
    private int value;
    private String category;
    private boolean answered;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public Question(String questionText, int value, String optionA, String optionB, String optionC, String optionD, String answer, String category) {
        this.questionText = questionText;
        this.answer = answer;
        this.value = value;
        this.category = category;
        this.answered = false;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public int getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public boolean checkAnswer(String answer) {
        return this.answer.equals(answer);
    }
    
    public boolean isAnswered() {
        return answered;
    }
}