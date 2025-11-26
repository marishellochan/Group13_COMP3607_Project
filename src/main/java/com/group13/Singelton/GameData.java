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

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        for (Question q : questions) {
            String category = q.getCategory();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }
        return categories;
    }

    public List<Integer> getValues(){
        List<Integer> values = new ArrayList<>();
        for (Question q : questions) {
            int value = q.getValue();
            if (!values.contains(value)) {
                values.add(value);
            }
        }
        return values;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Question> getQuestionsByCategory(String category) {
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question q : questions) {
            if (q.getCategory().equals(category)) {
                filteredQuestions.add(q);
            }
        }
        return filteredQuestions;
    }

    public Question getQuestionByCategoryAndValue(String category, int value) {
        for (Question q : questions) {
            if (q.getCategory().equals(category) && q.getValue() == value) {
                return q;
            }
        }
        return null; // not found
    }


}
