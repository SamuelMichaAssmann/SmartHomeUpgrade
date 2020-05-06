package com.example.smarthomeupgrade.ui.login;

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

public class LoginFragment extends Fragment {

    private LoginViewModel LoginViewModel;
    private WebView webView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        //Snackbar.make(view, "text", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {


        //    }
        //}).show();

        webView = (WebView) root.findViewById(R.id.webview_login);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/login.html");
        WebSettings websettings = webView.getSettings();
        websettings.setJavaScriptEnabled(true);


        return root;
    }
}
