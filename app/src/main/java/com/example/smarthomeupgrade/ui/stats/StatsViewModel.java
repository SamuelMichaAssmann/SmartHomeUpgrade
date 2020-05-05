package com.example.smarthomeupgrade.ui.stats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StatsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Stats");
    }

    public LiveData<String> getText() {
        return mText;
    }
}