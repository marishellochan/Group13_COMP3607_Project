package com.group13.Logging;

import java.io.File; //file operations
import java.io.FileWriter; //write text for files
import java.io.IOException; //file error
import com.group13.Observer.Observer;

//Record the events from jeopardy into csv file to see what happen

//Use singletion for 1 logger
public class EventLogger implements Observer {
    private static EventLogger logger; //singletion instance
    private FileWriter writer;
    private String logFile = "game_event_log.csv";
    // private String currentGameId = "Game01"; //default

    private EventLogger(){
        try{
            File file = new File(logFile);
            boolean fileExists = file.exists();
            writer = new FileWriter(logFile, true);//to write the text to the log file
            
            //put headings if new file
            if(!fileExists){
                //add underscores for CSV, also to match project
                writer.write("Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play\n");
              
                writer.flush();//write immediately
            }
        } catch (IOException e){
            System.out.println("Log file error:"+e.getMessage());
        }
    }

    //create logger if have none
    public static EventLogger getInstance(){
        if(logger==null){
            logger= new EventLogger();
        }
        return logger;
    }

    // public void setGameId(String gameId) {
    //     this.currentGameId = gameId;
    // }


    public void updateLog(LogEntry entry){
        try{
            // String gameId = currentGameId;
            String playerId = entry.getPlayerId();
            String activity = entry.getActivity();
            String time = entry.getTime();
            String category = entry.getCategory();
            int questionValue = entry.getQuestionValue();
            String answer = entry.getAnswer();
            String result = entry.getResult();
            int score = entry.getScore();
           //this would be the csv line
            String line = String.format("%d,%s,%s,%s,%s,%d,%s,%s,%d\n",
                    LogEntry.getCaseId(), playerId, activity, time, category, questionValue, answer, result, score);
            writer.write(line);//write the line
            writer.flush();//save
        }catch(IOException e){
            System.out.println("Write error: "+e.getMessage());
        }
    }

    //close when game ends
    public void close(){
        try{
            if(writer!=null){
                writer.close();
                System.out.println("Log file closed.");
            }
        } catch (IOException e){
            System.out.println("Close error: "+e.getMessage());
        }
    }
}