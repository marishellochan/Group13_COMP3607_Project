package com.group13.State;

import com.group13.ExceptionHandling.IllegalStateException;
import com.group13.Singelton.Game;
import com.group13.TemplatePattern_LoadData.TemplateLoadData;

public class PlayState extends GameState {
    
    // @Override
    // public void handleGame(Game game) {
    //     System.out.println("Game is starting in Play State...");
    //     displayGameBoard(game);
    //     play(game);
    //     // implement play state logic
    // }

    // public void displayGameBoard(Game game){
    //     // implement display game board
    // }

    // public void play(Game game) {
    //     // implement game play logic
    // }

    // public void endGame(Game game){
    //     game.setState(new EndState());
    //     game.getState().handleGame(game);
    // }

    @Override
    public void startgame(Game game) throws IllegalStateException {
        throw new IllegalStateException("Game is already in Play State.");
    }

    @Override
    public void endgame(Game game) throws IllegalStateException {
        game.setState(new EndState());
        System.out.println("Ending game from Play State...");
        generateReport(game);
    }

    @Override
    public void loadgame(Game game, TemplateLoadData template) throws IllegalStateException {
        throw new IllegalStateException("Cannot load game while in Play State.");
    }

    public void generateReport(Game game){
        // implement report generation logic
        // player has to choose what strategy to use for report generation
    }


}
