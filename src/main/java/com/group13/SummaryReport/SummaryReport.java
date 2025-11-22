package com.group13.SummaryReport;

import com.group13.Logging.EventLogger;

import java.io.File;
import java.util.List;

public class SummaryReport {

    private ReportStrat strategy;

    public void setStrategy(ReportStrat strategy) {
        this.strategy = strategy;
    }

    public File generate(List<String> events, EventLogger log) throws Exception {
        if (strategy == null) {
            throw new IllegalStateException("No report strategy selected.");
        }
        return strategy.generateReport(events, summary);

}