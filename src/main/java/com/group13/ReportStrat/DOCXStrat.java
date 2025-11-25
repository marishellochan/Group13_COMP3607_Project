package com.group13.ReportStrat;

import com.group13.GamePlay.Turn;
import org.apache.poi.xwpf.usermodel.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete Strategy for DOCX report generation using Apache POI.
 */
public class DOCXStrat implements ReportStrat {

    @Override
    public File generateReport(List<Turn> turns, String caseId) throws Exception {
        File outFile = new File(caseId + "_report.docx");

        XWPFDocument doc = new XWPFDocument();

        // Title
        XWPFParagraph titlePara = doc.createParagraph();
        titlePara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titlePara.createRun();
        titleRun.setText("GAME REPORT: " + caseId);
        titleRun.setBold(true);
        titleRun.setFontSize(16);

        // Spacing
        doc.createParagraph();

        // Final Scores Section
        XWPFParagraph scoreHeaderPara = doc.createParagraph();
        XWPFRun scoreHeaderRun = scoreHeaderPara.createRun();
        scoreHeaderRun.setText("FINAL SCORES");
        scoreHeaderRun.setBold(true);
        scoreHeaderRun.setFontSize(14);

        // Calculate final scores
        Map<String, Integer> finalScores = new HashMap<>();
        for (Turn turn : turns) {
            finalScores.put(turn.getPlayerName(), turn.getScoreAfterTurn());
        }

        // Write scores sorted descending
        finalScores.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .forEach(entry -> {
                    XWPFParagraph p = doc.createParagraph();
                    p.setIndentationLeft(240); // indent
                    XWPFRun run = p.createRun();
                    run.setText(String.format("%-30s %d", entry.getKey(), entry.getValue()));
                });

        // Spacing
        doc.createParagraph();

        // Turn-by-turn section header
        XWPFParagraph turnsHeaderPara = doc.createParagraph();
        XWPFRun turnsHeaderRun = turnsHeaderPara.createRun();
        turnsHeaderRun.setText("TURN-BY-TURN RUNDOWN");
        turnsHeaderRun.setBold(true);
        turnsHeaderRun.setFontSize(14);

        // Add each turn
        for (int i = 0; i < turns.size(); i++) {
            Turn turn = turns.get(i);
            String correctness = turn.isCorrect() ? "✓ CORRECT" : "✗ INCORRECT";

            // Turn number
            XWPFParagraph turnNumPara = doc.createParagraph();
            XWPFRun turnNumRun = turnNumPara.createRun();
            turnNumRun.setText("Turn " + (i + 1));
            turnNumRun.setBold(true);

            // Turn details
            addDetailLine(doc, "Player:", turn.getPlayerName());
            addDetailLine(doc, "Category:", turn.getCategory() + " (" + turn.getQuestionValue() + " points)");
            addDetailLine(doc, "Question:", sanitize(turn.getQuestionText()));
            addDetailLine(doc, "Your Answer:", sanitize(turn.getAnswerGiven()));
            addDetailLine(doc, "Result:", correctness);
            addDetailLine(doc, "Points Earned:", String.valueOf(turn.getPointsEarned()));
            addDetailLine(doc, "Running Total:", String.valueOf(turn.getScoreAfterTurn()));

            // Spacing between turns
            doc.createParagraph();
        }

        // Footer
        XWPFParagraph footerPara = doc.createParagraph();
        XWPFRun footerRun = footerPara.createRun();
        footerRun.setText("Total Turns: " + turns.size());
        footerRun.setBold(true);

        // Write to file
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            doc.write(fos);
        }
        doc.close();

        return outFile;
    }

    /**
     * Helper method to add a detail line in DOCX format.
     */
    private void addDetailLine(XWPFDocument doc, String label, String value) {
        XWPFParagraph para = doc.createParagraph();
        para.setIndentationLeft(240);

        XWPFRun labelRun = para.createRun();
        labelRun.setText(label);
        labelRun.setBold(true);

        XWPFRun valueRun = para.createRun();
        valueRun.setText(" " + value);
    }

    /**
     * Helper to clean up multi-line strings.
     */
    private static String sanitize(String text) {
        return text == null || text.isEmpty()
                ? "(no response)"
                : text.replaceAll("\\r?\\n", " ");
    }


}
