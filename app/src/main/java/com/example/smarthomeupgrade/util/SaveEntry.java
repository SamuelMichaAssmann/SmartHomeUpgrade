package com.example.smarthomeupgrade.util;

import android.content.Context;
import android.util.Log;

public class SaveEntry {
    private String source = "";
    private String filename = "";
    private Long time = 00l;
    private Dataset corrData;


    public SaveEntry(String in) {
        interpret(in);
    }

    public SaveEntry() {

    }

    //GETTER
    public String getFilename() {
        return filename;
    }

    public Dataset getCorrData() {
        return corrData;
    }

    public String getSource() {
        return source;
    }

    public Long getTime() {
        return time;
    }

    //SETTER
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setCorrData(Dataset corrData) {
        this.corrData = corrData;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    //METHODS
    public void writeSave(Context context) {
        time = System.currentTimeMillis();
        String data = source + "#" + time.toString() + "#" + filename + "#" + "\n";
        String file = SHUFileUtilities.readFile(context, SHUFileUtilities.SAVE_FILE);
        if (file == null) {
            Log.d("test", "writing: \n" + data);
            SHUFileUtilities.writeToFile( data, context, SHUFileUtilities.SAVE_FILE);
        } else {
            Log.d("test","writing: \n" + file + "\n" + data);
            SHUFileUtilities.writeToFile(file + "\n" +  data, context , SHUFileUtilities.SAVE_FILE);
        }
    }

    public void loadData(Context context){
        corrData = new Dataset(context, filename);
        corrData.execute();
    }

    private void interpret(String in) {
        int temp = 0;
        String time = "";
        for (int i = 0; i < in.length(); i++) {
            if (in.charAt(i) == '#') {
                temp = i;
                break;
            }
            source = source + in.charAt(i);
        }
        for (int i = temp + 1; i < in.length(); i++) {
            if (in.charAt(i) == '#') {
                temp = i;
                break;
            }
            time = time + in.charAt(i);
        }
        for (int i = temp + 1; i < in.length(); i++) {
            if (in.charAt(i) == '#') {
                temp = i;
                break;
            }
            filename = filename + in.charAt(i);
        }
        this.time = Long.decode(time);

    }
}
