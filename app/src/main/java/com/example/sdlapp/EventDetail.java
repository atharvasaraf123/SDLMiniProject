package com.example.sdlapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.sdlapp.ui.dashboard.DashboardFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class EventDetail extends AppCompatActivity {

    TextView descTextView;
    TextView dateTextView;
    TextView timeTextView;
    TextView venueTextView;
    TextView visitorTextView;
    String eventID;
    Button deleteButton;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Intent intent=getIntent();
        Event obj=(Event) intent.getSerializableExtra("mapp");
        eventID=obj.getEventID();
        progressBar=findViewById(R.id.progress_bar);
        Log.d("abcd",obj.getDate());
        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(obj.getTitle());
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
        timeTextView=findViewById(R.id.eventTime);
        venueTextView=findViewById(R.id.eventVenue);
        visitorTextView=findViewById(R.id.eventVisitor);
        descTextView.setText(obj.getDesc());
        dateTextView.setText(obj.getDate());
        deleteButton=findViewById(R.id.deleteButton);
        timeTextView.setText(obj.getTime());
        venueTextView.setText(obj.getVenue());
        visitorTextView.setText(String.valueOf(obj.getVisitorCount()));
        Log.d("abc", String.valueOf(obj.getVisitorCount()));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                FirebaseFirestore.getInstance().collection("clubs").document(FirebaseAuth.getInstance().getUid()).collection("events").document(eventID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Event deleted",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(), DashboardFragment.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }





}