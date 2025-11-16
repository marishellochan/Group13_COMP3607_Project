package com.group13.Logging;
import java.io.File; //file operations
import java.io.FileWriter; //write text for files
import java.io.IOException; //file error
import java.time.LocalDateTime;//current date, time

//Record the events from jeopardy into csv file to see what happen

//Use singletion for 1 logger
public class EventLogger {
    private static EventLogger logger; //singletion instance
    private FileWriter writer;
    private String logFile = "event_log.csv";

    private EventLogger(){
        try{File file = new File(logFile);
            boolean fileExists = file.exists();
            writer = new FileWriter(logFile, true);//to write the text to the log file
            
            //put headings if new file
            if(!fileExists){
                writer.write("Case ID, Player ID, Activity, Timestamp, Category, Question Valie, Answer Given, Result, Score After");
              
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

    //This is where we log the events, so we need a lot of paramters for info
    public void log(String gameId, String playerId, String activity, String category, int questionValue, String answer, String result, int score){
        try{
            String time = LocalDateTime.now().toString(); //current time
           
           //this would be the csv line
            String line = gameId+","+playerId+","+activity+","+time+","+category+","+questionValue+","+answer+","+result+","+score+"\n";
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