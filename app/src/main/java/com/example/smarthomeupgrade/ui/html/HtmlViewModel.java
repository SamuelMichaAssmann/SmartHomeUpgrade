package com.example.smarthomeupgrade.ui.html;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HtmlViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HtmlViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Html");
    }

    public LiveData<String> getText() {
        return mText;
    }
}