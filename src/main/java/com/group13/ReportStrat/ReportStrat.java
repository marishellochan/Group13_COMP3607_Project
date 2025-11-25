package com.group13.ReportStrat;

import com.group13.GamePlay.*;
import java.io.File;
import java.util.List;

public interface ReportStrat {
    // creating a report file based on the list of turns and case ID
    File generateReport(List<Turn> turns, String caseId) throws Exception;
}
