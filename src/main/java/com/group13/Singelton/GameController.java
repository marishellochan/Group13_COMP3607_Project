package com.group13.Singelton;

import java.util.ArrayList;

import com.group13.Logging.LogEntry;
import com.group13.Players.Player;

public class GameController  { // this is what the UI screens will communicate with to perform actions

    private Game game = Game.getInstance();
    private PlayerTurnManager playerTurnManager = PlayerTurnManager.getInstance();
    
    private static GameController instance = null;

    private GameController() {
        // Private constructor to prevent instantiation
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void initializePlayers(ArrayList<String> playerNames) {
        ArrayList<Player> players = new ArrayList<>();
        
        for (String name : playerNames) {
            Player player = new Player(name);
            players.add(player);
            
            LogEntry entry = LogEntry.createPlayerJoinedEvent(
                String.valueOf(player.getPlayerId()), 
                player.getPlayerName()
            );
            notifyEventLogger(entry);
        }
        
        playerTurnManager.set_Players(players);
        playerTurnManager.setCurrentPlayer(players.get(0));
    }
}
