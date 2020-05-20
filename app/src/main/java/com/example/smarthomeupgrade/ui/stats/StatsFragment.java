package com.example.smarthomeupgrade.ui.stats;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.smarthomeupgrade.MainActivity;
import com.example.smarthomeupgrade.R;
import com.example.smarthomeupgrade.util.SHUFileUtilities;

import java.util.ArrayList;

public class StatsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StatsViewModel statsViewModel = ViewModelProviders.of(this).get(StatsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_html, container, false);



        WebView webView = (WebView) root.findViewById(R.id.webview_home);
        webView.setWebViewClient(new WebViewClient());
        //Update data
        MainActivity.saveHandler.updateDatasets();
        MainActivity.saveHandler.updateHtml();
        //Update finished
        webView.loadUrl(Uri.parse("file://" + root.getContext().getFilesDir() + "/stats.html").toString());
        WebSettings websettings = webView.getSettings();
        webView.setBackgroundColor(Color.TRANSPARENT);
        websettings.setJavaScriptEnabled(true);
        Log.d("test","url : " + root.getContext().getFilesDir() + "/stats.html");

        return root;
    }
}
