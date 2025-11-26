package com.group13.TemplatePattern_LoadData;

import java.util.List;
import java.io.BufferedReader;

public abstract class TemplateLoadData {
    protected List<String[]> allGameData;
    protected List<String> lines;
    protected BufferedReader br;
    protected String filePath;

    public TemplateLoadData(String filePath) {
        this.filePath = filePath;
    }
    
    public final void loadData(){
        readData();
        try {
            parseAndStoreData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void readData();
    protected abstract void parseAndStoreData() throws Exception;
}
