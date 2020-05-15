package com.example.smarthomeupgrade.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.smarthomeupgrade.ui.login.LoginFragment;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;

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

        } else if (filename.equals("stats.html")) {
            int i;

        }
    }

    public static String ersetze(String data, String[] repArr) {
        String ready = "";
        try {
            for (int i = 0; i < repArr.length; i++) {
                ready += data.substring(0, data.indexOf("[")) + repArr[i];
                data = data.substring(data.indexOf("]") + 1);
            }
            ready += data;
        } catch (Exception e) {
            ready += data;
        }
        return (ready);
    }


    public static void copyAssets(Context context) {
        Log.d("copyAssets()", "entering copyAssets( " + context.toString() + " )");
        AssetManager assetManager = context.getAssets();
        //listAssetFiles("", context);
        String[] files = null;
        String[] directories = {"assets/bootstrap/js", "assets/bootstrap/css", "assets/fonts", "assets/img/scenery", "assets/img/socks", "assets/img/tutorial", "assets/js"};
        String debug = "directories:\n";
        for (String path : directories) {
            debug = debug + path + "\n";
        }
        Log.d("copyAssets()", debug);
        for (int i = 0; i < directories.length; i++) {

            try {
                Log.d("copyAssets()", "now getting list of files in " + directories[i]);
                files = assetManager.list(directories[i]);
                Log.d("copyAssets()", "successfully got the list");
                debug = "list contains:\n";
                for (String path : files) {
                    debug = debug + path + "\n";
                }
                Log.d("copyAssets()", debug + "list end\n");
            } catch (IOException e) {
                Log.e("copyAssets()", "Failed to get asset file list.", e);
            }

            String outDir = context.getFilesDir().getAbsolutePath() + "/" + directories[i];
            File test = new File(outDir);
            test.mkdirs();


            for (String filename : files) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    Log.d("copyAssets()", "copying " + filename + " from " + directories[i] + " to internal storage");
                    File outFile = new File(outDir, filename);
                    if(outFile.exists()){
                        Log.d("copyAssets()", "skipping " + filename );
                        continue;
                    }

                    in = context.getAssets().open(directories[i] + "/" + filename);
                    out = new FileOutputStream(outFile);


                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                } catch (IOException e) {
                    Log.e("copyAssets()", "Failed to copy asset file: " + filename, e);
                }
            }
        }
    }



    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }



}
