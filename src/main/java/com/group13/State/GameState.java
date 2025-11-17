package com.group13.State;

public abstract class GameState {
    
    abstract void startgame() throws IllegalStateException;
    abstract void endgame() throws IllegalStateException;
    abstract void loadgame() throws IllegalStateException;
    abstract void playgame() throws IllegalStateException;
    abstract void generateReport() throws IllegalStateException;
}