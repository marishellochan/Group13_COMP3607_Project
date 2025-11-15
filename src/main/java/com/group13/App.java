package com.group13;

import com.group13.TemplatePattern_LoadData.LoadDataCSV;
import com.group13.TemplatePattern_LoadData.LoadDataXML;
import com.group13.TemplatePattern_LoadData.LoadDataJSON;
import com.group13.Singelton.GameData;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "TESTING Load Data" );
        GameData gameData = GameData.getInstance();

        // LoadDataCSV loadDataCSV = new LoadDataCSV();
        // loadDataCSV.loadData(); 

        // LoadDataXML loadDataXML = new LoadDataXML();
        // loadDataXML.loadData();

        LoadDataJSON loadDataJSON = new LoadDataJSON();
        loadDataJSON.loadData();


        gameData.printQuestions();
        
    }
}
