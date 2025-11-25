package com.group13;

import com.group13.GamePlay.GameHistory;
import com.group13.GamePlay.Turn;
import com.group13.ReportStrat.*;
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
    private EventLogger eventLogger;

    @Before
    public void setUp() {
        gameHistory = new GameHistory("TEST_CASE_001");
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        question1 = new Question("Science", 100, "What is H2O?", "Water");
        question2 = new Question("History", 200, "What year did WWII end?", "1945");
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
        assertEquals("What is H2O?", question1.getQuestion());
    }

    @Test
    public void testQuestionParseAnswer() {
        assertEquals("Water", question1.getAnswer());
    }

    @Test
    public void testTurnParseAllData() {
        Turn turn = new Turn("Player1", "Science", 100, "What is H2O?", "Water", true, 100, 100);
        
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
        String multiLineQuestion = "Question line 1\nQuestion line 2\nQuestion line 3";
        Turn turn = new Turn("Player1", "Science", 100, multiLineQuestion, "Answer", true, 100, 100);
        
        assertEquals(multiLineQuestion, turn.getQuestionText());
    }

    @Test
    public void testTurnParseSpecialCharacters() {
        Turn turn = new Turn("Player1", "Science", 100, "E=mc²?", "E=mc²", true, 100, 100);
        
        assertEquals("E=mc²", turn.getAnswerGiven());
    }

    // ==================== GAMEPLAY TESTS ====================

    @Test
    public void testGameplayRecordTurnsInOrder() {
        Turn turn1 = new Turn("Player1", "Science", 100, "Q1", "A1", true, 100, 100);
        Turn turn2 = new Turn("Player2", "History", 200, "Q2", "A2", true, 200, 200);
        Turn turn3 = new Turn("Player1", "Geography", 150, "Q3", "A3", false, 0, 100);
        
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
        
        gameHistory.recordTurn(new Turn("Player1", "Science", 100, "Q", "A", true, 100, 100));
        assertEquals(1, gameHistory.getTurnCount());
        
        gameHistory.recordTurn(new Turn("Player2", "History", 200, "Q", "A", true, 200, 200));
        assertEquals(2, gameHistory.getTurnCount());
    }

    @Test
    public void testGameplayFilterTurnsByPlayer() {
        gameHistory.recordTurn(new Turn("Player1", "Science", 100, "Q1", "A1", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", "History", 200, "Q2", "A2", true, 200, 200));
        gameHistory.recordTurn(new Turn("Player1", "Geography", 150, "Q3", "A3", false, 0, 100));
        gameHistory.recordTurn(new Turn("Player2", "Literature", 300, "Q4", "A4", true, 300, 500));
        
        List<Turn> player1Turns = gameHistory.getTurnsForPlayer("Player1");
        List<Turn> player2Turns = gameHistory.getTurnsForPlayer("Player2");
        
        assertEquals(2, player1Turns.size());
        assertEquals(2, player2Turns.size());
        
        for (Turn turn : player1Turns) {
            assertEquals("Player1", turn.getPlayerName());
        }
    }

    @Test
    public void testGameplayValidateCorrectAnswer() {
        assertTrue(question1.isCorrect("Water"));
    }

    @Test
    public void testGameplayValidateIncorrectAnswer() {
        assertFalse(question1.isCorrect("Ice"));
    }

    @Test
    public void testGameplayPlayerTurn() {
        assertEquals("Player1", player1.getName());
        assertTrue(player1.getScore() >= 0);
    }

    // ==================== SCORING TESTS ====================

    @Test
    public void testScoringCorrectAnswerFullPoints() {
        Turn turn = new Turn("Player1", "Science", 100, "Q", "A", true, 100, 100);
        
        assertEquals(100, turn.getPointsEarned());
    }

    @Test
    public void testScoringIncorrectAnswerZeroPoints() {
        Turn turn = new Turn("Player1", "Science", 100, "Q", "Wrong", false, 0, 0);
        
        assertEquals(0, turn.getPointsEarned());
    }

    @Test
    public void testScoringRunningTotalAccumulation() {
        Turn turn1 = new Turn("Player1", "Science", 100, "Q1", "A1", true, 100, 100);
        Turn turn2 = new Turn("Player1", "History", 200, "Q2", "A2", true, 200, 300);
        Turn turn3 = new Turn("Player1", "Geography", 150, "Q3", "A3", false, 0, 300);
        Turn turn4 = new Turn("Player1", "Literature", 300, "Q4", "A4", true, 300, 600);
        
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
    public void testScoringMultiplePlayersSeparateScores() {
        Turn p1Turn1 = new Turn("Player1", "Science", 100, "Q1", "A1", true, 100, 100);
        Turn p2Turn1 = new Turn("Player2", "History", 200, "Q2", "A2", true, 200, 200);
        Turn p1Turn2 = new Turn("Player1", "Geography", 150, "Q3", "A3", false, 0, 100);
        Turn p2Turn2 = new Turn("Player2", "Literature", 300, "Q4", "A4", true, 300, 500);
        
        gameHistory.recordTurn(p1Turn1);
        gameHistory.recordTurn(p2Turn1);
        gameHistory.recordTurn(p1Turn2);
        gameHistory.recordTurn(p2Turn2);
        
        List<Turn> player1Turns = gameHistory.getTurnsForPlayer("Player1");
        List<Turn> player2Turns = gameHistory.getTurnsForPlayer("Player2");
        
        assertEquals(100, player1Turns.get(player1Turns.size() - 1).getScoreAfterTurn());
        assertEquals(500, player2Turns.get(player2Turns.size() - 1).getScoreAfterTurn());
    }

    @Test
    public void testScoringIncorrectAnswerNoScoreChange() {
        Turn turn1 = new Turn("Player1", "Science", 100, "Q1", "A1", true, 100, 100);
        Turn turn2 = new Turn("Player1", "Geography", 150, "Q2", "Wrong", false, 0, 100);
        
        gameHistory.recordTurn(turn1);
        gameHistory.recordTurn(turn2);
        
        assertEquals(100, turn1.getScoreAfterTurn());
        assertEquals(100, turn2.getScoreAfterTurn());
    }

    @Test
    public void testScoringDifferentQuestionValues() {
        Turn turn1 = new Turn("Player1", "Science", 100, "Q1", "A1", true, 100, 100);
        Turn turn2 = new Turn("Player1", "History", 200, "Q2", "A2", true, 200, 300);
        Turn turn3 = new Turn("Player1", "Geography", 500, "Q3", "A3", true, 500, 800);
        
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
        gameHistory.recordTurn(new Turn("Player1", "Science", 100, "Q1", "A1", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", "History", 200, "Q2", "A2", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        assertTrue(report.exists());
        assertTrue(report.getName().endsWith(".txt"));
        assertTrue(report.length() > 0);
    }

    @Test
    public void testReportingDOCXStratGeneration() throws Exception {
        gameHistory.recordTurn(new Turn("Player1", "Science", 100, "Q1", "A1", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", "History", 200, "Q2", "A2", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new DOCXStrat());
        File report = reporter.createReport(gameHistory);
        
        assertTrue(report.exists());
        assertTrue(report.getName().endsWith(".docx"));
        assertTrue(report.length() > 0);
    }

    @Test
    public void testReportingPDFStratGeneration() throws Exception {
        gameHistory.recordTurn(new Turn("Player1", "Science", 100, "Q1", "A1", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", "History", 200, "Q2", "A2", true, 200, 200));
        
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
        gameHistory.recordTurn(new Turn("Player1", "Science", 100, "Q1", "A1", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", "History", 200, "Q2", "A2", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        String content = new String(Files.readAllBytes(report.toPath()));
        assertTrue(content.contains("FINAL SCORES") || content.contains("Final"));
    }

    @Test
    public void testReportingContainsTurnByTurnRundown() throws Exception {
        gameHistory.recordTurn(new Turn("Player1", "Science", 100, "What is H2O?", "Water", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", "History", 200, "What year did WWII end?", "1945", true, 200, 200));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        String content = new String(Files.readAllBytes(report.toPath()));
        assertTrue(content.contains("Turn") || content.contains("TURN"));
    }

    @Test
    public void testReportingIncludesAllTurnFields() throws Exception {
        gameHistory.recordTurn(new Turn("Player1", "Science", 100, "What is H2O?", "Water", true, 100, 100));
        
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
        gameHistory.recordTurn(new Turn("Player1", "Science", 100, "Q1", "A1", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", "History", 200, "Q2", "Wrong", false, 0, 0));
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(gameHistory);
        
        String content = new String(Files.readAllBytes(report.toPath()));
        assertTrue(content.toLowerCase().contains("correct") || content.contains("✓") || content.contains("✗"));
    }

    @Test
    public void testReportingStrategyConsistency() throws Exception {
        gameHistory.recordTurn(new Turn("Player1", "Science", 100, "Q1", "A1", true, 100, 100));
        gameHistory.recordTurn(new Turn("Player2", "History", 200, "Q2", "A2", true, 200, 200));
        
        File txtReport = new SummaryReport(new TXTStrat()).createReport(gameHistory);
        File docxReport = new SummaryReport(new DOCXStrat()).createReport(gameHistory);
        File pdfReport = new SummaryReport(new PDFStrat()).createReport(gameHistory);
        
        assertTrue(txtReport.exists());
        assertTrue(docxReport.exists());
        assertTrue(pdfReport.exists());
    }

    @Test
    public void testReportingEmptyGameHistory() throws Exception {
        GameHistory emptyHistory = new GameHistory("EMPTY_CASE");
        
        SummaryReport reporter = new SummaryReport(new TXTStrat());
        File report = reporter.createReport(emptyHistory);
        
        assertTrue(report.exists());
    }

    // ==================== LOGGING TESTS ====================

    @Test
    public void testLoggingEventLoggerRecordsEvent() {
        try {
            eventLogger.log("Player1 answered question");
        } catch (Exception e) {
            fail("EventLogger should log without throwing exception: " + e.getMessage());
        }
    }

    @Test
    public void testLoggingEventLoggerRetrievesLogs() {
        eventLogger.log("Event 1");
        eventLogger.log("Event 2");
        eventLogger.log("Event 3");
        
        List<?> logs = eventLogger.getLogs();
        assertNotNull(logs);
        assertTrue(logs.size() >= 3);
    }

    @Test
    public void testLoggingLogEntryStoresData() {
        LogEntry entry = new LogEntry("Player1", "Answered question", "Science");
        
        assertEquals("Player1", entry.getPlayer());
        assertEquals("Answered question", entry.getEvent());
    }

    @Test
    public void testLoggingEventLoggerClearsLogs() {
        eventLogger.log("Event 1");
        eventLogger.log("Event 2");
        
        try {
            eventLogger.clearLogs();
        } catch (Exception e) {
            fail("EventLogger should clear logs without throwing exception: " + e.getMessage());
        }
    }

    @Test
    public void testLoggingMultipleLogEntries() {
        eventLogger.log("Player1 started game");
        eventLogger.log("Player1 answered Science question");
        eventLogger.log("Player1 scored 100 points");
        eventLogger.log("Player2 answered History question");
        
        List<?> logs = eventLogger.getLogs();
        assertTrue(logs.size() >= 4);
    }

    @Test
    public void testLoggingDoesNotAffectGameplay() {
        eventLogger.log("Turn started");
        
        Turn turn = new Turn("Player1", "Science", 100, "Q", "A", true, 100, 100);
        gameHistory.recordTurn(turn);
        
        eventLogger.log("Turn completed");
        
        assertEquals(1, gameHistory.getTurnCount());
    }

    // ==================== EDGE CASES ====================

    @Test
    public void testEdgeCaseEmptyAnswer() {
        Turn turn = new Turn("Player1", "Science", 100, "Q", "", false, 0, 0);
        assertEquals("", turn.getAnswerGiven());
    }

    @Test
    public void testEdgeCaseZeroPointQuestion() {
        Turn turn = new Turn("Player1", "Science", 0, "Q", "A", true, 0, 0);
        assertEquals(0, turn.getQuestionValue());
    }

    @Test
    public void testEdgeCaseLargePointValues() {
        Turn turn = new Turn("Player1", "Science", 9999, "Q", "A", true, 9999, 9999);
        assertEquals(9999, turn.getPointsEarned());
        assertEquals(9999, turn.getScoreAfterTurn());
    }

    @Test
    public void testEdgeCaseCaseSensitiveAnswers() {
        Question q = new Question("Science", 100, "What is H2O?", "Water");
        
        assertTrue(q.isCorrect("Water"));
        assertFalse(q.isCorrect("water"));
        assertFalse(q.isCorrect("WATER"));
    }

    @Test
    public void testEdgeCaseSpecialCharactersInLogging() {
        try {
            eventLogger.log("Player@123 answered: What is E=mc²?");
        } catch (Exception e) {
            fail("Should handle special characters in logs: " + e.getMessage());
        }
    }
}
