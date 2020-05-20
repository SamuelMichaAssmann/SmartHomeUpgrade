package com.example.smarthomeupgrade.util;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class EntryList extends ArrayList<ListElement> {

    public void test(){
        Log.d("EntryListUtils", "testing methods:" +
                "\ngetAllDates()    -> " + getAllDates() +
                "\ngetAllTimes()    -> " + getAllTimes() +
                "\ngetAllDezibels() -> " + getAllDezibels() +
                "\ngetAllStates()   -> " + getCurrState()
        );
    }

    public String getAllDates(){
        if(this.isEmpty())
            return "[ ]";
        String out = "[ " + "\"" + this.get(this.size() - 1).getDate() + "\"";
        for (int i = 2; i < this.size() && i < 14; i++){
            out = out + ", " + "\"" + this.get(this.size() - i).getDate() + "\"";
        }
        return out + "]";
    }

    public String getAllTimes(){
        if(this.isEmpty())
            return "[ ]";
        String out = "[ " + "\"" + this.get(this.size() - 1).getTime() + "\"";
        for (int i = 2; i < this.size() && i < 14; i++){
            out =  out + ", " + "\"" + this.get(this.size() - i).getTime() + "\"";
        }
        return out + "]";
    }

    public String getAllDezibels(){
        if(this.isEmpty())
            return "[ ]";
        String out = "[ " + this.get(this.size() - 1).getDezibel();
        for (int i = 2; i < this.size() && i < 14; i++){
            out = out + ", " + this.get(this.size() - i).getDezibel();
        }
        return out + "]";
    }

    public String getCurrState(){
        if(this.isEmpty())
            return null;
        if(this.get(this.size()-1).getState())
            return "true";
        return "false";
    }

    @NonNull
    @Override
    public String toString() {
        String out = "Entry List, len = " + this.size() + ", containing:\n";
        for (int i = 0; i < this.size(); i++){
            out = out + "     " + this.get(i) + "\n";
        }
        return out;
    }

    public String getRawText() {
        String out = "#SmarthomeUpgradeFile#\n";
        for (int i = 0; i < this.size(); i++){
            out = out + this.get(i).getRawText() + "\n";
        }
        return out;
    }

}
