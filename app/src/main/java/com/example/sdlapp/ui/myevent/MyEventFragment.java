package com.example.sdlapp.ui.myevent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdlapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MyEventFragment extends Fragment {

    private MyEventViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(MyEventViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myevent, container, false);
        RecyclerView recyclerView=root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final QuerySnapshot[] querySnapshot = new QuerySnapshot[1];
        Log.d("TAG", "a ");

        galleryViewModel.getFstore().getValue().collection("events").whereEqualTo("uid",galleryViewModel.getUser().getValue().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d("TAG", "fuck ");
                    querySnapshot[0] =task.getResult();
                    recyclerView.setAdapter(new RecyclerAdapter(querySnapshot[0]));
                }else{
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        return root;
    }
}