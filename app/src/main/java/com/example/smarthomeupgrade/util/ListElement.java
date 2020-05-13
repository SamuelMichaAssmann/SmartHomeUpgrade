package com.example.smarthomeupgrade.util;

import androidx.annotation.NonNull;

public class ListElement {
    private String date = "";
    private String time = "";
    private Boolean state;
    private int dezibel;

    public ListElement(String in){
        int temp = 0;
        for(int i = 0; i < in.length(); i++){
            if(in.charAt(i) == '#'){
                temp = i;
                break;
            }
            date = date + in.charAt(i);
        }
        for(int i = temp + 1; i < in.length(); i++){
            if(in.charAt(i) == '#'){
                temp = i;
                break;
            }
            time = time + in.charAt(i);
        }
        if(in.charAt(temp + 1 ) == '1'){
            state = true;
        } else {
            state = false;
        }
        temp += 2;
        String buffer = "";
        for(int i = temp + 1; i < in.length(); i++){
            if(in.charAt(i) == 'd') break;
            buffer = buffer + in.charAt(i);
        }
        dezibel = Integer.parseInt(buffer);
    }

    public Boolean getState() {
        return state;
    }

    public int getDezibel() {
        return dezibel;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @NonNull
    @Override
    public String toString() {
        return "Element: <Date: " + date + ", Time: " + time + ", State: " + state + ", Dezibel: " + dezibel + ">";
    }

    public String getRawText(){
        if(state)
            return date + "#" + time + "#1#" + dezibel + "dB";
        else
            return date + "#" + time + "#0#" + dezibel + "dB";
    }
}
