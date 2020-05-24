package com.example.smarthomeupgrade.util;

import android.content.Context;
import android.os.AsyncTask;
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
        Log.d("updateHtml()", "Entering updateHtml()");
        if(isEmpty()){
            Log.d("updateHtml()", "Saves are empty, exiting method after copying home.html and stats.html");
            SHUFileUtilities.writeToFile(SHUFileUtilities.readAssetFile(context, "home.html"), context, "home.html");
            SHUFileUtilities.writeToFile(SHUFileUtilities.readAssetFile(context, "stats.html"), context, "stats.html");
            return;
        }


        Log.d("updateHtml()", "Updating: home.html");
        String states = "[ ";
        for(SaveEntry machine : this){
            while(machine.getCorrData() == null || !machine.getCorrData().isFinished()){
                Log.d("updateHtml()","waiting for Dataset: " + machine.getFilename());
            }
            String in = machine.getCorrData().contents.getCurrState();
            if(in != null){
                states = states + in + " ,";
            } else {
            }
        }
        states = states.substring(0,states.length()-1) + " ]";
        Log.d("updateHtml()", "updating home.html with states: " + states);

        String home = SHUFileUtilities.readAssetFile(context, "home.html");
        //Log.d("updateHtml()", "read hom:\n" + home);
        String[] repArr_home = new String[1];
        repArr_home[0] = states;
        String newHome = SHUFileUtilities.ersetze(home, repArr_home);
        SHUFileUtilities.writeToFile(newHome, context, "home.html");

        Log.d("updateHtml()", "Updating: stats.html");
        String stats = SHUFileUtilities.readAssetFile(context, "stats.html");
        String[] repArr_stats = new String[3];
        SaveEntry mostRecent = getMostRecent();
        while(mostRecent.getCorrData() == null || !mostRecent.getCorrData().isFinished()){}

        for(SaveEntry n : this){
            while(n.getCorrData() == null || !n.getCorrData().isFinished()){}
            ArrayList d = this.get(this.size()-1).getCorrData().contents.getEveryDates();
            ArrayList s = this.get(this.size()-1).getCorrData().contents.getEveryState();
            repArr_stats[2] = workwithdata(d,s);
            Log.d("Statsabfrage", s + "\n" + d);
        }


        repArr_stats[0] = mostRecent.getCorrData().contents.getAllDezibels();
        repArr_stats[1] = mostRecent.getCorrData().contents.getAllTimes();


        Log.d("updateHtml()", "Updating stats.html with Arrays: \n" + repArr_stats[0] + "\n" + repArr_stats[1] + "\n" + repArr_stats[2]);
        String newStats = SHUFileUtilities.ersetze(stats, repArr_stats);

        SHUFileUtilities.writeToFile(newStats, context, "stats.html");
    }

    private String workwithdata(ArrayList d, ArrayList s) {
        int[][] arr = new int[d.size()][2];
        arr[0][0]++;
        if (s.get(0).equals(false)) arr[0][1]++;
        int counter = 0;
        for (int i = 1; i < d.size(); i++) {
            if (d.get(i - 1).equals(d.get(i))){
                arr[counter][0]++;
                if (s.get(i).equals(false)) arr[counter][1]++;
            }else counter++;
        }
        for (int[] i : arr) {
            Log.d("Data", "All: " + i[0] + " True: " + i[1]);
        }
        String t = "[" + (100 / arr[0][0]) * arr[0][1];
        for (int j = 1; j < arr.length; j++){
            if (arr[j][0] != 0){
                t += ", " + 100 / arr[j][0] * arr[j][1];
            }else   t += ", 0";
        }

        return t + "]";
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

    public void updateDatasets(){
        for(SaveEntry machine : this){
            machine.setCorrData(new Dataset(
                    machine.getSource(),context,machine.getFilename()
            ));
            machine.getCorrData().execute();
        }
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
