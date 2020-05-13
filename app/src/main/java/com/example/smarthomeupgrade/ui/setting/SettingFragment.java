package com.example.smarthomeupgrade.ui.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.smarthomeupgrade.R;
import com.google.android.material.snackbar.Snackbar;

public class SettingFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.example.smarthomeupgrade.ui.setting.SettingViewModel settingViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_setting, container, false);

        CheckBox checkBox = root.findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(root, "Resetting", Snackbar.LENGTH_LONG).show();

            }
        });

        WebView webView = (WebView) root.findViewById(R.id.settings);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/setting.html");
        WebSettings websettings = webView.getSettings();
        webView.setBackgroundColor(Color.TRANSPARENT);
        websettings.setJavaScriptEnabled(true);

        return root;
    }
}
