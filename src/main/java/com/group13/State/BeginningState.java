package com.group13.State;

import com.group13.Singelton.Game;

public class BeginningState extends GameState {

    // public BeginningState() {
    //     // Initialization code for Beginning State

    // }

    // @Override
    // public void handleGame(Game game ){
    //     game.setState(new BeginningState());
    //     System.out.println("Loading game from Beginning State...");
    //     loadData(game);
    //     // load game data 
    // }

    // public void loadData(Game game){
    //     // implement loading of data
    // }

    // public void startGame(Game game){
    //     game.setState(new PlayState());
    //    game.getState().handleGame(game);
    // }

    @Override
    public void startgame(Game game) {
        game.setState(new PlayState());
        System.out.println("Starting game from Beginning State...");
    }

    @Override
    public void endgame(Game game) {
    }

    @Override
    public void loadgame(Game game) {
        game.setState(new BeginningState());
        System.out.println("Loading game from Beginning State...");
        //load game data here 
    }

    
}
