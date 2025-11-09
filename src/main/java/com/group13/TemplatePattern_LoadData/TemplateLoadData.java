package com.group13.TemplatePattern_LoadData;

public abstract class TemplateLoadData {
    String path;

    public TemplateLoadData(String path) {
        this.path = path;
    }

    public final void loadData() {
        readData();
        processData();
        saveData();
    }

    protected abstract void readData();

    protected abstract void processData();

    protected abstract void saveData();
}
