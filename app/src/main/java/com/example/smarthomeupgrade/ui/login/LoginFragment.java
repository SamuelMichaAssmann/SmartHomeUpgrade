package com.example.smarthomeupgrade.ui.login;

import android.annotation.SuppressLint;
import android.graphics.Color;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.smarthomeupgrade.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.example.smarthomeupgrade.ui.login.LoginViewModel loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_login, container, false);


        WebView webView = (WebView) root.findViewById(R.id.txt);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/login.html");
        WebSettings websettings = webView.getSettings();
        webView.setBackgroundColor(Color.TRANSPARENT);
        websettings.setJavaScriptEnabled(true);


        Button commit = root.findViewById(R.id.button_add);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(root, "starting connection...", Snackbar.LENGTH_LONG).show();
                WebLoadingTask webLoadingTask = new WebLoadingTask();
                webLoadingTask.execute();
            }
        });



        return root;
    }

    class WebLoadingTask extends AsyncTask<Void, Void, Void> {

        ArrayList<ListElement> contents = new ArrayList();

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Log.d("WebLoadingTask","starting download");
                URL oracle = new URL("https://raw.githubusercontent.com/SamuelMichaAssmann/DummyDBSmartHomeUpgrade/master/W001");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(oracle.openStream()));

                String inputLine = in.readLine();
                if( !isValidSource(inputLine) ) {
                    Log.d("WebLoadingTask","Given Link is not a Valid Site: 1st Line: |" + inputLine+"|");
                    //return null;
                }
                while ((inputLine = in.readLine()) != null) {
                    contents.add(new ListElement(inputLine));
                    Log.d("WebLoadingTask","read line" + inputLine);

                }
                in.close();
            } catch (Exception e) {
                Log.d("WebLoadingTask","Error occured", e );
            }


            return null;
        }

        private boolean isValidSource(String firstLine){
            if(firstLine == "#SmarthomeUpgradeFile#") return true;
            return false;
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
            if(in.charAt(temp + 1 ) == 1){
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
    }
}
