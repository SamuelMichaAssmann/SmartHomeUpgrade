package com.example.smarthomeupgrade.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

import com.example.smarthomeupgrade.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private WebView webView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_html, container, false);

        webView = (WebView) root.findViewById(R.id.webview_home);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/home.html");
        WebSettings websettings = webView.getSettings();
        websettings.setJavaScriptEnabled(true);
        return root;
    }
}
