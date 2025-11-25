package com.group13.Singelton;

import java.awt.EventQueue;
import com.group13.TemplatePattern_LoadData.TemplateLoadData;
import com.group13.UI.MainFrame;
import  com.group13.Logging.*;
import com.group13.Observer.*;
import com.group13.UI.MainFrame;

public class Game implements Subject { // Marishel : the game is the subject so whenever an action is done, it will notify its EventLogger

    private static Game instance;
    private PlayerTurnManager playerTurnManager;
    private Observer eventLogger; // Marishel : the observer to log events
    private GameData gameData;

    // private GameHistory gameHistory; //Aaron: track turns for reporting

    private Game() {
        // Private constructor to prevent instantiation
        this.playerTurnManager = PlayerTurnManager.getInstance();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    // public GameHistory getGameHistory() {
    //     return this.gameHistory;
    // }

    public PlayerTurnManager getTurnManager() {
        return this.playerTurnManager;
    }


    public void startUp(){
         EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }

    public void load(TemplateLoadData template){
        template.loadData(); 
    }

    public void end(){
        //end game 
    }

    public void registerEventLogger(Observer o) {
        // Implementation here
    }

    public void removeEventLogger(Observer o) {
        // Implementation here
    }

    public void notifyEventLogger(LogEntry entry) {
        // Implementation here
    }

    public void setGameData(GameData data){
        this.gameData = data;
    }

    public void startGameSession(String gameId){
        //start history when game begins
        // this.gameHistory = new GameHistory(gameId);
        // System.out.println("GameHistory started: "+gameId);

        //connect EventLogger with same gameId
        EventLogger logger = EventLogger.getInstance();
        logger.setGameId(gameId);

        //log that it started
        logger.logSystemEvent("Game Started");
    }
    
}