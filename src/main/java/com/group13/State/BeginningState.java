package com.group13.State;

import com.group13.Singelton.Game;
import com.group13.Singelton.GameData;
import com.group13.TemplatePattern_LoadData.*;
import com.group13.ExceptionHandling.IllegalStateException;
public class BeginningState extends GameState {

    @Override
    public void startgame(Game game) throws IllegalStateException {
        game.setState(new PlayState());
        System.out.println("Starting game from Beginning State...");
        
    }

    @Override
    public void endgame(Game game) throws IllegalStateException {
        throw new IllegalStateException("Cannot end game from Beginning State.");
    }

    @Override
    public void loadgame(Game game) throws IllegalStateException {
        TemplateLoadData loadDataMethod = chooseLoadDataTemplate();
        loadDataMethod.loadData();
        set_Players(game);
    

        //load game data here
        
    }

    public TemplateLoadData chooseLoadDataTemplate(){
        
        // click of button to choose load data template
    }

    public int getPlayerNumber(){
        // get number of players from user input
    }

    public void set_Players(Game game){
        // create players and set them in PlayerTurnManager
    }


    
}
