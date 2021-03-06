package com.example.smarthomeupgrade.ui.tutorial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.smarthomeupgrade.R;

public class TutorialFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TutorialViewModel tutorialViewModel = ViewModelProviders.of(this).get(TutorialViewModel.class);
        View root = inflater.inflate(R.layout.fragment_html, container, false);

        WebView webView = (WebView) root.findViewById(R.id.webview_home);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/tutorial.html");
        WebSettings websettings = webView.getSettings();
        websettings.setJavaScriptEnabled(true);

        return root;
    }
}
