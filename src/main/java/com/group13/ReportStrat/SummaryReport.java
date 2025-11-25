package com.group13.ReportStrat;

import com.group13.GamePlay.GameHistory;
import com.group13.GamePlay.Turn;

import java.io.File;
import java.util.List;


public class SummaryReport {
    
    private final ReportStrat strategy;

    public SummaryReport(ReportStrat strategy) {
        this.strategy = strategy;
    }

    public File createReport(GameHistory history) throws Exception {
        String caseId = history.getCaseId();
        List<Turn> turns = history.getTurns();
        return strategy.generateReport(turns, caseId);
    }

}
