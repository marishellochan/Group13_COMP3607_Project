package com.group13.TemplatePattern_LoadData;

import java.io.FileReader;

import com.group13.Singelton.GameData;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.util.List;

public class LoadDataCSV extends TemplateLoadData {
    protected List<String[]> allGameData;
    private CSVParser csvParser;
    private CSVReader csvReader;

            
    @Override
    protected void readandparseData() {
        System.out.println("Reading data from CSV file...");
        try{
            String filePath = "C:\\Users\\maris\\Downloads\\sample_game_CSV.csv";
            // Implementation for reading CSV file
            FileReader fileReader = new FileReader(filePath);
            csvParser = new CSVParserBuilder().withSeparator(',').build();
            csvReader = new CSVReaderBuilder(fileReader).withCSVParser(csvParser).withSkipLines(0).build();
            allGameData = csvReader.readAll();
            csvReader.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void storeData() {
        System.out.println("Storing CSV data...");
        // Implementation for storing CSV data
        GameData gameData = GameData.getInstance();
        if(allGameData.isEmpty()){
            System.out.println("No data found in CSV file.");
            return;
        } 

        for(i = 1; i < allGameData.size(); i++) { // Assuming first row is header
            String[] row = allGameData.get(i);
        
            String category = row[0];
            String question = row[2].trim();
            String optionA = row[3].trim();
            String optionB = row[4].trim();
            String optionC = row[5].trim();
            String optionD = row[6].trim();
            String correctAnswer = row[7].trim();

            // Create Question object and add to GameData
            // Assuming a MultipleChoiceQuestion class exists
            MultipleChoiceQuestion question = new MultipleChoiceQuestion(
                questionText, optionA, optionB, optionC, optionD, correctAnswer
            );
            gameData.addQuestion(question);
        }

    }


}
