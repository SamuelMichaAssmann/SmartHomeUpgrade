package com.example.smarthomeupgrade.ui.html;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.smarthomeupgrade.R;

public class NutzungsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HtmlViewModel htmlViewModel = ViewModelProviders.of(this).get(HtmlViewModel.class);
        View root = inflater.inflate(R.layout.fragment_html, container, false);

        WebView webView = (WebView) root.findViewById(R.id.webview_home);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/nutzerbedingungen.html");
        WebSettings websettings = webView.getSettings();
        websettings.setJavaScriptEnabled(true);

        return root;
    }
}
