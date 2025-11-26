package com.group13;

import com.group13.GamePlay.Turn;
import com.group13.ReportStrat.*;
import com.group13.Singelton.GameHistory;
import com.group13.Logging.EventLogger;
import com.group13.Logging.LogEntry;
import com.group13.Players.Player;
import com.group13.Questions.Question;
import com.group13.ExceptionHandling.NoDataException;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

/**
 * Test suite for Group13 Trivia Game.
 * Focus: Parsing, Gameplay, Scoring, Reporting, Logging
 */
public class AppTest {

    private GameHistory gameHistory;
    private Player player1;
    private Player player2;
    private Question question1;
    private Question question2;
    private Question question3;
    private Question question4;
    private Question edgeQuestion;
    private Question edgeQuestion2;
    private Question edgeQuestion3;
    private Question edgeQuestion4;
    private Question edgeQuestion5;
    private EventLogger eventLogger;

    @Before
    public void setUp() {
        gameHistory = GameHistory.getInstance();

        player1 = new Player("Player1");
        player2 = new Player("Player2");

        question1 = new Question();
        question1.setCategory("Science");
        question1.setValue(100);
        question1.setQuestionText("What is H2O?");
        question1.setAnswer("Water");
        question1.setOptions("Water", "Ice", "Steam", "Oxygen");

        question2 = new Question();
        question2.setCategory("History");
        question2.setValue(200);
        question2.setQuestionText("What year did WW2 end?");
        question2.setAnswer("1945");
        question2.setOptions("1939", "1942", "1945", "1950");

        question3 = new Question();
        question3.setCategory("Geography");
        question3.setValue(100);
        question3.setQuestionText("What is the capital of France?");
        question3.setAnswer("Paris");
        question3.setOptions("Berlin", "Madrid", "Rome", "Paris");

        question4 = new Question();
        question4.setCategory("Literature");
        question4.setValue(300);
        question4.setQuestionText("Who wrote '1984'?");
        question4.setAnswer("George Orwell");
        question4.setOptions("Aldous Huxley", "George Orwell", "Mark Twain", "J.K. Rowling");

        edgeQuestion = new Question();
        edgeQuestion.setCategory("Physics");
        edgeQuestion.setValue(100);
        edgeQuestion.setQuestionText("E=mc²?");
        edgeQuestion.setAnswer("E=mc²");
        edgeQuestion.setOptions("E=mc", "E=mc²", "E=2mc", "E=mc³");

        eventLogger = EventLogger.getInstance();
    }

