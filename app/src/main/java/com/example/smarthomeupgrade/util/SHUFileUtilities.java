package com.example.smarthomeupgrade.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.smarthomeupgrade.ui.login.LoginFragment;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class SHUFileUtilities {
    public static void writeToFile(String data, Context context, String filename) {
        //replaceData(data,filename);

        Log.d("Read/WriteUtil", "Starting to write to file: " + filename);
        Log.d("Read/WriteUtil", "data:\n" + data);
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }



    public static String readAssetFile(Context context, String filename) {
        String out = "";
        try {
            InputStream inputStream = context.getAssets().open(filename);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                out = bufferedReader.readLine();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    out = out + "\n";
                    out = out + receiveString;
                }

                inputStream.close();

            }
        } catch (Exception e) {
            Log.e("Read/WriteUtil", "File not found: " + e.toString());
        }
        return out;
    }


    public static void replaceData(View root, Context context, String data, String filename) {

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



    public static void copyFileOrDir(Context context,String path, String destinationDir) {
        AssetManager assetManager = context.getAssets();
        String assets[] = null;
        try {
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(context, path,destinationDir);
            } else {
                String fullPath = destinationDir + "/" + path;
                File dir = new File(fullPath);
                if (!dir.exists())
                    dir.mkdir();
                for (int i = 0; i < assets.length; ++i) {
                    copyFileOrDir(context,path + "/" + assets[i], destinationDir + path + "/" + assets[i]);
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    private static void copyFile(Context context, String filename, String destinationDir) {
        AssetManager assetManager = context.getAssets();
        String newFileName = destinationDir + "/" + filename;

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        new File(newFileName).setExecutable(true, false);
    }




}
