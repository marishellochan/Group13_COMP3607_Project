package com.group13.State;

import com.group13.Players.Player;

public class AnswerState extends PlayerState {
    
    @Override
    public void answerQuestion(Player player) {
        // Logic for answering a question
        System.out.println(player.getPlayerName() + " is answering the question.");
    }
    
}