    @After
    public void tearDown() {
        // Clean up generated report files
        File[] files = new File(".").listFiles((dir, name) -> 
            name.endsWith("_report.txt") || name.endsWith("_report.docx") || name.endsWith("_report.pdf")
        );
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    // ==================== PARSING TESTS ====================

    @Test
    public void testQuestionParseCategory() {
        assertEquals("Science", question1.getCategory());
    }

    @Test
    public void testQuestionParsePointValue() {
        assertEquals(100, question1.getValue());
    }

    @Test
    public void testQuestionParseQuestionText() {
        assertEquals("What is H2O?", question1.getQuestionText());
    }

    @Test
    public void testQuestionParseAnswer() {
        assertEquals("Water", question1.getAnswer());
    }

    @Test
    public void testTurnParseAllData() {
        Turn turn = new Turn("Player1", question1, "Water", true, 100, 100);
        
        assertEquals("Player1", turn.getPlayerName());
        assertEquals("Science", turn.getCategory());
        assertEquals(100, turn.getQuestionValue());
        assertEquals("What is H2O?", turn.getQuestionText());
        assertEquals("Water", turn.getAnswerGiven());
        assertTrue(turn.isCorrect());
        assertEquals(100, turn.getPointsEarned());
        assertEquals(100, turn.getScoreAfterTurn());
    }

    @Test
    public void testTurnParseMultiLineQuestion() {
        Question multiLineQ = new Question();
        String multiLineQuestion = "Question line 1\nQuestion line 2\nQuestion line 3";
        multiLineQ.setQuestionText(multiLineQuestion);
        multiLineQ.setCategory("Test");
        multiLineQ.setValue(100);
        multiLineQ.setOptions("A", "B", "C", "D");
        multiLineQ.setAnswer("A");
        
        Turn turn = new Turn("Player1", multiLineQ, "A", true, 100, 100);
        
        assertEquals(multiLineQuestion, turn.getQuestionText());
    }

    @Test
    public void testTurnParseSpecialCharacters() {
        Turn turn = new Turn("Player1", edgeQuestion, "A", true, 100, 100);
        
        assertEquals("E=mc²", turn.getAnswerGiven());
    }

    // ==================== GAMEPLAY TESTS ====================

    @Test
    public void testGameplayRecordTurnsInOrder() {
        Turn turn1 = new Turn("Player1", question1, "A1", true, 100, 100);
        Turn turn2 = new Turn("Player2", question2, "A2", true, 200, 200);
        Turn turn3 = new Turn("Player1", question3, "A3", false, 0, 100);
        
        gameHistory.recordTurn(turn1);
        gameHistory.recordTurn(turn2);
        gameHistory.recordTurn(turn3);
        
        List<Turn> turns = gameHistory.getTurns();
        assertEquals(3, turns.size());
        assertEquals("Player1", turns.get(0).getPlayerName());
        assertEquals("Player2", turns.get(1).getPlayerName());
        assertEquals("Player1", turns.get(2).getPlayerName());
    }

    @Test
    public void testGameplayTurnCountTracking() {
        assertEquals(0, gameHistory.getTurnCount());
        
        gameHistory.recordTurn(new Turn("Player1", question1, "A", true, 100, 100));
        assertEquals(1, gameHistory.getTurnCount());
        
        gameHistory.recordTurn(new Turn("Player2", question2, "A", true, 200, 200));
        assertEquals(2, gameHistory.getTurnCount());
    }

    @Test
    public void testGameplayValidateCorrectAnswer() {
        assertTrue(question1.checkAnswer("Water"));
    }

    @Test
    public void testGameplayValidateIncorrectAnswer() {
        assertFalse(question1.checkAnswer("Ice"));
    }

    @Test
    public void testGameplayPlayerTurn() {
        assertEquals("Player1", player1.getPlayerName());
        assertTrue(player1.getScore() >= 0);
    }

    // ==================== SCORING TESTS ====================

    @Test
    public void testScoringCorrectAnswerFullPoints() {
        Turn turn = new Turn("Player1", question1, "A", true, 100, 100);
        
        assertEquals(100, turn.getPointsEarned());
    }

    @Test
    public void testScoringIncorrectAnswerZeroPoints() {
        Turn turn = new Turn("Player1", question1, "Wrong", false, 0, 0);
        
        assertEquals(0, turn.getPointsEarned());
    }

    @Test
    public void testScoringRunningTotalAccumulation() {
        Turn turn1 = new Turn("Player1", question1, "A1", true, 100, 100);
        Turn turn2 = new Turn("Player1", question2, "A2", true, 200, 300);
        Turn turn3 = new Turn("Player1", question3, "A3", false, 0, 300);
        Turn turn4 = new Turn("Player1", question4, "A4", true, 300, 600);
        
        gameHistory.recordTurn(turn1);
        gameHistory.recordTurn(turn2);
        gameHistory.recordTurn(turn3);
        gameHistory.recordTurn(turn4);
        
        List<Turn> turns = gameHistory.getTurns();
        assertEquals(100, turns.get(0).getScoreAfterTurn());
        assertEquals(300, turns.get(1).getScoreAfterTurn());
        assertEquals(300, turns.get(2).getScoreAfterTurn());
        assertEquals(600, turns.get(3).getScoreAfterTurn());
    }

    @Test
    public void testScoringIncorrectAnswerNoScoreChange() {
        Turn turn1 = new Turn("Player1", question1, "A1", true, 100, 100);
        Turn turn2 = new Turn("Player1", question2, "Wrong", false, 0, 100);
        
        gameHistory.recordTurn(turn1);
        gameHistory.recordTurn(turn2);
        
        assertEquals(100, turn1.getScoreAfterTurn());
        assertEquals(100, turn2.getScoreAfterTurn());
    }

    @Test
    public void testScoringDifferentQuestionValues() {
        Turn turn1 = new Turn("Player1", question1, "A1", true, 100, 100);
        Turn turn2 = new Turn("Player1", question2, "A2", true, 200, 300);
        Turn turn3 = new Turn("Player1", question3, "A3", true, 500, 800);
        
        gameHistory.recordTurn(turn1);
        gameHistory.recordTurn(turn2);
        gameHistory.recordTurn(turn3);
        
        assertEquals(100, turn1.getPointsEarned());
        assertEquals(200, turn2.getPointsEarned());
        assertEquals(500, turn3.getPointsEarned());
    }

    // ==================== REPORTING TESTS ====================

    @Test
    public void testReportingTXTStratGeneration() throws Exception {
        gameHistory.recordTurn(new Turn("Player1", question1, "A1", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", question2, "A2", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        assertTrue(report.exists());
        assertTrue(report.getName().endsWith(".txt"));
        assertTrue(report.length() > 0);
    }

    @Test
    public void testReportingDOCXStratGeneration() throws Exception {
        gameHistory.recordTurn(new Turn("Player1", question1, "A1", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", question2, "A2", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new DOCXStrat());
        File report = reporter.createReport(gameHistory);
        
        assertTrue(report.exists());
        assertTrue(report.getName().endsWith(".docx"));
        assertTrue(report.length() > 0);
    }

    @Test
    public void testReportingPDFStratGeneration() throws Exception {
        gameHistory.recordTurn(new Turn("Player1", question1, "A1", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", question2, "A2", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new PDFStrat());
        File report = reporter.createReport(gameHistory);
        
        assertTrue(report.exists());
        assertTrue(report.getName().endsWith(".pdf"));
        assertTrue(report.length() > 0);
    }

    @Test
    public void testReportingFilenameContainsCaseId() throws Exception {
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        assertTrue(report.getName().contains("TEST_CASE_001"));
    }

    @Test
    public void testReportingContainsFinalScoresSection() throws Exception {
        gameHistory.recordTurn(new Turn("Player1", question1, "A1", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", question2, "A2", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        String content = new String(Files.readAllBytes(report.toPath()));
        assertTrue(content.contains("FINAL SCORES") || content.contains("Final"));
    }

    @Test
    public void testReportingContainsTurnByTurnRundown() throws Exception {
        gameHistory.recordTurn(new Turn("Player1", question1, "Water", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", question2, "1945", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        String content = new String(Files.readAllBytes(report.toPath()));
        assertTrue(content.contains("Turn") || content.contains("TURN"));
    }

    @Test
    public void testReportingIncludesAllTurnFields() throws Exception {
        gameHistory.recordTurn(new Turn("Player1", question1, "Water", true, 100, 100));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        String content = new String(Files.readAllBytes(report.toPath()));
        assertTrue(content.contains("Player1"));
        assertTrue(content.contains("Science"));
        assertTrue(content.contains("100"));
        assertTrue(content.contains("Water"));
    }

    @Test
    public void testReportingIndicatesCorrectness() throws Exception {
        gameHistory.recordTurn(new Turn("Player1", question1, "A1", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", question2, "Wrong", false, 0, 0));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        String content = new String(Files.readAllBytes(report.toPath()));
        assertTrue(content.toLowerCase().contains("correct") || content.contains("✓") || content.contains("✗"));
    }

    @Test
    public void testReportingStrategyConsistency() throws Exception {
        gameHistory.recordTurn(new Turn("Player1", question1, "A1", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", question2, "A2", true, 200, 200));
        
        File txtReport = new SummaryReport(new TXTStrat()).createReport(gameHistory);
        File docxReport = new SummaryReport(new DOCXStrat()).createReport(gameHistory);
        File pdfReport = new SummaryReport(new PDFStrat()).createReport(gameHistory);
        
        assertTrue(txtReport.exists());
        assertTrue(docxReport.exists());
        assertTrue(pdfReport.exists());
    }

    // ==================== LOGGING TESTS ====================

    @Test
    public void testLoggingEventLoggerExists() {
        assertNotNull(eventLogger);
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
        LogEntry entry = LogEntry.createSelectCategoryEvent("1", "Science");
        
        assertEquals("1", entry.getPlayerId());
        assertEquals("Select Category", entry.getActivity());
        assertEquals("Science", entry.getCategory());
    }

    @Test
    public void testLoggingSelectQuestionEventCreation() {
        LogEntry entry = LogEntry.createSelectQuestionEvent("1", "Science", 100);
        
        assertEquals("1", entry.getPlayerId());
        assertEquals("Select Question", entry.getActivity());
        assertEquals("Science", entry.getCategory());
        assertEquals(100, entry.getQuestionValue());
    }

    @Test
    public void testLoggingAnswerQuestionEventCreation() {
        LogEntry entry = LogEntry.createAnswerQuestionEvent("1", "Science", 100, "A", "Correct", 100);
        
        assertEquals("1", entry.getPlayerId());
        assertEquals("Answer Question", entry.getActivity());
        assertEquals("Science", entry.getCategory());
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
            // If no exception is thrown, test passes
            assertTrue(true);
        } catch (Exception e) {
            fail("EventLogger should log without throwing exception: " + e.getMessage());
        }
    }

    @Test
    public void testLoggingEventLoggerSingleton() {
        EventLogger logger1 = EventLogger.getInstance();
        EventLogger logger2 = EventLogger.getInstance();
        
        assertSame(logger1, logger2);
    }

    // ==================== EDGE CASES ====================

    @Test
    public void testEdgeCaseEmptyAnswer() {
        edgeQuestion2 = new Question();
        edgeQuestion2.setCategory("Science");
        edgeQuestion2.setValue(100);
        edgeQuestion2.setQuestionText("Q");
        Turn turn = new Turn("Player1", edgeQuestion2, "", false, 0, 0);
        assertEquals("", turn.getAnswerGiven());
    }

    @Test
    public void testEdgeCaseZeroPointQuestion() {
        edgeQuestion3 = new Question();
        edgeQuestion3.setCategory("Science");   
        edgeQuestion3.setValue(0);
        edgeQuestion3.setQuestionText("Q");
        Turn turn = new Turn("Player1", edgeQuestion3, "A", true, 0, 0);
        assertEquals(0, turn.getQuestionValue());
    }

    @Test
    public void testEdgeCaseLargePointValues() {
        edgeQuestion4 = new Question();
        edgeQuestion4.setCategory("Science");
        edgeQuestion4.setValue(9999);
        edgeQuestion4.setQuestionText("Q");
        Turn turn = new Turn("Player1", edgeQuestion4, "A", true, 9999, 9999);
        assertEquals(9999, turn.getPointsEarned());
        assertEquals(9999, turn.getScoreAfterTurn());
    }

    @Test
    public void testEdgeCaseCaseSensitiveAnswers() {
        edgeQuestion5 = new Question();
        edgeQuestion5.setCategory("Science");
        edgeQuestion5.setValue(100);
        edgeQuestion5.setQuestionText("What is H2O?");
        edgeQuestion5.setAnswer("Water");
        
        assertTrue(edgeQuestion5.checkAnswer("Water"));
        assertFalse(edgeQuestion5.checkAnswer("water"));
        assertFalse(edgeQuestion5.checkAnswer("WATER"));
    }

}

    
