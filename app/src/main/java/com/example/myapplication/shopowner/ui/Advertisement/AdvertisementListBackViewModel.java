package com.example.myapplication.shopowner.ui.Advertisement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdvertisementListBackViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AdvertisementListBackViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Stores fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
