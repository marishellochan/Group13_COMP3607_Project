package com.group13.ReportStrat;

import com.group13.GamePlay.Turn;
import com.group13.Singelton.GameHistory;

import java.io.File;
import java.util.List;


public class SummaryReport {
    
    private final ReportStrat strategy;

    public SummaryReport(ReportStrat strategy) {
        this.strategy = strategy;
    }

    public File createReport(GameHistory history) throws Exception {
        List<Turn> turns = history.getTurns();
        return strategy.generateReport(turns);
    }

}
