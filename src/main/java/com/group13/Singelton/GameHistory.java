package com.group13.Singelton;

import java.util.List;

import com.group13.GamePlay.Turn;

import java.util.ArrayList;


//for full history of all turns in a game
//this generates the game report

public class GameHistory {
    private static GameHistory instance = null;
    private List<Turn> turns;
    private String caseId;

    //empty record
    private GameHistory(){
        this.turns = new ArrayList<>();//empty list
    }

    public static GameHistory getInstance(){
        if (instance == null){
            instance = new GameHistory();
        }
        return instance;
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
