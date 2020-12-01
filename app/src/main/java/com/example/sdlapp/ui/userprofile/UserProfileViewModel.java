package com.example.sdlapp.ui.userprofile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<FirebaseAuth>fAuth;
    private MutableLiveData<FirebaseUser> user;

    public UserProfileViewModel() {
        mText = new MutableLiveData<>();
        fAuth=new MutableLiveData<>();
        user=new MutableLiveData<>();
        fAuth.setValue(FirebaseAuth.getInstance());
        user.setValue(fAuth.getValue().getCurrentUser());
        mText.setValue("This is home fragment");
    }

    public MutableLiveData<String> getmText() {
        return mText;
    }

    public void setmText(MutableLiveData<String> mText) {
        this.mText = mText;
    }

    public MutableLiveData<FirebaseAuth> getfAuth() {
        return fAuth;
    }

    public void setfAuth(MutableLiveData<FirebaseAuth> fAuth) {
        this.fAuth = fAuth;
    }

    public MutableLiveData<FirebaseUser> getUser() {
        return user;
    }

    public void setUser(MutableLiveData<FirebaseUser> user) {
        this.user = user;
    }
// TODO: Implement the ViewModel
}