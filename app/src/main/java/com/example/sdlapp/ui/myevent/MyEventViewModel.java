package com.example.sdlapp.ui.myevent;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class MyEventViewModel extends ViewModel {

    private MutableLiveData<FirebaseAuth>fAuth;
    private MutableLiveData<FirebaseUser> user;
    private MutableLiveData<FirebaseFirestore> fstore;
    private MutableLiveData<QuerySnapshot>qSnapshot;


    public MyEventViewModel() {
        fAuth=new MutableLiveData<>();
        user=new MutableLiveData<>();
        qSnapshot=new MutableLiveData<>();
        fstore=new MutableLiveData<>();
        fAuth.setValue(FirebaseAuth.getInstance());
        fstore.setValue(FirebaseFirestore.getInstance());
        user.setValue(fAuth.getValue().getCurrentUser());
        Log.d("abcd","abcd");
//        fstore.getValue().collection("events").whereEqualTo("uid",user.getValue().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    Log.d("abcd",String.valueOf(task.getResult().size()));
//                    qSnapshot.setValue(task.getResult());
//                }else{
//                    Log.d("TAG", "Error getting documents: ", task.getException());
//                }
//            }
//        });
//        fstore.getValue().collection("events")
//                .whereEqualTo("capital", true)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

    }

    public MutableLiveData<QuerySnapshot> getqSnapshot() {
        return qSnapshot;
    }

    public MutableLiveData<FirebaseFirestore> getFstore() {
        return fstore;
    }

    public MutableLiveData<FirebaseAuth> getfAuth() {
        return fAuth;
    }

    public MutableLiveData<FirebaseUser> getUser() {
        return user;
    }


}