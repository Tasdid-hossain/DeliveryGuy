package com.example.deliveryguy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference events;
    EventModel currentEvent;
    EditText image1, image2, image3, image4;
    Button update_button, cancel_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        setTitle("Add Event");
        image1 = (EditText) findViewById(R.id.edit_image1);
        image2 = (EditText) findViewById(R.id.edit_image2);
        image3 = (EditText) findViewById(R.id.edit_image3);
        image4 = (EditText) findViewById(R.id.edit_image4);
        update_button = (Button) findViewById(R.id.update_event);
        cancel_button =(Button) findViewById(R.id.cancel_event);

        /*Init Firebase*/
        database = FirebaseDatabase.getInstance();
        events = database.getReference().child("Events");

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeData();
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        loadData();
    }

    private void writeData() {
        EventModel eventModel = new EventModel();
        eventModel.setImage1(image1.getText().toString());
        eventModel.setImage2(image2.getText().toString());
        eventModel.setImage3(image3.getText().toString());
        eventModel.setImage4(image4.getText().toString());
        events.setValue(eventModel);
        Intent i = new Intent(EventActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void loadData() {
        events.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentEvent = dataSnapshot.getValue(EventModel.class);
                image1.setText(currentEvent.getImage1());
                image2.setText(currentEvent.getImage2());
                image3.setText(currentEvent.getImage3());
                image4.setText(currentEvent.getImage4());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
