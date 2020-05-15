package com.example.smarthomeupgrade.util;

import android.content.Context;

public class SaveEntry {
    private String source = "";
    private Long time = 00l;


    public SaveEntry(String in){
        interpret(in);
    }

    public SaveEntry(){

    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public Long getTime() {
        return time;
    }

    public void writeSave(Context context){
        time = System.currentTimeMillis();
        String file = SHUFileUtilities.readFile(context,SHUFileUtilities.SAVE_FILE);
        if(file == null)
            SHUFileUtilities.writeToFile(source + "#" + time.toString() + "#",context,SHUFileUtilities.SAVE_FILE);
        else
        SHUFileUtilities.writeToFile(file + "\n" + source + "#" + time.toString() + "#",context,SHUFileUtilities.SAVE_FILE);
    }

    private void interpret(String in){
        int temp = 0;
        String time = "";
        boolean state;
        for(int i = 0; i < in.length(); i++){
            if(in.charAt(i) == '#'){
                temp = i;
                break;
            }
            source = source + in.charAt(i);
        }
        for(int i = temp + 1; i < in.length(); i++){
            if(in.charAt(i) == '#'){
                temp = i;
                break;
            }
            time = time + in.charAt(i);
        }
        this.time = Long.decode(time);

    }
}
