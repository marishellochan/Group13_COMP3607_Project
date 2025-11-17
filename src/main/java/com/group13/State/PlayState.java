package com.group13.State;

public class PlayState extends GameState {
    
    @Override
    void loadgame() throws IllegalStateException {
        throw new IllegalStateException("Cannot load game from Play State.");
        
    }

    @Override
    void startgame() throws IllegalStateException {
        throw new IllegalStateException("Game is already in Play State.");
    }

    @Override
    void playgame() throws IllegalStateException {
        System.out.println("Playing the game...");
        // Implementation for playing the game
    }



    @Override
    void endgame() throws IllegalStateException {
        System.out.println("Ending the game...");
        // Implementation for ending the game from play state
    }

    @Override
    void generateReport() throws IllegalStateException {
        throw new IllegalStateException("Cannot generate report from Play State.");
    }

}
