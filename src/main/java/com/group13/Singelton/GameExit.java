package com.group13.Singelton;

import javax.swing.JOptionPane;

import com.group13.UI.GenerateReportScreen;
import com.group13.UI.MainFrame;

public class GameExit {
    private static GameExit instance = null;
    private MainFrame mainFrame;

    private GameExit() {
        // Private constructor to prevent instantiation
    }

    public static GameExit getInstance() {
        if (instance == null) {
            instance = new GameExit();
        }
        return instance;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    public void exitGame() {
        int choice = JOptionPane.showConfirmDialog(
            mainFrame,
            "Are you sure you want to quit?",
            "Quit Game",
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            Game.getInstance().end();
            mainFrame.showScreen(new GenerateReportScreen(mainFrame));
        }
    }

    
}
