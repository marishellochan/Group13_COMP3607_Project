package com.group13.Observer;

import com.group13.Logging.*;

public interface Subject {
    void registerEventLogger(Observer o);
    void removeEventLogger(Observer o);
    void notifyEventLogger(LogEntry entry);
}
