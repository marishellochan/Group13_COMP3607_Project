package com.group13.State;

import com.group13.ExceptionHandling.IllegalStateException;
import com.group13.Singelton.Game;
import com.group13.TemplatePattern_LoadData.TemplateLoadData;

public abstract class GameState {
    
    public abstract void startgame(Game game) throws IllegalStateException;
    public abstract void endgame(Game game) throws IllegalStateException;
    public abstract void loadgame(Game game, TemplateLoadData template)throws IllegalStateException;
}