package com.group13.ReportStrat;

import com.group13.GamePlay.Turn;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// Concrete Strategy for plain-text report generation.
// Ready to use—no external dependencies required.
 
public class TXTStrat implements ReportStrat {

    @Override
    public File generateReport(List<Turn> turns, String caseId) throws Exception {
        File outFile = new File(caseId + "_report.txt");
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            // Header
            writer.write("=".repeat(100));
            writer.newLine();
            writer.write("GAME REPORT: " + caseId);
            writer.newLine();
            writer.write("=".repeat(100));
            writer.newLine();
            writer.newLine();

            // Compute final scores (last score for each player)
            Map<String, Integer> finalScores = new HashMap<>();
            for (Turn turn : turns) {
                finalScores.put(turn.getPlayerName(), turn.getScoreAfterTurn());
            }

            // Write final scores sorted descending
            writer.write("FINAL SCORES:");
            writer.newLine();
            writer.write("-".repeat(100));
            writer.newLine();
            finalScores.entrySet().stream()
                    .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                    .forEach(entry -> {
                        try {
                            writer.write(String.format("  %-30s %d", entry.getKey(), entry.getValue()));
                            writer.newLine();
                        } catch (Exception e) {
                            
                        }
                    });
            writer.newLine();
            writer.newLine();

            // Turn-by-turn rundown
            writer.write("TURN-BY-TURN RUNDOWN:");
            writer.newLine();
            writer.write("-".repeat(100));
            writer.newLine();

            for (int i = 0; i < turns.size(); i++) {
                Turn turn = turns.get(i);
                String correctness = turn.isCorrect() ? "✓ CORRECT" : "✗ INCORRECT";
                
                writer.write(String.format("Turn %d:%n", i + 1));
                writer.write(String.format("  Player:        %s%n", turn.getPlayerName()));
                writer.write(String.format("  Category:      %s (%d points)%n", 
                        turn.getCategory(), turn.getQuestionValue()));
                writer.write(String.format("  Question:      %s%n", sanitize(turn.getQuestionText())));
                writer.write(String.format("  Your Answer:   %s%n", sanitize(turn.getAnswerGiven())));
                writer.write(String.format("  Result:        %s%n", correctness));
                writer.write(String.format("  Points Earned: %d%n", turn.getPointsEarned()));
                writer.write(String.format("  Running Total: %d%n", turn.getScoreAfterTurn()));
                writer.newLine();
            }

            writer.write("-".repeat(100));
            writer.newLine();
            writer.write(String.format("Total Turns: %d%n", turns.size()));
            writer.write("=".repeat(100));
            writer.newLine();

        }

        return outFile;
    }

    
    //Helper to clean up multi-line strings for text output.
     
    private static String sanitize(String text) {
        return text == null || text.isEmpty() 
            ? "(no response)" 
            : text.replaceAll("\\r?\\n", " ");
    }
}
