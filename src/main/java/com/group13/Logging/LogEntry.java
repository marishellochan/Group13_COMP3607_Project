package com.group13.Logging;

public class LogEntry {
    private String gameId;
    private String playerId;
    private String activity;
    private String category;
    private int questionValue;
    private String answer;
    private String result;
    private int score;
    
    //default constructor with empty log entry
    public LogEntry() {
        this.gameId = "";
        this.playerId = "";
        this.activity = "";
        this.category = "";
        this.questionValue = 0;
        this.answer = "";
        this.result = "";
        this.score = 0;
    }

    //constructor instead of hardcoded details
    public LogEntry(String gameId, String playerId, String activity, String category, int questionValue, String answer, String result, int score){
        this.gameId=gameId;
        this.playerId = playerId;
        this.activity = activity;
        this.category = category;
        this.questionValue = questionValue;
        this.answer = answer;
        this.result = result;
        this.score = score;
    }

    //basic log entry
    public LogEntry(String gameId, String playerId, String activity){
        this(gameId, playerId, activity, "", 0, "", "", 0);
    }

//we have getters cus Marishel say to access data from here
//to pass into log function as parameters
    public String getGameId(){return gameId;}
    public String getPlayerId(){return playerId;
    }
    public String getActivity(){return activity;
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

    //add setters for the various log entries
    public void setGameId(String gameId){this.gameId=gameId;
    }
    public void setPlayerId(String playerId){ this.playerId=playerId;
    }
    public void setActivity(String activity){this.activity=activity;
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
}
