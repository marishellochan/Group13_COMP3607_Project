package com.group13.Singelton;

import java.util.ArrayList;
import java.util.List;

import com.group13.Logging.LogEntry;
import com.group13.Players.Player;
import com.group13.Questions.Question;
import com.group13.Singelton.Game;
import com.group13.TemplatePattern_LoadData.TemplateLoadData;

public class GameController  { // this is what the UI screens will communicate with to perform actions

    private Game game = Game.getInstance();
    
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

    public void loadGame(TemplateLoadData template){
        game.load(template);
        game.getGameData().printQuestions();
    }

    public void endGame(){
        game.end();
    }

    public void initializePlayers(ArrayList<String> playerNames) {
        ArrayList<Player> players = new ArrayList<>();
        
        for (String name : playerNames) {
            Player player = new Player(name);
            players.add(player);
            
            // Create log entry
            LogEntry entry = LogEntry.createPlayerJoinedEvent(String.valueOf(player.getPlayerId()), 
                player.getPlayerName());
            game.notifyEventLogger(entry);
        }
        
        game.getTurnManager().set_Players(players);
        game.getTurnManager().setCurrentPlayer(players.get(0));
    }

    public List<Player> getAllPlayers() {
        return game.getTurnManager().getPlayers();
    }

     public Player getCurrentPlayer() {
        return game.getTurnManager().getCurrentPlayer();
    }

     public List<String> getAvailableCategories() {
        return game.getGameData().getCategories();
    }
    
    public int getCurrentTurn() {
        return game.getTurnManager().getCurrentTurn();
    }

    public void nextTurn() {
        game.getTurnManager().nextTurn();
    }

    // categories and values for question selection
    public boolean isCategoryAvailable(String category) {
        List<Question> questions = game.getGameData().getQuestionsByCategory(category);
        for (Question q : questions) {
            if (!q.isAnswered()) {
                return true;
            }
        }
        return false;
    }

    public void selectCategory(String category) {
        Player currentPlayer = game.getTurnManager().getCurrentPlayer();
        LogEntry entry = LogEntry.createSelectCategoryEvent(String.valueOf(currentPlayer.getPlayerId()), 
            category
        );
        game.notifyEventLogger(entry);
    }

    //question logic 
    public List<Integer> getQuestionValues() {
        return game.getGameData().getValues();
    }

    public boolean isQuestionAvailable(String category, int value) {
        Question q = game.getGameData().getQuestionByCategoryAndValue(category, value);
        return q != null && !q.isAnswered();
    }

    public Question selectQuestion(String category, int value) {
        Player currentPlayer = game.getTurnManager().getCurrentPlayer();
        Question question = game.getGameData().getQuestionByCategoryAndValue(category, value);
        
        if (question != null) {
            LogEntry entry = LogEntry.createSelectQuestionEvent(
                String.valueOf(currentPlayer.getPlayerId()),
                category, 
                value
            );
            game.notifyEventLogger(entry);
        }
        
        return question;
    }

    // player answering logic 
    public boolean submitAnswer(Question question, String answer) {
        Player currentPlayer = getCurrentPlayer();
        boolean correct = question.checkAnswer(answer);
        String result = "Incorrect";
        
        if (correct) {
            result = "Correct";
            currentPlayer.addPoints(question.getValue());
        }
        
        question.setAnswered();

        //use new caseId from turn manager
        String caseId = game.getTurnManager().getCurrentCaseId();
        
        // Logging
        LogEntry entry = LogEntry.createAnswerQuestionEvent(
            String.valueOf(currentPlayer.getPlayerId()),
            question.getCategory(),
            question.getValue(),
            answer,
            result,
            currentPlayer.getScore()
        );
        game.notifyEventLogger(entry);
        
        if (correct) {
            LogEntry scoreEntry = LogEntry.scoreUpdatedEvent(String.valueOf(currentPlayer.getPlayerId()), 
                currentPlayer.getScore()
            );
            game.notifyEventLogger(scoreEntry);
        }
        return correct;
    }

    // this ensures the game ends when all questions are answered

    public boolean isGameOver() {
        List<String> categories = game.getGameData().getCategories();
        for (String category : categories) {
            if (isCategoryAvailable(category)) {
                return false;
            }
        }
        return true;
    }

}
