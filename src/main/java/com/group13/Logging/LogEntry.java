package com.group13.Logging;

public class LogEntry {
    private String gameId = "Game01";
    private String playerId = " Player1";
    private String activity;
    private String category = "";
    private int questionValue= 0;
    private String answer = "";
    private String result = "";
    private int score = 0;
    

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
