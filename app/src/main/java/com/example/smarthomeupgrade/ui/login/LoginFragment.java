package com.example.smarthomeupgrade.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthomeupgrade.R;
import com.example.smarthomeupgrade.util.ManageAssetFolders;
import com.example.smarthomeupgrade.util.SHUFileUtilities;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {
    private View root;
    private RecyclerView list;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.example.smarthomeupgrade.ui.login.LoginViewModel loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_login, container, false);


        ManageAssetFolders manageAssetFolders = new ManageAssetFolders(root.getContext());
        manageAssetFolders.execute();

        WebView webView = (WebView) root.findViewById(R.id.txt);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/login.html");
        WebSettings websettings = webView.getSettings();
        webView.setBackgroundColor(Color.TRANSPARENT);
        websettings.setJavaScriptEnabled(true);



        //list = root.findViewById(R.id.listview);
        //layoutManager = new LinearLayoutManager(root.getContext());
        //list.setLayoutManager(layoutManager);


        Button commit = root.findViewById(R.id.button_add);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://raw.githubusercontent.com/SamuelMichaAssmann/DummyDBSmartHomeUpgrade/master/W001 test link
                EditText github = root.findViewById(R.id.text_link);
                EditText filepath = root.findViewById(R.id.text_name);
                Log.d("test","got Link: " + github.getText().toString());
                Snackbar.make(root, "starting connection...", Snackbar.LENGTH_SHORT).show();
                if(github.getText().toString().equals("debug")){
                    Log.d("test","debugging Dataset");
                    Dataset webLoadingTask = new Dataset(root, root.getContext(),filepath.getText().toString() + ".txt");
                    webLoadingTask.execute();
                } else {
                    Dataset webLoadingTask = new Dataset(root, github.getText().toString(), root.getContext(), filepath.getText().toString() + ".txt");
                    webLoadingTask.execute();
                }

            }
        });


        return root;
    }

    class Dataset extends AsyncTask<Void, Void, Void> {

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

        public Dataset(View root, String URL, Context context, String filename){
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
                    URL file = new URL(this.URL);
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

    class ListElement {
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

    class EntryList extends ArrayList<ListElement> {

        public void test(){
            Log.d("EntryListUtils", "testing methods:" +
                    "\ngetAllDates()    -> " + getAllDates() +
                    "\ngetAllTimes()    -> " + getAllTimes() +
                    "\ngetAllDezibels() -> " + getAllDezibels() +
                    "\ngetAllStates()   -> " + getAllStates()
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

        public String getAllStates(){
            if(this.isEmpty())
                return "[ ]";
            String out = "[ ";
            if(this.get(this.size() - 1).getState()){
                out = out + "True";
            } else {
                out = out + "False";
            }
            for (int i = 2; i < this.size() && i < 14; i++){
                if(this.get(this.size() - i).getState())
                    out = out + ", True";
                else
                    out = out + ", False";
            }
            return out + "]";
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

}
