package com.group13.SummaryReport;

import com.group13.SummaryReport.*;
import com.group13.Logging.EventLogger;

import java.io.File;
import java.util.List;

public interface ReportStrat {

    File generateReport(List<String> data, EventLogger log) throws Exception;
}
