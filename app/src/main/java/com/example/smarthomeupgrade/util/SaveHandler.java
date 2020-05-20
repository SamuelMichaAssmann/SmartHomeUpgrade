package com.example.smarthomeupgrade.util;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

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
                SaveEntry nEntry = new SaveEntry(inputLine);
                if( (!isNewerVersion(nEntry)) && (!isOlderVersion(nEntry)) ){
                    this.add(nEntry);
                    nEntry.loadData(context);
                }
            }
            in.close();
        } catch (Exception e) {

        }
        return;
    }

    public void updateHtml() {
        if(isEmpty())
            return;


        Log.d("updateHtml()", "Updating: home.html");
        String states = "[ ";
        for(SaveEntry machine : this){
            while(machine.getCorrData() == null){
                Log.d("updateHtml()","waiting for Dataset: " + machine.getFilename());
            }
            String in = machine.getCorrData().contents.getCurrState();
            if(in != null){
                states = states + in + " ,";
            } else {
            }
        }
        states = states.substring(0,states.length()-1) + " ]";

        String home = SHUFileUtilities.readAssetFile(context, "home.html");
        //Log.d("updateHtml()", "read hom:\n" + home);
        String[] repArr_home = new String[1];
        repArr_home[0] = states;
        String newHome = SHUFileUtilities.ersetze(home, repArr_home);
        SHUFileUtilities.writeToFile(newHome, context, "home.html");

        Log.d("updateHtml()", "Updating: stats.html");
        String stats = SHUFileUtilities.readAssetFile(context, "stats.html");
        String[] repArr_stats = new String[2];
        repArr_stats[0] = getMostRecent().getCorrData().contents.getAllDezibels();
        repArr_stats[1] = getMostRecent().getCorrData().contents.getAllTimes();
        String newStats = SHUFileUtilities.ersetze(stats, repArr_stats);

        SHUFileUtilities.writeToFile(newHome, context, "stats.html");
    }



    public void createSave(String filename, String URL){
        SaveEntry newSave = new SaveEntry();
        newSave.setSource(URL);
        newSave.setFilename(filename);
        newSave.loadData(context);
        newSave.writeSave(context);
        removeOlderVersions(newSave);
        this.add(newSave);
    }

    private void removeOlderVersions(SaveEntry newEntry){
        for(SaveEntry in: this){
            if(newEntry.getFilename().contentEquals(in.getFilename()) && newEntry.getSource().contentEquals(in.getSource()))
                if(newEntry.getTime() > in.getTime())
                    this.remove(in);
        }
    }

    private boolean isNewerVersion(SaveEntry newEntry){
        for(SaveEntry in: this){
            if(newEntry.getFilename().contentEquals(in.getFilename()) && newEntry.getSource().contentEquals(in.getSource()))
                if(newEntry.getTime() > in.getTime())
                    return true;
        }
        return false;
    }

    private boolean isOlderVersion(SaveEntry newEntry){
        for(SaveEntry in: this){
            if(newEntry.getFilename().contentEquals(in.getFilename()) && newEntry.getSource().contentEquals(in.getSource()))
                if(newEntry.getTime() < in.getTime())
                    return true;
        }
        return false;
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
