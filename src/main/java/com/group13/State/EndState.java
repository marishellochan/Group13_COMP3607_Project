package com.group13.State;

import com.group13.Singelton.Game;
public class EndState extends GameState {

//    @Override
//     public void handleGame(Game game) {
//          System.out.println("Ending game.....");
//          generateReport(game); // need to also implare strategy of printing report 
//     }

//     public void generateReport(Game game){
        
//     }

    @Override
    public void startgame(Game game) throws IllegalStateException {
        throw new IllegalStateException("Cannot start game from End State.");
    }

    @Override
    public void endgame(Game game) throws IllegalStateException {
        throw new IllegalStateException("Game is already in End State.");
    }

    @Override
    public void loadgame(Game game) throws IllegalStateException {
        throw new IllegalStateException("Cannot load game from End State.");
    }

    

   

    
    
}
