package com.group13.SummaryReport;

import com.group13.Logging.EventLogger;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class DOCXStrat implements ReportStrat {

    @Override
    public File generateReport(List<String> events, EventLogger log) throws Exception {
        File output = new File("summary_report.docx");

        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph title = doc.createParagraph();
        XWPFRun titleRun = title.createRun();
        titleRun.setBold(true);
        titleRun.setFontSize(16);
        titleRun.setText("Jeopardy Summary Report");

        XWPFParagraph scoreHeader = doc.createParagraph();
        scoreHeader.createRun().setText("Final Scores:");

        summary.getFinalScores().forEach((player, score) -> {
            XWPFParagraph p = doc.createParagraph();
            p.createRun().setText(player + ": " + score);
        });

        XWPFParagraph logHeader = doc.createParagraph();
        logHeader.createRun().setText("\nTurn-by-Turn Events:");

        for (Event e : events) {
            XWPFParagraph p = doc.createParagraph();
            p.createRun().setText(e.toString());
        }

        FileOutputStream out = new FileOutputStream(output);
        doc.write(out);
        out.close();

        return output;
    }
}
