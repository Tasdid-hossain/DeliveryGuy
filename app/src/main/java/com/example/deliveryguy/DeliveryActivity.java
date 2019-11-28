package com.example.deliveryguy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class DeliveryActivity extends AppCompatActivity {
    EditText status;
    Button change_status;
    String key;
    String uidKey, singleDataKey;

    FirebaseDatabase database;
    DatabaseReference Orders;
    int change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        status=(EditText) findViewById(R.id.status);
        change_status = (Button) findViewById(R.id.change_status);
        key = getIntent().getStringExtra("key");
        change =0;
        /*Init Firebase*/
        database = FirebaseDatabase.getInstance();
        Orders = database.getReference().child("Orders");
        setTitle("Change Status");
        loadData();

        change_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Orders.child(uidKey).child(singleDataKey).child("status").setValue(status.getText().toString());
                Intent i = new Intent(DeliveryActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadData() {
        Orders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("first snap",dataSnapshot.toString());
                for(DataSnapshot uid: dataSnapshot.getChildren()){
                    Log.d("Second snap",uid.toString());
                    for(DataSnapshot singleData: uid.getChildren()){
                        if(singleData.getKey().equals(key)){
                            uidKey = uid.getKey();
                            singleDataKey = singleData.getKey();
                            status.setText(singleData.child("status").getValue(String.class));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
