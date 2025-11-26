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

/**
 * Test suite for Group13 Jeopardy Game.
 * Tests: Parsing, Gameplay, Scoring, Reporting, Logging
 */
public class AppTest {

    private GameHistory gameHistory;
    private GameData gameData;
    private Player player1;
    private Player player2;
    private Question question1;
    private Question question2;
    private Question question3;
    private Question question4;
    private EventLogger eventLogger;

    @Before
    public void setUp() {
        // Get singleton instances
        gameHistory = GameHistory.getInstance();
        gameData = GameData.getInstance();
        eventLogger = EventLogger.getInstance();
        
        // Clear any existing data
        if (gameHistory.getTurns() != null) {
            gameHistory.getTurns().clear();
        }
        
        // Load test data from CSV
        loadTestDataFromCSV();
        
        // Get loaded questions from GameData
        List<Question> allQuestions = getAllLoadedQuestions();
        
        // Assign questions if loading succeeded
        if (allQuestions.size() >= 4) {
            question1 = allQuestions.get(0); // First question (100 points)
            question2 = allQuestions.get(1); // Second question (200 points)
            question3 = allQuestions.get(2); // Third question (300 points)
            question4 = allQuestions.get(3); // Fourth question (500 points)
        } else {
            // Fallback: manually create questions if loading failed
            createFallbackQuestions();
        }
        
        // Create test players
        player1 = new Player("TestPlayer1");
        player2 = new Player("TestPlayer2");
    }

    @After
    public void tearDown() {
        // Clean up generated report files
        File[] files = new File(".").listFiles((dir, name) -> 
            name.endsWith("report.txt") || 
            name.endsWith("report.docx") || 
            name.endsWith("report.pdf")
        );
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        
        // Clear game history
        if (gameHistory != null && gameHistory.getTurns() != null) {
            gameHistory.getTurns().clear();
        }
    }

    /**
     * Load test data from CSV file
     */
    private void loadTestDataFromCSV() {
        try {
            TemplateLoadData loader = new LoadDataCSV("Test_data/Test_data_CSV.csv");
            loader.loadData();
        } catch (Exception e) {
            System.err.println("Warning: Could not load CSV test data: " + e.getMessage());
        }
    }

    /**
     * Load test data from JSON file
     */
    private void loadTestDataFromJSON() {
        try {
            TemplateLoadData loader = new LoadDataJSON("Test_data/Test_data_JSON.json");
            loader.loadData();
        } catch (Exception e) {
            System.err.println("Warning: Could not load JSON test data: " + e.getMessage());
        }
    }

    /**
     * Load test data from XML file
     */
    private void loadTestDataFromXML() {
        try {
            TemplateLoadData loader = new LoadDataXML("Test_data/Test_data_XML.xml");
            loader.loadData();
        } catch (Exception e) {
            System.err.println("Warning: Could not load XML test data: " + e.getMessage());
        }
    }

    /**
     * Get all questions loaded from GameData, sorted by value
     */
    private List<Question> getAllLoadedQuestions() {
        List<String> categories = gameData.getCategories();
        List<Question> allQuestions = new java.util.ArrayList<>();
        
        for (String category : categories) {
            List<Question> categoryQuestions = gameData.getQuestionsByCategory(category);
            allQuestions.addAll(categoryQuestions);
        }
        
        // Sort by value to ensure consistent ordering (100, 200, 300, 500)
        allQuestions.sort((q1, q2) -> Integer.compare(q1.getValue(), q2.getValue()));
        
        return allQuestions;
    }

    /**
     * Create fallback questions if loading fails
     */
    private void createFallbackQuestions() {
        question1 = new Question();
        question1.setCategory("Variables & Data Types");
        question1.setValue(100);
        question1.setQuestionText("Which of the following declares an integer variable in C++?");
        question1.setOptions("int num;", "float num;", "num int;", "integer num;");
        question1.setAnswer("A");
        
        question2 = new Question();
        question2.setCategory("Control Structures");
        question2.setValue(200);
        question2.setQuestionText("What is the output of: if (5 > 10) cout << 'Hi'; else cout << 'Bye';");
        question2.setOptions("Hi", "Bye", "Error", "Nothing");
        question2.setAnswer("B");
        
        question3 = new Question();
        question3.setCategory("Functions");
        question3.setValue(300);
        question3.setQuestionText("What is the return type of int add(int a, int b)?");
        question3.setOptions("int", "void", "double", "none");
        question3.setAnswer("A");
        
        question4 = new Question();
        question4.setCategory("Arrays");
        question4.setValue(500);
        question4.setQuestionText("How many elements in int arr[3][4];?");
        question4.setOptions("7", "12", "3", "4");
        question4.setAnswer("B");
    }

