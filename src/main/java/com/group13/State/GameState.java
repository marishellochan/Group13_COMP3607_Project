package com.group13.State;

import com.group13.ExceptionHandling.IllegalStateException;

public abstract class GameState {
    
    abstract void loadgame() throws IllegalStateException;
    abstract void startgame() throws IllegalStateException; 
    abstract void playgame() throws IllegalStateException;
    abstract void endgame() throws IllegalStateException; 
    abstract void generateReport() throws IllegalStateException;

}