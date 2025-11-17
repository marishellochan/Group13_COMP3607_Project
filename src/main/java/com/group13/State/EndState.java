package com.group13.State;
import com.group13.ExceptionHandling.IllegalStateException;
public class EndState extends GameState {

    @Override
    void loadgame() throws IllegalStateException {
        throw new IllegalStateException("Cannot load game from End State.");
    }

    @Override
    void startgame() throws IllegalStateException {
        throw new IllegalStateException("Cannot start game from End State.");
    }

    @Override
    void playgame() throws IllegalStateException {
        throw new IllegalStateException("Cannot play game from End State.");
    }

    @Override
    void endgame() throws IllegalStateException {
        throw new IllegalStateException("Game has already ended.");
    }

    @Override
    void generateReport() throws IllegalStateException {
        System.out.println("Generating end game report...");
        // Implementation for generating report at the end of the game
    }
    
}
