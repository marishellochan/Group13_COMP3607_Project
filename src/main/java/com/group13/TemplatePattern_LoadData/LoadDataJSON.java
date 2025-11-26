package com.group13.TemplatePattern_LoadData;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.group13.ExceptionHandling.NoDataException;
import com.group13.Questions.Question;
import com.group13.Singelton.GameData;

public class LoadDataJSON extends TemplateLoadData {
    protected JsonArray jsonArray;
    protected List<JsonObject> allQuestionObjects;
    private Reader reader;
    @Override
    protected void readData() throws JsonSyntaxException {
        
        System.out.println("Reading data from JSON file...");
        try{
            reader = new FileReader("DataFiles/sample_game_JSON.json");
            JsonElement jsonElement = JsonParser.parseReader(reader);

            if (!jsonElement.isJsonArray()) {
                throw new JsonSyntaxException("Expected a JSON array");
            }
            jsonArray = jsonElement.getAsJsonArray();
            allQuestionObjects = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                if (element.isJsonObject()) {
                    allQuestionObjects.add(element.getAsJsonObject());
                }
            }
            System.out.println("JSON data read successfully.");
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    @Override
    protected void parseAndStoreData() throws NoDataException {
        System.out.println("Storing JSON data...");
        GameData gameData = GameData.getInstance();

        if (allQuestionObjects.isEmpty()) {
            throw new NoDataException("No data to parse and store.");
        }

        for (JsonObject jsonObject : allQuestionObjects) {
            Question q = new Question();
            q.setCategory(jsonObject.get("Category").getAsString());
            q.setValue(jsonObject.get("Value").getAsInt());
            q.setQuestionText(jsonObject.get("Question").getAsString());
            JsonObject options = jsonObject.getAsJsonObject("Options");
            q.setOptions(
                options.get("A").getAsString(),
                options.get("B").getAsString(),
                options.get("C").getAsString(),
                options.get("D").getAsString()
            );
            q.setAnswer(jsonObject.get("CorrectAnswer").getAsString());
            gameData.addQuestion(q);

        }
    }
    
}
