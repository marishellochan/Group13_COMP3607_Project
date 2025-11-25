package com.group13.Singelton;

import java.util.ArrayList;
import java.util.List;
import com.group13.Players.Player;

public class PlayerTurnManager {
    private static PlayerTurnManager instance;
    private static final int MAX_PLAYERS = 4;
    private static final int MIN_PLAYERS = 2;
    private int currentNumberOfPlayers;
    private int currentTurn = 1;
    private Player currentPlayer;
    private List<Player> players;
    private String gameSessionId; //for game id

    private PlayerTurnManager() {
        // Private constructor to prevent instantiation
        this.gameSessionId = "G" + System.currentTimeMillis(); // Unique game session ID using timestamp to never repeat
    }

    public static PlayerTurnManager getInstance() {
        if (instance == null) {
            instance = new PlayerTurnManager();
        }
        return instance;
    }

    public void set_Players(ArrayList<Player> players){
        this.players = players;
        this.currentNumberOfPlayers = players.size();
        this.currentPlayer = players.get(0); //start with first player
    }

    //Case id whith game session and turn
    public String getCurrentCaseId(){
        return gameSessionId + "_" + String.format("%02d", currentTurn);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void turn() {
        // implement some logic to manage player turns 
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public void nextTurn() {
        currentTurn = (currentTurn % currentNumberOfPlayers) + 1;
        currentPlayer = players.get(currentTurn - 1);
    }
}
