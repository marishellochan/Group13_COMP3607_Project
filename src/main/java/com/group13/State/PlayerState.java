package com.group13.State;

import com.group13.Players.Player;
import com.group13.ExceptionHandling.IllegalStateException;

public abstract class PlayerState {
    
    abstract public void answerQuestion(Player player) throws IllegalStateException;
}
