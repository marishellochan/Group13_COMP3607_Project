package com.group13.State;

import com.group13.ExceptionHandling.IllegalStateException;

public abstract class GameState {
    
<<<<<<< HEAD
    abstract void startgame() throws IllegalStateException;
    abstract void endgame() throws IllegalStateException;
    abstract void loadgame() throws IllegalStateException;
    abstract void playgame() throws IllegalStateException;
    abstract void generateReport() throws IllegalStateException;
=======
    abstract void loadgame() throws IllegalStateException;
    abstract void startgame() throws IllegalStateException; 
    abstract void playgame() throws IllegalStateException;
    abstract void endgame() throws IllegalStateException; 
    abstract void generateReport() throws IllegalStateException;

>>>>>>> 11990299794cc52b0093e9d4eca3863700009688
}