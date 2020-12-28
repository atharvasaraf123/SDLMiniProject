package com.example.sdlapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sdlapp.ui.dashboard.DashboardFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kusu.loadingbutton.LoadingButton;

import java.util.List;
import java.util.Map;

public class EventSuggestion extends AppCompatActivity {

    TextView descTextView;
    TextView dateTextView;
    TextView timeTextView;
    TextView venueTextView;
    TextView eveName;
    LoadingButton loadingButton;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_suggestion);
        Intent intent=getIntent();
        Event obj=(Event) intent.getSerializableExtra("values");
        Log.d("abc",intent.getStringExtra("collectionId"));
        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(obj.getTitle().toUpperCase());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        descTextView=findViewById(R.id.eventDesc);
        dateTextView=findViewById(R.id.eventDate);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        timeTextView=findViewById(R.id.eventTime);
        eveName=findViewById(R.id.eveName);
        venueTextView=findViewById(R.id.eventVenue);
        loadingButton=findViewById(R.id.loadingButton);
        firestore=FirebaseFirestore.getInstance();
        eveName.setText(obj.getTitle());
        descTextView.setText(obj.getDesc());
        dateTextView.setText(obj.getDate());
        timeTextView.setText(obj.getTime());
        venueTextView.setText(obj.getVenue());
        if(firebaseUser.getUid().equals(obj.getUid())){
         loadingButton.setVisibility(View.INVISIBLE);

        }else{
            loadingButton.setText("I want to visit");
            loadingButton.setButtonColor(Color.BLACK);
        }

        loadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingButton.showLoading();
                if(firebaseUser.getUid().equals(obj.getUid())){
                   firestore.collection("events").document(obj.getEventID()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(getApplicationContext(),"Event Deleted",Toast.LENGTH_LONG);
                              Intent intent1=new Intent(getApplicationContext(), DashboardFragment.class);
                              startActivity(intent1);
                               loadingButton.hideLoading();
                           }
                       }
                   });
                }else{
                    Log.d("alls",obj.getEventID());
                    DocumentReference documentReference=firestore.collection("clubs").document(intent.getStringExtra("collectionId"));
                    documentReference.collection("events").document(obj.getEventID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                Long v= (Long) task.getResult().get("visitorCount");
                                documentReference.collection("events").document(obj.getEventID()).update("visitorCount",v+1);
                                loadingButton.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(),"Visitor added",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}