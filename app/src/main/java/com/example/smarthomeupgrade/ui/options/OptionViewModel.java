package com.example.smarthomeupgrade.ui.options;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OptionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OptionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Login");
    }

    public LiveData<String> getText() {
        return mText;
    }
}