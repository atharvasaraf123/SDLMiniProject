package com.example.sdlapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kusu.loadingbutton.LoadingButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class AddEvent extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    EditText dateEditText;
    EditText timeEditText;
    EditText nameEditText;
    EditText descEditText;
    EditText venueEditText;
    LoadingButton loadingButton;
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseFirestore fStore;
    boolean cs,entc,it=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        fAuth=FirebaseAuth.getInstance();
       user=fAuth.getCurrentUser();
        fStore=FirebaseFirestore.getInstance();
        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        }); nameEditText=findViewById(R.id.eventTitle);
        descEditText=findViewById(R.id.eventDesc);
        venueEditText=findViewById(R.id.eventVenue);
        dateEditText =  findViewById(R.id.eventDate);
        timeEditText=findViewById(R.id.eventTime);
        loadingButton=findViewById(R.id.loadingButton);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        loadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingButton.showLoading();
                String name=nameEditText.getText().toString().trim();
                String desc=descEditText.getText().toString().trim();
                String date=dateEditText.getText().toString().trim();
                String time=timeEditText.getText().toString().trim();
                String venue=venueEditText.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    nameEditText.setError("Name is required");
                    loadingButton.hideLoading();
                    return;
                }
                if(TextUtils.isEmpty(desc)){
                    descEditText.setError("Description is required");
                    loadingButton.hideLoading();
                    return;
                }
                if(TextUtils.isEmpty(date)){
                    dateEditText.setError("Date is required");
                    loadingButton.hideLoading();
                    return;
                }
                if(TextUtils.isEmpty(time)){
                    timeEditText.setError("Time is required");
                    loadingButton.hideLoading();
                    return;
                }
                if(TextUtils.isEmpty(venue)){
                    venueEditText.setError("Venue is required");
                    loadingButton.hideLoading();
                    return;
                }

                HashMap<String,Boolean>mapp=new HashMap<>();
                mapp.put("cs",cs);
                mapp.put("it",it);
                mapp.put("entc",entc);
                Event event=new Event(nameEditText.getText().toString().trim(),descEditText.getText().toString().trim(),dateEditText.getText().toString().trim(),timeEditText.getText().toString().trim(),venueEditText.getText().toString().trim(),mapp,user.getUid());
//                fStore.collection("clubs").add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                       event.setEventID(documentReference.getId());
//                        documentReference.set(event).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(getApplicationContext(),"Event Added",Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
//                    }

//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        loadingButton.setError("Please try again");
//                        Toast.makeText(getApplicationContext(),"Try again later",Toast.LENGTH_LONG).show();
//                    }
//                });
                fStore.collection("clubs").document(fAuth.getUid()).collection("events").add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        event.setEventID(documentReference.getId());
                        documentReference.set(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Event Added",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingButton.setError("Please try again");
                        Toast.makeText(getApplicationContext(),"Try again later",Toast.LENGTH_LONG).show();
                    }
                });
                loadingButton.hideLoading();
                startActivity(new Intent(getApplicationContext(),MainActivity2.class));
            }
        });
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        timeEditText.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddEvent.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    public void depCheckBoxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.comp:
                if(checked)cs=true;
                else cs=false;
                break;
            case R.id.it:
                if(checked)it=true;
                else it=false;
                break;
//            case R.id.entc:
//                if(checked)entc=true;
//                else entc=false;
//                break;
        }

    }
}