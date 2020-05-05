package com.example.smarthomeupgrade.ui.tutorial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.smarthomeupgrade.R;

public class TutorialFragment extends Fragment {

    private TutorialViewModel tutorialViewModel;
    private WebView webView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tutorialViewModel = ViewModelProviders.of(this).get(TutorialViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tutorial, container, false);

        webView = (WebView) root.findViewById(R.id.webview_home);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/tutorial.html");
        WebSettings websettings = webView.getSettings();
        websettings.setJavaScriptEnabled(true);
        return root;
    }
}
