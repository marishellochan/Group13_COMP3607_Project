package com.group13.Logging;

import java.time.LocalDateTime;

public class LogEntry {
    private static int caseCounter = 0; // Static counter for unique case IDs
    private int caseId ; // Unique case ID for each log entry
    private String playerId;
    private String time;
    private String activity;
    private String category;
    private int questionValue;
    private String answer;
    private String result;
    private int score;
  
    public LogEntry() {
        this.caseId = caseCounter++; //for gamesession id
        this.time = LocalDateTime.now().toString();
    }

    public static LogEntry createSystemEvent(String activity) {
        LogEntry entry = new LogEntry();
        entry.setPlayerId("System");
        entry.setActivity(activity);
        return entry;
    }
    
    // Player joined event
    public static LogEntry createPlayerJoinedEvent(String playerId, String playerName) {
        LogEntry entry = new LogEntry();
        entry.setPlayerId(playerId);
        entry.setActivity("Enter Player Name");
        return entry;
    }
    
    // Select category event
    public static LogEntry createSelectCategoryEvent(String playerId, String category) {
        LogEntry entry = new LogEntry();
        entry.setPlayerId(playerId);
        entry.setActivity("Select Category");
        entry.setCategory(category);
        return entry;
    }
    
    // Select question event
    public static LogEntry createSelectQuestionEvent(String playerId, String category, int questionValue) {
        LogEntry entry = new LogEntry();
        entry.setPlayerId(playerId);
        entry.setActivity("Select Question");
        entry.setCategory(category);
        entry.setQuestionValue(questionValue);
        return entry;
    }
    
    // Answer question event
    public static LogEntry createAnswerQuestionEvent(String playerId, String category, 
                                                      int questionValue, String answer, 
                                                      String result, int score) {
        LogEntry entry = new LogEntry();
        entry.setPlayerId(playerId);
        entry.setActivity("Answer Question");
        entry.setCategory(category);
        entry.setQuestionValue(questionValue);
        entry.setAnswer(answer);
        entry.setResult(result);
        entry.setScore(score);
        return entry;
    }

    public static LogEntry scoreUpdatedEvent(String playerId, int newScore) {
        LogEntry entry = new LogEntry();
        entry.setPlayerId(playerId);
        entry.setActivity("Score Updated");
        entry.setScore(newScore);
        return entry;
    }



    public int getCaseId(){ 
        return this.caseId;
    }

    public String getTime(){ return time;
    }
    public String getActivity(){ return activity;
    }
    public String getPlayerId(){return playerId;
    }
    public String getCategory(){return category;
    }
    public int getQuestionValue(){return questionValue;
    }
    public String getAnswer(){ return answer;
    }
    public String getResult(){return result;
    }
    public int getScore(){ return score;
    }
    
    public void setPlayerId(String playerId){ this.playerId=playerId;
    }
    public void setCategory(String category){this.category=category;
    }
    public void setQuestionValue(int questionValue){ this.questionValue=questionValue;
    }
    public void setAnswer(String answer){ this.answer=answer;
    }
    public void setResult(String result){this.result=result;
    }
    public void setScore(int score){ this.score=score;
    }
    public void setActivity(String activity){ this.activity=activity;
    }
    

}
