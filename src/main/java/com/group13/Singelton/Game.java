package com.group13.Singelton;

import com.group13.State.*;

import  com.group13.Logging.*;
import com.group13.Observer.*;

public class Game implements Subject { // Marishel : the game is the subject so whenever an action is done, it will notify its EventLogger

    private static Game instance;
    private PlayerTurnManager playerTurnManager;
    private Observer eventLogger; // Marishel : the observer to log events
    private GameState state;

    private Game() {
        // Private constructor to prevent instantiation
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void setTurnManager(PlayerTurnManager ptm) {
        this.playerTurnManager = ptm;
    }


    public void setState(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return state;
    }

    public void load(){
        try{
            state.loadgame(this);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        
    }

    public void start(){
        try{
            state.startgame(this);
        } catch (Exception e){  
            System.out.println(e.getMessage());
        }
    }

    public void end(){
        try{
            state.endgame(this);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void registerEventLogger(Observer o) {
        // Implementation here
    }

    public void removeEventLogger(Observer o) {
        // Implementation here
    }

    public void notifyEventLogger(LogEntry entry) {
        // Implementation here
    }

    
}