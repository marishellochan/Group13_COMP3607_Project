package com.group13.SummaryReport;

import com.group13.Logging.EventLogger;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class TXTStrat implements ReportStrat {
    
    @Override
    public File generateReport(List<String> events, EventLogger log) throws Exception {
        File output = new File("summary_report.txt");

        try (PrintWriter writer = new PrintWriter(output)) {
            writer.println("Jeopardy Summary Report");
            writer.println("-------------------------");
            writer.println();

            writer.println("Final Scores:");
            summary.getFinalScores().forEach((player, score) ->
                writer.println(player + ": " + score)
            );

            writer.println("\nTurn-by-Turn Events:");
            for (Event e : events) {
                writer.println(e.toString());
            }
        }

        return output;
    }
}
