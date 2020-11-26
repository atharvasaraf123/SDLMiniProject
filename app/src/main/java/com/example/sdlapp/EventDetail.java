package com.example.sdlapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.Map;

public class EventDetail extends AppCompatActivity {

    TextView descTextView;
    TextView dateTextView;
    TextView timeTextView;
    TextView venueTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Intent intent=getIntent();
        Event obj=(Event) intent.getSerializableExtra("mapp");
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
        descTextView.setText(obj.getDesc());
        dateTextView.setText(obj.getDate());
        timeTextView.setText(obj.getTime());
        venueTextView.setText(obj.getVenue());
    }
}