package com.group13.SummaryReport;

import com.group13.Logging.EventLogger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.util.List;

public class PDFStrat implements ReportStrat {

    @Override
    public File generateReport(List<String> data, EventLogger log) throws Exception {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 700);

        for (String line : data) {
            contentStream.showText(line);
            contentStream.newLineAtOffset(0, -15);
        }

        contentStream.endText();
        contentStream.close();

        File outputFile = File.createTempFile("summary_report", ".pdf");
        document.save(outputFile);
        document.close();

        log.logEvent("PDF report generated: " + outputFile.getAbsolutePath());
        return outputFile;
    }
}

