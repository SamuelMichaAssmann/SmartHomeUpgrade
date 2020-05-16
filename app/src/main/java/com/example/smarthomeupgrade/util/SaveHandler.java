package com.example.smarthomeupgrade.util;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.util.ArrayList;

public class SaveHandler extends ArrayList<SaveEntry> {
    private Context context;
    private String filename;

    public SaveHandler(Context context) {
        this.context = context;
        this.filename = SHUFileUtilities.SAVE_FILE;

        BufferedReader in = SHUFileUtilities.connectToFile(context, filename);
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                this.add(new SaveEntry(inputLine));
            }
        } catch (Exception e) {

        }
        return;
    }

    public void createSave(String filename, String URL){
        SaveEntry newSave = new SaveEntry();
        newSave.setSource(URL);
        newSave.setFilename(filename);
        newSave.writeSave(context);
        this.add(newSave);
    }

    public SaveEntry getMostRecent(){
        if(isEmpty()) return null;
        SaveEntry i = null;
        Long v = 0l;
        for(SaveEntry entry: this){
            if(v < entry.getTime()){
                i = entry;
                v = entry.getTime();
            }
        }
        return i;
    }
}
