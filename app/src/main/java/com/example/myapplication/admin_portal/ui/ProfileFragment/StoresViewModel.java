package com.example.myapplication.admin_portal.ui.ProfileFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StoresViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StoresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Stores fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}