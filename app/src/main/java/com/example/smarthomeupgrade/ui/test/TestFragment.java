package com.example.smarthomeupgrade.ui.test;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.smarthomeupgrade.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class TestFragment extends Fragment {

    private TestViewModel galleryViewModel;
    TextView txt;
    ArrayList<File> dateilist;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stats, container, false);

        //Snackbar.make(view, "text", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {


        //    }
        //}).show();


        return root;
    }
}
