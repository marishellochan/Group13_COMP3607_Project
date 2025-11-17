package com.group13.Singelton;

import com.group13.State.*;

public class Game {

    private static Game instance;
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

    
}