package com.example.smarthomeupgrade.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class SHUFileUtilities {
    public static void writeToFile(String data, Context context, String filename) {

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
}
