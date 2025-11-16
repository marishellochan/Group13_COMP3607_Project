package com.group13.Singelton;

public class Game {
    private static Game instance;

    private Game() {
        // Private constructor to prevent instantiation
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    // Other game-related methods can be added here
}