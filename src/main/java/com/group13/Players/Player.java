package com.group13.Players;

public class Player {
    private static int playerId = 0; // Static variable to keep track of player IDs
    private String playerName;
    private int score;

    //this is the constrcutor
    public Player(String playerName) {
        playerId++; // Marishel : let the id increment for each new player
        this.playerName = playerName;
        this.score = 0; // Initial score 0
        
    }

    //we can add points
    public void addPoints(int points){
        this.score = this.score + points;
    }

    //getter for id
    public int getPlayerId(){
        return playerId;
    }

    //to get the name of player
    public String getPlayerName(){
        return playerName;
    }

    //for the score
    public int getScore(){
        return  score;
    }

    //toString
    @Override
    public String toString(){
        return "Player ID: "+playerId+", Player Name: "+playerName+", Score: "+score;
    }

}

