package com.group13.GamePlay;

import java.util.List;
import java.util.ArrayList;


//for full history of all turns in a game
//this generates the game report

public class GameHistory {
    private List<Turn> turns;
    private String caseId;

    //empty record
    public GameHistory(String caseId){
        this.caseId = caseId;
        this.turns = new ArrayList<>();//empty list
    }

    //add the turn to record
    public void recordTurn(Turn turn){
        turns.add(turn); //add to list
        System.out.println("Recorded turn "+turns.size()+": "+turn.getPlayerName());
    }

    //get all turns
    public List<Turn> getTurns(){
        return turns;
    }

    //turn count
    public int getTurnCount(){
        return turns.size();}

    //get one player turn
    public List<Turn> getTurnsForPlayer(String playerName){
        List<Turn> playerTurns = new ArrayList<>();

        //go through all
        for(Turn turn : turns){
            if(turn.getPlayerName().equals(playerName)){
                playerTurns.add(turn);
            }
        }
        return playerTurns;
    }

    //game id
    public String getCaseId(){
        return caseId;
    }

    //print turns
    public void printAllTurns(){
        System.out.println("Game History for: "+caseId);
        System.out.println("Total turns: "+ turns.size());
        System.out.println();

        for(int i=0;i< turns.size();i++){
            System.out.println("Turn "+(i+1)+": "+turns.get(i).toString());
        }
    }
    
}
