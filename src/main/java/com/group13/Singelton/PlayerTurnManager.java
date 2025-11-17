package com.group13.Singelton;

import java.util.List;
import com.group13.Players.Player;

public class PlayerTurnManager {
    private static PlayerTurnManager instance;
    private List<Player> players;

    private PlayerTurnManager() {
        // Private constructor to prevent instantiation
    }

    public static PlayerTurnManager getInstance() {
        if (instance == null) {
            instance = new PlayerTurnManager();
        }
        return instance;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void turn() {
        // implement some logic to manage player turns 
    }



    


}