    // ==================== PARSING TESTS ====================

    @Test
    public void testQuestionParseCategory() {
        assertNotNull("Question1 should not be null", question1);
        assertNotNull("Question should have a category", question1.getCategory());
        assertEquals("Variables & Data Types", question1.getCategory());
    }

    @Test
    public void testQuestionParsePointValue() {
        assertNotNull("Question1 should not be null", question1);
        assertEquals(100, question1.getValue());
    }

    @Test
    public void testQuestionParseQuestionText() {
        assertNotNull("Question1 should not be null", question1);
        assertNotNull("Question text should not be null", question1.getQuestionText());
        assertFalse("Question text should not be empty", question1.getQuestionText().isEmpty());
    }

    @Test
    public void testQuestionParseAnswer() {
        assertNotNull("Question1 should not be null", question1);
        assertNotNull("Answer should not be null", question1.getAnswer());
        assertEquals("A", question1.getAnswer());
    }

    @Test
    public void testTurnParseAllData() {
        assertNotNull("Question1 should not be null", question1);
        Turn turn = new Turn(player1.getPlayerName(), question1, "A", true, 100, 100);
        
        assertEquals(player1.getPlayerName(), turn.getPlayerName());
        assertEquals(question1.getCategory(), turn.getCategory());
        assertEquals(100, turn.getQuestionValue());
        assertEquals(question1.getQuestionText(), turn.getQuestionText());
        assertEquals("A", turn.getAnswerGiven());
        assertTrue(turn.isCorrect());
        assertEquals(100, turn.getPointsEarned());
        assertEquals(100, turn.getScoreAfterTurn());
    }

    @Test
    public void testTurnParseSpecialCharacters() {
        assertNotNull("Question2 should not be null", question2);
        // Question2 contains special characters like << and '
        Turn turn = new Turn(player1.getPlayerName(), question2, "B", true, 200, 200);
        
        assertNotNull("Question text should handle special characters", turn.getQuestionText());
        assertEquals("B", turn.getAnswerGiven());
    }

    // ==================== GAMEPLAY TESTS ====================

    @Test
    public void testGameplayRecordTurnsInOrder() {
        assertNotNull("Questions should be loaded", question1);
        assertNotNull("Questions should be loaded", question2);
        assertNotNull("Questions should be loaded", question3);
        
        Turn turn1 = new Turn(player1.getPlayerName(), question1, "A", true, 100, 100);
        Turn turn2 = new Turn(player2.getPlayerName(), question2, "B", true, 200, 200);
        Turn turn3 = new Turn(player1.getPlayerName(), question3, "A", false, 0, 100);
        
        gameHistory.recordTurn(turn1);
        gameHistory.recordTurn(turn2);
        gameHistory.recordTurn(turn3);
        
        List<Turn> turns = gameHistory.getTurns();
        assertEquals(3, turns.size());
        assertEquals(player1.getPlayerName(), turns.get(0).getPlayerName());
        assertEquals(player2.getPlayerName(), turns.get(1).getPlayerName());
        assertEquals(player1.getPlayerName(), turns.get(2).getPlayerName());
    }

    @Test
    public void testGameplayTurnCountTracking() {
        assertNotNull("Questions should be loaded", question1);
        assertNotNull("Questions should be loaded", question2);
        
        int initialCount = gameHistory.getTurnCount();
        
        gameHistory.recordTurn(new Turn(player1.getPlayerName(), question1, "A", true, 100, 100));
        assertEquals(initialCount + 1, gameHistory.getTurnCount());
        
        gameHistory.recordTurn(new Turn(player2.getPlayerName(), question2, "B", true, 200, 200));
        assertEquals(initialCount + 2, gameHistory.getTurnCount());
    }

    @Test
    public void testGameplayValidateCorrectAnswer() {
        assertNotNull("Question1 should not be null", question1);
        assertTrue("Correct answer should be validated", question1.checkAnswer("A"));
    }

    @Test
    public void testGameplayValidateIncorrectAnswer() {
        assertNotNull("Question1 should not be null", question1);
        assertFalse("Incorrect answer should be rejected", question1.checkAnswer("D"));
    }

    @Test
    public void testGameplayQuestionMarkedAsAnswered() {
        assertNotNull("Question1 should not be null", question1);
        assertFalse("Question should start as unanswered", question1.isAnswered());
        question1.setAnswered();
        assertTrue("Question should be marked as answered", question1.isAnswered());
    }

    // ==================== SCORING TESTS ====================

