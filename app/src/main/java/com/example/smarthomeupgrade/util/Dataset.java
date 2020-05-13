package com.example.smarthomeupgrade.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.smarthomeupgrade.ui.login.LoginFragment;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

public class Dataset extends AsyncTask<Void, Void, Void> {

    private String URL;
    private String commonSave = "savedDatasets.txt";
    private String filename = "last_pull.txt";
    private Boolean is_offline = false;
    private Context rootContext;
    private View root;

    public Dataset(View root, String URL, Context context){
        super();
        this.root = root;
        this.URL = URL;
        this.rootContext = context;
    }

    public Dataset( View root, String URL, Context context, String filename){
        super();
        this.root = root;
        this.filename = filename;
        this.URL = URL;
        this.rootContext = context;
    }

    public Dataset(View root, Context context, String filename){
        super();
        this.root = root;
        this.is_offline = true;
        this.filename = filename;
        this.rootContext = context;

    }

    EntryList contents = new EntryList();

    private void writeToFile(String data, Context context) {

        Log.d("WebLoadingTask" ,"Starting to write to file: " + filename);
        Log.d("WebLoadingTask" ,"data:\n" + data);
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private BufferedReader connectToFile(Context context) {
        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                return new BufferedReader(inputStreamReader);

            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        }
        return null;
    }

    private void updateHtml(){
        //rootContext.getApplicationContext().getResources().getAssets().
    }

    @SuppressLint("WrongThread")
    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("WebLoadingTask", "path to Files: " + rootContext.getFilesDir());

        try {
            BufferedReader in;
            Log.d("WebLoadingTask","starting download");
            if (!is_offline) {
                java.net.URL file = new URL(this.URL);
                in = new BufferedReader(
                        new InputStreamReader(file.openStream()));
            } else {
                in = connectToFile(rootContext);
            }
            String inputLine = in.readLine();
            if( !isValidSource(inputLine) ) {
                Log.d("WebLoadingTask","Given Link is not a Valid Site: 1st Line: |" + inputLine+"|");
                Snackbar.make(root,"Please make sure to enter a Valid site." , BaseTransientBottomBar.LENGTH_LONG).show();
                return null;
            }
            while ((inputLine = in.readLine()) != null) {
                contents.add(new ListElement(inputLine));
                Log.d("WebLoadingTask","reading line: " + inputLine);

            }
            Log.d("DatasetImport", "Imported:\n" + contents.toString());
            in.close();
        } catch (Exception e) {
            Log.d("WebLoadingTask","Error occured", e );
            Snackbar.make(root,"Oh oh, something went terribly wrong." , BaseTransientBottomBar.LENGTH_LONG).show();
            return null;
        }
        if(contents.isEmpty()){
            Log.d("WebloadingTask", "No Data found on Site, something must've gone wrong.");
            Snackbar.make(root,"No Data found on Site, something must've gone wrong." , BaseTransientBottomBar.LENGTH_LONG).show();
            return null;
        }

        if(!is_offline)
            writeToFile( contents.getRawText(), rootContext);

        Snackbar.make(root,"imported " + contents.size() + " entries, corresponding file: " + filename, BaseTransientBottomBar.LENGTH_LONG).show();
        contents.test();
        return null;
    }

    private boolean isValidSource(String firstLine){
        Log.d("WebloadingTask","validating source: "+ firstLine);
        if("#SmarthomeUpgradeFile#".equals(firstLine)) {
            Log.d("WebloadingTask","validation successful");
            return true;
        } else {
            Log.d("WebloadingTask","validation failed");
            return false;
        }
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
