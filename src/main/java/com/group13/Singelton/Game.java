package com.group13.Singelton;

import java.awt.EventQueue;

import com.group13.TemplatePattern_LoadData.TemplateLoadData;
import com.group13.UI.MainFrame;
import  com.group13.Logging.*;
import com.group13.Observer.*;
import com.group13.UI.MainFrame;

public class Game implements Subject { // Marishel : the game is the subject so whenever an action is done, it will notify its EventLogger

    private static Game instance;
    private Observer eventLogger; // Marishel : the observer to log events
    private PlayerTurnManager playerTurnManager;
    private GameData gameData;
    private GameHistory gameHistory;

    // private GameHistory gameHistory; //Aaron: track turns for reporting

    private Game() {
        // Private constructor to prevent instantiation
        this.playerTurnManager = PlayerTurnManager.getInstance();
        this.gameData = GameData.getInstance();
        this.gameHistory = GameHistory.getInstance();
        registerEventLogger();


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
        LogEntry entry = LogEntry.createSystemEvent("Game Start up..");
        notifyEventLogger(entry);
        EventLogger.getInstance().clearLog(); // clear previous logs on startup
    }

    public void load(TemplateLoadData template){
        template.loadData(); 
        LogEntry entry = new LogEntry();
        entry.setPlayerId("System");
        entry.setActivity("Load Game Data");
        notifyEventLogger(entry);
    }

    public void end(){
        LogEntry entry = new LogEntry();
        entry.setPlayerId("System");
        entry.setActivity("Game Ended");
        notifyEventLogger(entry);
        EventLogger.getInstance().close();
    }

    public void registerEventLogger() {
        this.eventLogger = EventLogger.getInstance();

    }

    public void removeEventLogger() {
        this.eventLogger = null;
    }

    public void notifyEventLogger(LogEntry entry) {
        if (this.eventLogger != null) {
            this.eventLogger.updateLog(entry);
        }
    }

    public PlayerTurnManager getTurnManager(){
        return this.playerTurnManager;
    }

    public GameData getGameData(){
        return this.gameData;
    }

    public GameHistory getGameHistory(){
        return this.gameHistory;
    }

    // public void setGameData(GameData data){
    //     this.gameData = data;
    // }

    // public void startGameSession(String gameId){
    //     //start history when game begins
    //     // this.gameHistory = new GameHistory(gameId);
    //     // System.out.println("GameHistory started: "+gameId);

    //     //connect EventLogger with same gameId
    //     EventLogger logger = EventLogger.getInstance();
    //     logger.setGameId(gameId);

    //     //log that it started
    //     logger.logSystemEvent("Game Started");
    // }
    
}