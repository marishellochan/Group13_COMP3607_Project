package com.group13.Observer;

import com.group13.Logging.*;

public interface Subject {
    void registerEventLogger();
    void removeEventLogger();
    void notifyEventLogger(LogEntry entry);
}
