package com.group13.State;

public class BeginningState extends GameState {

    @Override
    void loadgame() throws IllegalStateException {
        System.out.println("Loading game from Beginning State...");
        // load game data 
    }

    @Override
    void startgame() throws IllegalStateException {
        // start game and transition to PlayState
        // Implementation for starting the game from beginning state
    }

    @Override
    void endgame() throws IllegalStateException {
        \throw new IllegalStateException("Cannot end game from Beginning State.");
        // Implementation for ending the game from beginning state
    }