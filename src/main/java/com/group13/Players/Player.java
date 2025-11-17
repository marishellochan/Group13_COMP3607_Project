package com.group13.Players;

import com.group13.State.PlayerState;

public class Player {
    private String playerId;
    private String playerName;
    private int score;
    private PlayerState state;

    //this is the constrcutor
    public Player(String playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.score = 0; // Initial score 0
    }

    //we can add points
    public void addPoints(int points){
        this.score = this.score + points;
    }

    //getter for id
    public String getPlayerId(){
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

    public void setState(PlayerState state) {
        this.state = state;
    }

    public PlayerState getState() {
        return state;
    }
}

