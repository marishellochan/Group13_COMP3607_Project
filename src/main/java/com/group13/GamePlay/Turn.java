package com.group13.GamePlay;

//This is for the game report
//collect the info for each player's turn
public class Turn {
    private String playerName;
    private String category;
    private int questionValue;
    private String questionText;
    private String answerGiven;
    private boolean isCorrect;
    private int pointsEarned;
    private int scoreAfterTurn;

    //give all details for turn
    public Turn(String playerName, String category, int value, String questionText, String answer, boolean correct, int points, int newScore){
        this.playerName = playerName;
        this.category = category;
        this.questionValue = value;
        this.questionText = questionText;
        this.answerGiven = answer;
        this.isCorrect = correct;
        this.pointsEarned = points;
        this.scoreAfterTurn = newScore;
    }

    //getters for the info
    public String getPlayerName(){return playerName;
    }

    public String getCategory(){return category;
    }

    //point value
    public int getQuestionValue(){return questionValue;
    }

    //full question
    public String getQuestionText(){return questionText;
    }

    public String getAnswerGiven(){
        return answerGiven;
    }

    public boolean isCorrect(){ return isCorrect;
    }


    //points eanred or lost 
    public int getPointsEarned(){ return pointsEarned;
    }

    //total
    public int getScoreAfterTurn(){ return scoreAfterTurn;
    }

    //print turn
    public String toString(){
        String correctness;
        if(isCorrect){
            correctness = "correct";
        } else {
            correctness = "incorrect";
        }
        return playerName+" | "+category+" (" +questionValue+ " pts) | "+ 
               "Answer: "+answerGiven+" | "+ correctness+" | Score: "+scoreAfterTurn;
    }
}
