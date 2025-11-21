package com.group13.Singelton;

import java.util.ArrayList;
import java.util.List;
import com.group13.Players.Player;

public class PlayerTurnManager {
    private static PlayerTurnManager instance;
    private static final int MAX_PLAYERS = 4;
    private static final int MIN_PLAYERS = 2;
    private int currentNumberOfPlayers;
    private Player currentPlayer;
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

    // public int getPlayerNumber(){
    //     // get number of players from user input button
    // }

    // public void set_Players(){
    //     // create players and set them in PlayerTurnManager
    //     int numPlayers = getPlayerNumber();
    //     ArrayList<Player> players = new ArrayList<>();
    //     for(int i = 1; i <= numPlayers; i++){
    //         // read player name from iput MUST CHange LATER
    //         Player player = new Player("NamePlayer"+i);
    //         players.add(player);
    //     }
    //     this.players = players;
    // }

    public List<Player> getPlayers() {
        return players;
    }

    public void turn() {
        // implement some logic to manage player turns 
    }



    


}
