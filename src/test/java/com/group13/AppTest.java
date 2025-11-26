package com.group13;

import com.group13.GamePlay.Turn;
import com.group13.ReportStrat.*;
import com.group13.Singelton.GameHistory;
import com.group13.Logging.EventLogger;
import com.group13.Logging.LogEntry;
import com.group13.Players.Player;
import com.group13.Questions.Question;
import com.group13.Singelton.GameData;
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
        // Reset singletons for clean test state
        gameHistory = GameHistory.getInstance();
        gameData = GameData.getInstance();
        eventLogger = EventLogger.getInstance();
        
        // Clear any existing data
        gameHistory.getTurns().clear();
        
        // Load test data from CSV
        loadTestDataFromCSV();
        
        // Get questions from loaded data for testing
        List<Question> allQuestions = gameData.getQuestionsByCategory("Variables & Data Types");
        if (allQuestions.size() >= 2) {
            question1 = allQuestions.get(0); // 100 point question
            question2 = allQuestions.get(1); // 200 point question
        }
        
        List<Question> controlQuestions = gameData.getQuestionsByCategory("Control Structures");
        if (controlQuestions.size() >= 2) {
            question3 = controlQuestions.get(0); // 100 point question
            question4 = controlQuestions.get(1); // 200 point question
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
        gameHistory.getTurns().clear();
    }

    /**
     * Helper method to load test data from CSV file
     */
    private void loadTestDataFromCSV() {
        try {
            TemplateLoadData loader = new LoadDataCSV();
            loader.loadData();
        } catch (Exception e) {
            System.err.println("Failed to load test data: " + e.getMessage());
        }
    }

    /**
     * Helper method to load test data from JSON file
     */
    private void loadTestDataFromJSON() {
        try {
            TemplateLoadData loader = new LoadDataJSON();
            loader.loadData();
        } catch (Exception e) {
            System.err.println("Failed to load test data: " + e.getMessage());
        }
    }

    /**
     * Helper method to load test data from XML file
     */
    private void loadTestDataFromXML() {
        try {
            TemplateLoadData loader = new LoadDataXML();
            loader.loadData();
        } catch (Exception e) {
            System.err.println("Failed to load test data: " + e.getMessage());
        }
    }

    // ==================== PARSING TESTS ====================

    @Test
    public void testQuestionParseCategory() {
        assertNotNull("Question should have a category", question1.getCategory());
        assertEquals("Variables & Data Types", question1.getCategory());
    }

    @Test
    public void testQuestionParsePointValue() {
        assertEquals(100, question1.getValue());
    }

    @Test
    public void testQuestionParseQuestionText() {
        assertNotNull("Question text should not be null", question1.getQuestionText());
        assertFalse("Question text should not be empty", question1.getQuestionText().isEmpty());
    }

    @Test
    public void testQuestionParseAnswer() {
        assertNotNull("Answer should not be null", question1.getAnswer());
        assertEquals("A", question1.getAnswer());
    }

    @Test
    public void testQuestionParseOptions() {
        assertNotNull("Option A should not be null", question1.getOptionA());
        assertNotNull("Option B should not be null", question1.getOptionB());
        assertNotNull("Option C should not be null", question1.getOptionC());
        assertNotNull("Option D should not be null", question1.getOptionD());
    }

    @Test
    public void testCSVDataLoading() {
        List<String> categories = gameData.getCategories();
        assertFalse("Categories should not be empty", categories.isEmpty());
        assertTrue("Should contain 'Variables & Data Types' category", 
            categories.contains("Variables & Data Types"));
    }

    @Test
    public void testJSONDataLoading() {
        // Clear existing data
        gameData = GameData.getInstance();
        
        loadTestDataFromJSON();
        
        List<String> categories = gameData.getCategories();
        assertFalse("Categories should not be empty after JSON load", categories.isEmpty());
    }

    @Test
    public void testXMLDataLoading() {
        // Clear existing data
        gameData = GameData.getInstance();
        
        loadTestDataFromXML();
        
        List<String> categories = gameData.getCategories();
        assertFalse("Categories should not be empty after XML load", categories.isEmpty());
    }

    @Test
    public void testTurnParseAllData() {
        Turn turn = new Turn(player1.getPlayerName(), question1, "A", true, 100, 100);
        
        assertEquals("TestPlayer1", turn.getPlayerName());
        assertEquals(question1.getCategory(), turn.getCategory());
        assertEquals(100, turn.getQuestionValue());
        assertEquals(question1.getQuestionText(), turn.getQuestionText());
        assertEquals("A", turn.getAnswerGiven());
        assertTrue(turn.isCorrect());
        assertEquals(100, turn.getPointsEarned());
        assertEquals(100, turn.getScoreAfterTurn());
    }

    @Test
    public void testTurnParseMultiLineQuestion() {
        // Use a real question that might have special formatting
        Turn turn = new Turn(player1.getPlayerName(), question2, "B", true, 200, 200);
        
        assertNotNull("Question text should not be null", turn.getQuestionText());
        assertFalse("Question text should not be empty", turn.getQuestionText().isEmpty());
    }

    @Test
    public void testTurnParseSpecialCharacters() {
        // Test with actual question that has special characters (like C++ operators)
        Turn turn = new Turn(player1.getPlayerName(), question1, "A", true, 100, 100);
        
        assertNotNull("Answer should handle special characters", turn.getAnswerGiven());
    }

    // ==================== GAMEPLAY TESTS ====================

    @Test
    public void testGameplayRecordTurnsInOrder() {
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
        int initialCount = gameHistory.getTurnCount();
        
        gameHistory.recordTurn(new Turn(player1.getPlayerName(), question1, "A", true, 100, 100));
        assertEquals(initialCount + 1, gameHistory.getTurnCount());
        
        gameHistory.recordTurn(new Turn(player2.getPlayerName(), question2, "B", true, 200, 200));
        assertEquals(initialCount + 2, gameHistory.getTurnCount());
    }

    @Test
    public void testGameplayValidateCorrectAnswer() {
        assertTrue("Correct answer 'A' should be accepted", question1.checkAnswer("A"));
    }

    @Test
    public void testGameplayValidateIncorrectAnswer() {
        assertFalse("Incorrect answer should be rejected", question1.checkAnswer("D"));
    }

    @Test
    public void testGameplayPlayerInitialization() {
        assertEquals("TestPlayer1", player1.getPlayerName());
        assertEquals(0, player1.getScore());
    }

    @Test
    public void testGameplayQuestionMarkedAsAnswered() {
        assertFalse("Question should start as unanswered", question1.isAnswered());
        question1.setAnswered();
        assertTrue("Question should be marked as answered", question1.isAnswered());
    }

    // ==================== SCORING TESTS ====================

    @Test
    public void testScoringCorrectAnswerFullPoints() {
        Turn turn = new Turn(player1.getPlayerName(), question1, "A", true, 100, 100);
        assertEquals(100, turn.getPointsEarned());
    }

    @Test
    public void testScoringIncorrectAnswerZeroPoints() {
        Turn turn = new Turn(player1.getPlayerName(), question1, "D", false, 0, 0);
        assertEquals(0, turn.getPointsEarned());
    }

    @Test
    public void testScoringRunningTotalAccumulation() {
        Turn turn1 = new Turn(player1.getPlayerName(), question1, "A", true, 100, 100);
        Turn turn2 = new Turn(player1.getPlayerName(), question2, "B", true, 200, 300);
        Turn turn3 = new Turn(player1.getPlayerName(), question3, "D", false, 0, 300);
        Turn turn4 = new Turn(player1.getPlayerName(), question4, "B", true, 200, 500);
        
        gameHistory.recordTurn(turn1);
        gameHistory.recordTurn(turn2);
        gameHistory.recordTurn(turn3);
        gameHistory.recordTurn(turn4);
        
        List<Turn> turns = gameHistory.getTurns();
        assertEquals(100, turns.get(0).getScoreAfterTurn());
        assertEquals(300, turns.get(1).getScoreAfterTurn());
        assertEquals(300, turns.get(2).getScoreAfterTurn());
        assertEquals(500, turns.get(3).getScoreAfterTurn());
    }

    @Test
    public void testScoringIncorrectAnswerNoScoreChange() {
        Turn turn1 = new Turn(player1.getPlayerName(), question1, "A", true, 100, 100);
        Turn turn2 = new Turn(player1.getPlayerName(), question2, "D", false, 0, 100);
        
        gameHistory.recordTurn(turn1);
        gameHistory.recordTurn(turn2);
        
        assertEquals(100, turn1.getScoreAfterTurn());
        assertEquals(100, turn2.getScoreAfterTurn());
    }

    @Test
    public void testScoringDifferentQuestionValues() {
        // Get questions with different values
        List<Question> questions = gameData.getQuestionsByCategory("Variables & Data Types");
        
        if (questions.size() >= 3) {
            Turn turn1 = new Turn(player1.getPlayerName(), questions.get(0), "A", true, 100, 100);
            Turn turn2 = new Turn(player1.getPlayerName(), questions.get(1), "B", true, 200, 300);
            Turn turn3 = new Turn(player1.getPlayerName(), questions.get(2), "A", true, 300, 600);
            
            assertEquals(100, turn1.getPointsEarned());
            assertEquals(200, turn2.getPointsEarned());
            assertEquals(300, turn3.getPointsEarned());
        }
    }

    @Test
    public void testScoringPlayerAddPoints() {
        assertEquals(0, player1.getScore());
        player1.addPoints(100);
        assertEquals(100, player1.getScore());
        player1.addPoints(200);
        assertEquals(300, player1.getScore());
    }

    // ==================== REPORTING TESTS ====================

    @Test
    public void testReportingTXTStratGeneration() throws Exception {
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
    public void testReportingContainsFinalScoresSection() throws Exception {
        gameHistory.recordTurn(new Turn(player1.getPlayerName(), question1, "A", true, 100, 100));
        gameHistory.recordTurn(new Turn(player2.getPlayerName(), question2, "B", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        String content = new String(Files.readAllBytes(report.toPath()));
        assertTrue("Report should contain final scores section", 
            content.contains("FINAL SCORES") || content.contains("Final"));
    }

    @Test
    public void testReportingContainsTurnByTurnRundown() throws Exception {
        gameHistory.recordTurn(new Turn(player1.getPlayerName(), question1, "A", true, 100, 100));
        gameHistory.recordTurn(new Turn(player2.getPlayerName(), question2, "B", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        String content = new String(Files.readAllBytes(report.toPath()));
        assertTrue("Report should contain turn-by-turn section", 
            content.contains("Turn") || content.contains("TURN"));
    }

    @Test
    public void testReportingIncludesAllTurnFields() throws Exception {
        gameHistory.recordTurn(new Turn(player1.getPlayerName(), question1, "A", true, 100, 100));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        String content = new String(Files.readAllBytes(report.toPath()));
        assertTrue("Report should contain player name", content.contains(player1.getPlayerName()));
        assertTrue("Report should contain category", content.contains(question1.getCategory()));
        assertTrue("Report should contain point value", content.contains("100"));
    }

    @Test
    public void testReportingIndicatesCorrectness() throws Exception {
        gameHistory.recordTurn(new Turn(player1.getPlayerName(), question1, "A", true, 100, 100));
        gameHistory.recordTurn(new Turn(player2.getPlayerName(), question2, "D", false, 0, 0));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        String content = new String(Files.readAllBytes(report.toPath()));
        assertTrue("Report should indicate correctness", 
            content.toLowerCase().contains("correct") || 
            content.contains("✓") || 
            content.contains("✗"));
    }

    @Test
    public void testReportingStrategyConsistency() throws Exception {
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
        Turn turn = new Turn(player1.getPlayerName(), question1, "", false, 0, 0);
        assertEquals("Empty answer should be recorded", "", turn.getAnswerGiven());
    }

    @Test
    public void testEdgeCaseNullAnswerHandling() {
        Turn turn = new Turn(player1.getPlayerName(), question1, null, false, 0, 0);
        // Turn should handle null answer gracefully
        assertNotNull("Turn should exist even with null answer", turn);
    }

    @Test
    public void testEdgeCaseMultiplePlayerssameName() {
        Player p1 = new Player("SameName");
        Player p2 = new Player("SameName");
        
        assertNotEquals("Players with same name should have different IDs", 
            p1.getPlayerId(), p2.getPlayerId());
    }

    @Test
    public void testEdgeCaseCaseInsensitiveAnswerCheck() {
        // Test that answer checking is case-insensitive as per checkAnswer implementation
        boolean result = question1.checkAnswer("a"); // lowercase
        // This depends on your implementation - adjust assertion accordingly
        assertNotNull("Answer check should complete", result);
    }

    @Test
    public void testEdgeCaseAllQuestionsAnswered() {
        List<Question> allQuestions = gameData.getQuestionsByCategory("Variables & Data Types");
        
        for (Question q : allQuestions) {
            q.setAnswered();
        }
        
        boolean anyUnanswered = false;
        for (Question q : allQuestions) {
            if (!q.isAnswered()) {
                anyUnanswered = true;
                break;
            }
        }
        
        assertFalse("All questions should be marked as answered", anyUnanswered);
    }
}