package com.example.smarthomeupgrade.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smarthomeupgrade.MainActivity;
import com.example.smarthomeupgrade.R;
import com.example.smarthomeupgrade.util.SHUFileUtilities;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_html, container, false);


        WebView webView = (WebView) root.findViewById(R.id.webview_home);
        webView.setWebViewClient(new WebViewClient());
        //DATAUpdate!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        MainActivity.saveHandler.updateDatasets();
        MainActivity.saveHandler.updateHtml();
        //Update finished
        webView.loadUrl(Uri.parse("file://" + root.getContext().getFilesDir() + "/home.html").toString());
        WebSettings websettings = webView.getSettings();
        webView.setBackgroundColor(Color.TRANSPARENT);
        websettings.setJavaScriptEnabled(true);
        Log.d("test","url : " + root.getContext().getFilesDir() + "/home.html");

        return root;
    }
}