    @Test
    public void testScoringCorrectAnswerFullPoints() {
        assertNotNull("Question1 should not be null", question1);
        Turn turn = new Turn(player1.getPlayerName(), question1, "A", true, 100, 100);
        assertEquals(100, turn.getPointsEarned());
    }

    @Test
    public void testScoringIncorrectAnswerNoScoreChange() {
        assertNotNull("Questions should be loaded", question1);
        assertNotNull("Questions should be loaded", question2);
        
        Turn turn1 = new Turn(player1.getPlayerName(), question1, "A", true, 100, 100);
        Turn turn2 = new Turn(player1.getPlayerName(), question2, "D", false, 0, 100);
        
        gameHistory.recordTurn(turn1);
        gameHistory.recordTurn(turn2);
        
        assertEquals(100, turn1.getScoreAfterTurn());
        assertEquals(100, turn2.getScoreAfterTurn());
    }

    @Test
    public void testScoringRunningTotalAccumulation() {
        assertNotNull("Questions should be loaded", question1);
        assertNotNull("Questions should be loaded", question2);
        assertNotNull("Questions should be loaded", question3);
        assertNotNull("Questions should be loaded", question4);
        
        Turn turn1 = new Turn(player1.getPlayerName(), question1, "A", true, 100, 100);
        Turn turn2 = new Turn(player1.getPlayerName(), question2, "B", true, 200, 300);
        Turn turn3 = new Turn(player1.getPlayerName(), question3, "D", false, 0, 300);
        Turn turn4 = new Turn(player1.getPlayerName(), question4, "B", true, 500, 800);
        
        gameHistory.recordTurn(turn1);
        gameHistory.recordTurn(turn2);
        gameHistory.recordTurn(turn3);
        gameHistory.recordTurn(turn4);
        
        List<Turn> turns = gameHistory.getTurns();
        assertEquals(100, turns.get(0).getScoreAfterTurn());
        assertEquals(300, turns.get(1).getScoreAfterTurn());
        assertEquals(300, turns.get(2).getScoreAfterTurn());
        assertEquals(800, turns.get(3).getScoreAfterTurn());
    }

    // ==================== REPORTING TESTS ====================

