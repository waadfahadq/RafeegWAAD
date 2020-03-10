package com.example.myapplication.admin_portal.ui.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RequestsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RequestsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Requests fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}