package com.example.smarthomeupgrade.util;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.smarthomeupgrade.ui.login.LoginFragment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class SHUFileUtilities {
    public static void writeToFile(String data, Context context, String filename) {
        replaceData(data,filename);

        Log.d("Read/WriteUtil" ,"Starting to write to file: " + filename);
        Log.d("Read/WriteUtil" ,"data:\n" + data);
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static ArrayList<String> readAssetFile(Context context, String filename) {
        ArrayList<String> out = new ArrayList<>();
        try {
            InputStream inputStream = context.getAssets().open(filename);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                out.add(bufferedReader.readLine());
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    out.add("\n");
                    out.add(receiveString);
                }

                inputStream.close();

            }
        }
        catch (Exception e) {
            Log.e("Read/WriteUtil", "File not found: " + e.toString());
        }
        return out;
    }


    public static void replaceData(String data, String filename) {

        if (filename.equals("home.html")) {

        }else if (filename.equals("stats.html")){
            int i;

        }
        }

    public static String ersetze( String data, String[] repArr) {
        String ready = "";
        try {
            for (int i = 0;i < repArr.length ;i++ ) {
                ready += data.substring(0,data.indexOf("[")) + repArr[i];
                data = data.substring(data.indexOf("]")+1);
            }
            ready += data;
        } catch(Exception e) {
            ready += data;
        }
        return (ready);
    }


}
