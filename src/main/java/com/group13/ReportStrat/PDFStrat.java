package com.group13.ReportStrat;

import com.group13.GamePlay.Turn;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 // Concrete Strategy for PDF report generation using Apache PDFBox.
 // Simplified version without explicit font dependencies.

public class PDFStrat implements ReportStrat {

    private static final float MARGIN = 50;
    private static final float PAGE_WIDTH = PDRectangle.LETTER.getWidth();
    private static final float PAGE_HEIGHT = PDRectangle.LETTER.getHeight();
    private static final float LINE_HEIGHT = 15;

    @Override
    public File generateReport(List<Turn> turns, String caseId) throws Exception {
        File outFile = new File(caseId + "_report.pdf");

        PDDocument doc = new PDDocument();
        float yPosition = PAGE_HEIGHT - MARGIN;

        // Create first page
        PDPage page = new PDPage(PDRectangle.LETTER);
        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        // Title
        yPosition = writeTitle(contentStream, "GAME REPORT: " + caseId, yPosition);
        yPosition -= LINE_HEIGHT;

        // Final Scores Section
        yPosition = writeHeader(contentStream, "FINAL SCORES", yPosition);

        // Calculate final scores
        Map<String, Integer> finalScores = new HashMap<>();
        for (Turn turn : turns) {
            finalScores.put(turn.getPlayerName(), turn.getScoreAfterTurn());
        }

        // Write scores sorted descending
        for (Map.Entry<String, Integer> entry : finalScores.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .toList()) {
            String line = String.format("  %-35s %d", entry.getKey(), entry.getValue());
            yPosition = writeLine(contentStream, line, yPosition, false);
        }

        yPosition -= LINE_HEIGHT;

        // Turn-by-turn section
        yPosition = writeHeader(contentStream, "TURN-BY-TURN RUNDOWN", yPosition);

        for (int i = 0; i < turns.size(); i++) {
            Turn turn = turns.get(i);
            String correctness = turn.isCorrect() ? "CORRECT" : "INCORRECT";

            // Check if we need a new page
            if (yPosition < MARGIN + (LINE_HEIGHT * 8)) {
                contentStream.close();
                page = new PDPage(PDRectangle.LETTER);
                doc.addPage(page);
                contentStream = new PDPageContentStream(doc, page);
                yPosition = PAGE_HEIGHT - MARGIN;
            }

            // Turn number
            yPosition = writeLine(contentStream, "Turn " + (i + 1), yPosition, true);

            // Turn details
            yPosition = writeLine(contentStream, "  Player: " + turn.getPlayerName(), yPosition, false);
            yPosition = writeLine(contentStream, "  Category: " + turn.getCategory() + " (" + turn.getQuestionValue() + " points)", yPosition, false);
            yPosition = writeLine(contentStream, "  Question: " + sanitize(turn.getQuestionText()), yPosition, false);
            yPosition = writeLine(contentStream, "  Your Answer: " + sanitize(turn.getAnswerGiven()), yPosition, false);
            yPosition = writeLine(contentStream, "  Result: " + correctness, yPosition, false);
            yPosition = writeLine(contentStream, "  Points Earned: " + turn.getPointsEarned(), yPosition, false);
            yPosition = writeLine(contentStream, "  Running Total: " + turn.getScoreAfterTurn(), yPosition, false);

            yPosition -= LINE_HEIGHT;
        }

        // Footer
        yPosition = writeLine(contentStream, "Total Turns: " + turns.size(), yPosition, true);

        contentStream.close();
        doc.save(outFile);
        doc.close();

        return outFile;
    }

    /**
     * Write a title line (large text).
     */
    private float writeTitle(PDPageContentStream stream, String text, float yPosition) throws IOException {
        stream.setLeading(LINE_HEIGHT);
        stream.beginText();
        stream.setFont(null, 16); // Uses default font
        float xPosition = MARGIN + 100;
        stream.newLineAtOffset(xPosition, yPosition);
        stream.showText(text);
        stream.endText();
        return yPosition - (LINE_HEIGHT * 1.5f);
    }

    /**
     * Write a section header.
     */
    private float writeHeader(PDPageContentStream stream, String text, float yPosition) throws IOException {
        stream.setLeading(LINE_HEIGHT);
        stream.beginText();
        stream.setFont(null, 12);
        stream.newLineAtOffset(MARGIN, yPosition);
        stream.showText(text);
        stream.endText();
        return yPosition - LINE_HEIGHT;
    }

    /**
     * Write a regular line.
     */
    private float writeLine(PDPageContentStream stream, String text, float yPosition, boolean bold) throws IOException {
        stream.setLeading(LINE_HEIGHT);
        stream.beginText();
        stream.setFont(null, bold ? 11 : 10);
        stream.newLineAtOffset(MARGIN, yPosition);
        stream.showText(text);
        stream.endText();
        return yPosition - LINE_HEIGHT;
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