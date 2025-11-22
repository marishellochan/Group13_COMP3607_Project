package com.group13.SummaryReport;

import com.group13.Logging.EventLogger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.util.List;

public class PDFSTRATONE {
    
    @Override
    public File generateReport(List<Event> events, GameSummary summary) throws Exception {
        File output = new File("summary_report.pdf");

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        PDPageContentStream stream = new PDPageContentStream(doc, page);
        stream.setFont(PDType1Font.HELVETICA, 12);
        stream.beginText();
        stream.newLineAtOffset(50, 750);
        stream.setLeading(14);

        stream.showText("Jeopardy Summary Report");
        stream.newLine();
        stream.newLine();

        stream.showText("Final Scores:");
        stream.newLine();
        summary.getFinalScores().forEach((player, score) -> {
            try {
                stream.showText(player + ": " + score);
                stream.newLine();
            } catch (Exception ignored) {}
        });

        stream.newLine();
        stream.showText("Turn-by-Turn Events:");
        stream.newLine();

        for (Event e : events) {
            stream.showText(e.toString());
            stream.newLine();
        }

        stream.endText();
        stream.close();
        doc.save(output);
        doc.close();

        return output;
    }
}
