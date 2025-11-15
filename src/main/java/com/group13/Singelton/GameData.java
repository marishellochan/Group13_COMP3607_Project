package com.group13.Singelton;
import com.group13.Questions.*;
import java.util.List;
import java.util.ArrayList;

public class GameData {
    private static GameData instance = null;
    private List<Question> questions;

    private GameData() {
        questions = new ArrayList<Question>();
    }

    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void printQuestions() {
        for (Question q : questions) {
            System.out.println(q);
        }
    }
}
