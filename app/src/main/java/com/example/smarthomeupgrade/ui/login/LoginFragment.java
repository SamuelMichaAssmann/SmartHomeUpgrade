package com.example.smarthomeupgrade.ui.login;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
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
import androidx.annotation.RequiresApi;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthomeupgrade.MainActivity;
import com.example.smarthomeupgrade.R;
import com.example.smarthomeupgrade.util.Dataset;

import com.example.smarthomeupgrade.util.ManageAssetFolders;
import com.google.android.material.snackbar.Snackbar;



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
                    Dataset webLoadingTask = new Dataset( root, root.getContext(),filepath.getText().toString() + ".txt");
                    webLoadingTask.execute();
                } else if(github.getText().toString().equals("v")) {
                    Dataset webLoadingTask = new Dataset( root, "https://raw.githubusercontent.com/SamuelMichaAssmann/DummyDBSmartHomeUpgrade/master/W001", root.getContext(), filepath.getText().toString() + ".txt");
                    webLoadingTask.execute();
                    MainActivity.saveHandler.createSave(filepath.getText().toString() + ".txt","https://raw.githubusercontent.com/SamuelMichaAssmann/DummyDBSmartHomeUpgrade/master/W001");
                } else {
                    Dataset webLoadingTask = new Dataset( root, github.getText().toString(), root.getContext(), filepath.getText().toString() + ".txt");
                    webLoadingTask.execute();
                    MainActivity.saveHandler.createSave(filepath.getText().toString() + ".txt",github.getText().toString());
                }
                github.setText("");
                filepath.setText("");
                //final NavController navController = Navigation.findNavController();
                //navController.navigate(R.id.nav_home);

            }
        });

        return root;
    }

}
