package com.group13;

import com.group13.GamePlay.Turn;
import com.group13.ReportStrat.*;
import com.group13.Singelton.GameHistory;
import com.group13.Singelton.GameData;
import com.group13.Logging.EventLogger;
import com.group13.Logging.LogEntry;
import com.group13.Players.Player;
import com.group13.Questions.Question;
import com.group13.TemplatePattern_LoadData.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;
import java.util.List;


public class DataLoadingTest {
    private GameData gameData;
    
    @Before
    public void setUp() {
        gameData = GameData.getInstance();
        // Clear data before each test
        gameData.getQuestions().clear();

    }

    //Test to Load test data from CSV file
    @Test
    public void loadTestDataFromCSV() {
        TemplateLoadData loader = new LoadDataCSV("Test_data/Test_data_CSV.csv");
        loader.loadData();
        assertNotNull("GameData instance should not be null after loading CSV", gameData.getQuestions());
    }

    //Test to Load test data from JSON file
    @Test
    public void loadTestDataFromJSON() {
        TemplateLoadData loader = new LoadDataJSON("Test_data/Test_data_JSON.json");
        loader.loadData();
        assertNotNull("GameData instance should not be null after loading JSON", gameData.getQuestions());
    }

    //Test to Load test data from XML file
    @Test
    public void loadTestDataFromXML() {
        TemplateLoadData loader = new LoadDataXML("Test_data/Test_data_XML.xml");
        loader.loadData();  
        assertNotNull("GameData instance should not be null after loading XML", gameData.getQuestions());
    }

     @Test
    public void testCSVLoadsCorrectQuestionValues() {
        TemplateLoadData loader = new LoadDataCSV("Test_data/Test_data_CSV.csv");
        loader.loadData();
        
        List<Question> questions = gameData.getQuestions();
        // Verify questions have correct point values
        assertEquals(100, questions.get(0).getValue());
        assertEquals(200, questions.get(1).getValue());
        assertEquals(300, questions.get(2).getValue());
        assertEquals(500, questions.get(3).getValue());
    }

    @Test
    public void testLoadedQuestionsHaveCategories() {
        TemplateLoadData loader = new LoadDataCSV("Test_data/Test_data_CSV.csv");
        loader.loadData();
        
        List<Question> questions =  gameData.getQuestions();
        // Verify each question has a non-empty category
        for (Question q : questions) {
            assertNotNull("Question should have category", q.getCategory());
            assertFalse("Category should not be empty", q.getCategory().isEmpty());
        }
    }


}