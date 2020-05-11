package com.example.smarthomeupgrade.ui.setting;

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

public class SettingFragment extends Fragment {

    private SettingViewModel SettingViewModel;
    private WebView webView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SettingViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);

        //Snackbar.make(view, "text", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {

        //    }
        //}).show();

        return root;
    }
}
