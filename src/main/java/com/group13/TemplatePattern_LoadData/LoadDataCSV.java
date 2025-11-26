package com.group13.TemplatePattern_LoadData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;

import com.group13.ExceptionHandling.NoDataException;
import com.group13.Questions.Question;
import com.group13.Singelton.GameData;

import java.util.List;

public class LoadDataCSV extends TemplateLoadData {
    protected List<String[]> allGameData;
    protected BufferedReader br;
    protected List<String> lines;

            
    @Override
    protected void readData() {
        System.out.println("Reading data from CSV file...");
        try{
            lines = new ArrayList<>();
            String filePath = "Group13_COMP3607_Project\\sample_data\\sample_game_CSV.csv";
            // Implementation for reading CSV file
            br = new BufferedReader(new FileReader(filePath));
            String line;
            
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("CSV file not found: " + e.getMessage());
        }catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }


    @Override
    protected void parseAndStoreData() throws NoDataException {
        System.out.println("Storing CSV data...");
        // Implementation for storing CSV data
        GameData gameData = GameData.getInstance();
        if(lines.isEmpty()){
           throw new NoDataException("No data to parse and store.");
        } 

        for(int i = 1; i < lines.size(); i++) {
            String[] row = lines.get(i).split(",");

            String category = row[0].trim();
            int value = Integer.parseInt(row[1].trim());
            String question = row[2].trim();
            String optionA = row[3].trim();
            String optionB = row[4].trim();
            String optionC = row[5].trim();
            String optionD = row[6].trim();
            String correctAnswer = row[7].trim();

            // Create Question object and add to GameData
            // Assuming a MultipleChoiceQuestion class exists
            Question questionObj = new Question();
            questionObj.setCategory(category);
            questionObj.setValue(value);
            questionObj.setQuestionText(question);
            questionObj.setOptions(optionA, optionB, optionC, optionD);
            questionObj.setAnswer(correctAnswer);
            
            gameData.addQuestion(questionObj);
        }

    }


}
