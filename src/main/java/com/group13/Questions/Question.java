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

    public Question(){
        this.answered = false;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAnswered() {
        this.answered = true;
    }

    public void setOptions(String optionA, String optionB, String optionC, String optionD) {
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

    public String toString() {
        return "Category: " + category + "\n" +
               "Value: " + value + "\n" +
               "Question: " + questionText + "\n" +
               "A: " + optionA + "\n" +
               "B: " + optionB + "\n" +
               "C: " + optionC + "\n" +
               "D: " + optionD + "\n";
    }
}