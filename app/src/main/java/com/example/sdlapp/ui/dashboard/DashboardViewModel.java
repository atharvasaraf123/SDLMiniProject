package com.example.sdlapp.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<FirebaseAuth>fAuth;
    private MutableLiveData<FirebaseUser> user;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        fAuth=new MutableLiveData<>();
        user=new MutableLiveData<>();
        fAuth.setValue(FirebaseAuth.getInstance());
        user.setValue(fAuth.getValue().getCurrentUser());
        mText.setValue("This is home fragment");
    }



    public MutableLiveData<FirebaseAuth> getfAuth() {
        return fAuth;
    }

    public MutableLiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<String> getText() {
        return mText;
    }
}