package com.group13.State;

import com.group13.Players.Player;
import com.group13.ExceptionHandling.IllegalStateException;

public abstract class WaitingState extends PlayerState {
    
    @Override
    public void answerQuestion(Player player) throws IllegalStateException {
        throw new IllegalStateException("Cannot answer question in Waiting State.");
    }

}
