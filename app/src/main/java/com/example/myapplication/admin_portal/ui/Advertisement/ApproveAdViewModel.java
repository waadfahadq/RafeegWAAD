package com.example.myapplication.admin_portal.ui.Advertisement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ApproveAdViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ApproveAdViewModel() {
        mText = new MutableLiveData<> ();
        mText.setValue("This is Stores fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
