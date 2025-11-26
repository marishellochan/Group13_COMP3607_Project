package com.group13.Logging;

import java.io.File; //file operations
import java.io.FileWriter; //write text for files
import java.io.IOException; //file error
import java.time.LocalDateTime;
import com.group13.Observer.Observer;
import com.group13.Logging.LogEntry;

//Record the events from jeopardy into csv file to see what happen

//Use singletion for 1 logger
public class EventLogger implements Observer {
    private static EventLogger logger; //singletion instance
    private FileWriter writer;
    private String logFile = "game_event_log.csv";
    private String currentGameId;

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

    //unique game id for the log
     public void setGameId(String gameId) {
         this.currentGameId = gameId;
}

//required method from observer interface
    @Override
    public void updateLog(LogEntry entry){
        try{
            String time = LocalDateTime.now().toString(); //fix for current time
            String line = String.format("%d,%s,%s,%s,%s,%d,%s,%s,%d\n",
                    entry.getCaseId(), //use caseId from log entry
                    entry.getPlayerId(), 
                    entry.getActivity(), 
                    time, //for iso format
                    entry.getCategory(), 
                    entry.getQuestionValue(), 
                    entry.getAnswer(), 
                    entry.getResult(), 
                    entry.getScore());
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

    public void clearLog() {
        try {
            writer.close();
            File file = new File(logFile);
            if (file.delete()) {
                System.out.println("Log file deleted successfully.");
            } else {
                System.out.println("Failed to delete the log file.");
            }
            // Reinitialize the writer
            writer = new FileWriter(logFile, true);
            // Write headers again
            writer.write("Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error clearing log file: " + e.getMessage());
        }
    }
}