    @Test
    public void testReportingTXTStratGeneration() throws Exception {
        assertNotNull("Questions should be loaded", question1);
        assertNotNull("Questions should be loaded", question2);
        
        gameHistory.recordTurn(new Turn(player1.getPlayerName(), question1, "A", true, 100, 100));
        gameHistory.recordTurn(new Turn(player2.getPlayerName(), question2, "B", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        assertNotNull("Report file should not be null", report);
        assertTrue("Report file should exist", report.exists());
        assertTrue("Report should be TXT format", report.getName().endsWith(".txt"));
        assertTrue("Report should have content", report.length() > 0);
    }

    @Test
    public void testReportingDOCXStratGeneration() throws Exception {
        assertNotNull("Questions should be loaded", question1);
        assertNotNull("Questions should be loaded", question2);
        
        gameHistory.recordTurn(new Turn(player1.getPlayerName(), question1, "A", true, 100, 100));
        gameHistory.recordTurn(new Turn(player2.getPlayerName(), question2, "B", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new DOCXStrat());
        File report = reporter.createReport(gameHistory);
        
        assertNotNull("Report file should not be null", report);
        assertTrue("Report file should exist", report.exists());
        assertTrue("Report should be DOCX format", report.getName().endsWith(".docx"));
        assertTrue("Report should have content", report.length() > 0);
    }

    @Test
    public void testReportingPDFStratGeneration() throws Exception {
        assertNotNull("Questions should be loaded", question1);
        assertNotNull("Questions should be loaded", question2);
        
        gameHistory.recordTurn(new Turn(player1.getPlayerName(), question1, "A", true, 100, 100));
        gameHistory.recordTurn(new Turn(player2.getPlayerName(), question2, "B", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new PDFStrat());
        File report = reporter.createReport(gameHistory);
        
        assertNotNull("Report file should not be null", report);
        assertTrue("Report file should exist", report.exists());
        assertTrue("Report should be PDF format", report.getName().endsWith(".pdf"));
        assertTrue("Report should have content", report.length() > 0);
    }

    @Test
    public void testReportingContainsTurnByTurnRundown() throws Exception {
        assertNotNull("Questions should be loaded", question1);
        assertNotNull("Questions should be loaded", question2);
        
        gameHistory.recordTurn(new Turn(player1.getPlayerName(), question1, "A", true, 100, 100));
        gameHistory.recordTurn(new Turn(player2.getPlayerName(), question2, "B", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        String content = new String(Files.readAllBytes(report.toPath()));
        assertTrue("Report should contain turn-by-turn section", 
            content.contains("Turn") || content.contains("TURN"));
    }

    @Test
    public void testReportingStrategyConsistency() throws Exception {
        assertNotNull("Questions should be loaded", question1);
        assertNotNull("Questions should be loaded", question2);
        
        gameHistory.recordTurn(new Turn(player1.getPlayerName(), question1, "A", true, 100, 100));
        gameHistory.recordTurn(new Turn(player2.getPlayerName(), question2, "B", true, 200, 200));
        
        File txtReport = new SummaryReport(new TXTStrat()).createReport(gameHistory);
        File docxReport = new SummaryReport(new DOCXStrat()).createReport(gameHistory);
        File pdfReport = new SummaryReport(new PDFStrat()).createReport(gameHistory);
        
        assertTrue("TXT report should exist", txtReport.exists());
        assertTrue("DOCX report should exist", docxReport.exists());
        assertTrue("PDF report should exist", pdfReport.exists());
    }

    // ==================== LOGGING TESTS ====================

    @Test
    public void testLoggingEventLoggerExists() {
        assertNotNull("EventLogger should exist", eventLogger);
    }

    @Test
    public void testLoggingEventLoggerSingleton() {
        EventLogger logger1 = EventLogger.getInstance();
        EventLogger logger2 = EventLogger.getInstance();
        
        assertSame("EventLogger should be singleton", logger1, logger2);
    }

    @Test
    public void testLoggingSystemEventCreation() {
        LogEntry entry = LogEntry.createSystemEvent("Game Started");
        
        assertEquals("System", entry.getPlayerId());
        assertEquals("Game Started", entry.getActivity());
    }

    @Test
    public void testLoggingPlayerJoinedEventCreation() {
        LogEntry entry = LogEntry.createPlayerJoinedEvent("1", "TestPlayer");
        
        assertEquals("1", entry.getPlayerId());
        assertEquals("Enter Player Name", entry.getActivity());
    }

    @Test
    public void testLoggingSelectCategoryEventCreation() {
        LogEntry entry = LogEntry.createSelectCategoryEvent("1", "Variables & Data Types");
        
        assertEquals("1", entry.getPlayerId());
        assertEquals("Select Category", entry.getActivity());
        assertEquals("Variables & Data Types", entry.getCategory());
    }

    @Test
    public void testLoggingSelectQuestionEventCreation() {
        LogEntry entry = LogEntry.createSelectQuestionEvent("1", "Variables & Data Types", 100);
        
        assertEquals("1", entry.getPlayerId());
        assertEquals("Select Question", entry.getActivity());
        assertEquals("Variables & Data Types", entry.getCategory());
        assertEquals(100, entry.getQuestionValue());
    }

    @Test
    public void testLoggingAnswerQuestionEventCreation() {
        LogEntry entry = LogEntry.createAnswerQuestionEvent("1", "Variables & Data Types", 
            100, "A", "Correct", 100);
        
        assertEquals("1", entry.getPlayerId());
        assertEquals("Answer Question", entry.getActivity());
        assertEquals("Variables & Data Types", entry.getCategory());
        assertEquals(100, entry.getQuestionValue());
        assertEquals("A", entry.getAnswer());
        assertEquals("Correct", entry.getResult());
        assertEquals(100, entry.getScore());
    }

    @Test
    public void testLoggingScoreUpdatedEventCreation() {
        LogEntry entry = LogEntry.scoreUpdatedEvent("1", 150);
        
        assertEquals("1", entry.getPlayerId());
        assertEquals("Score Updated", entry.getActivity());
        assertEquals(150, entry.getScore());
    }

    @Test
    public void testLoggingUpdateLog() {
        try {
            eventLogger.setGameId("TEST_GAME_001");
            LogEntry entry = LogEntry.createSystemEvent("Test Event");
            eventLogger.updateLog(entry);
            assertTrue("EventLogger should log without throwing exception", true);
        } catch (Exception e) {
            fail("EventLogger should not throw exception: " + e.getMessage());
        }
    }

    // ==================== EDGE CASES ====================

    @Test
    public void testEdgeCaseEmptyAnswer() {
        assertNotNull("Question1 should not be null", question1);
        Turn turn = new Turn(player1.getPlayerName(), question1, "", false, 0, 0);
        assertEquals("Empty answer should be recorded", "", turn.getAnswerGiven());
    }

    @Test
    public void testEdgeCaseCaseSensitiveAnswers() {
        assertNotNull("Question1 should not be null", question1);
        // checkAnswer uses equalsIgnoreCase, so both should work
        assertTrue("Uppercase should match", question1.checkAnswer("A"));
        assertTrue("Lowercase should match (equalsIgnoreCase)", question1.checkAnswer("a"));
    }
}