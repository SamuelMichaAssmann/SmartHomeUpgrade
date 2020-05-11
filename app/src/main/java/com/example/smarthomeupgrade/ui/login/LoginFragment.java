package com.example.smarthomeupgrade.ui.login;

import android.annotation.SuppressLint;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LoginFragment extends Fragment {

    private LoginViewModel LoginViewModel;
    private WebView webView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        Button commit = root.findViewById(R.id.button_add);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //Snackbar.make(view, "text", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {


        //    }
        //}).show();


        return root;
    }

    class WebLoadingTask extends AsyncTask<Void, Void, Void> {


        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL oracle = new URL("https://raw.githubusercontent.com/SamuelMichaAssmann/DummyDBSmartHomeUpgrade/master/W001");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(oracle.openStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    Log.d("test",inputLine);

                }
                in.close();
            } catch (Exception e) {
                Log.d("test","Error occured", e );
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
