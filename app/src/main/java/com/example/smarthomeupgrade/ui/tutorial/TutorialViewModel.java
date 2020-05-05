package com.example.smarthomeupgrade.ui.tutorial;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TutorialViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TutorialViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Tutorial");
    }

    public LiveData<String> getText() {
        return mText;
    }
}