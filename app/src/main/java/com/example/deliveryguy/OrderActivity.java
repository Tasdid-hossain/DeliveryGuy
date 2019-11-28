package com.example.deliveryguy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = "OrderActivity";
    private static final int ACTIVITY_NUM =3;
    FirebaseDatabase database;
    DatabaseReference Orders;
    Query orderyQuery;
    ArrayList<Request> requests;

    RecyclerView recycler_orders;
    RecyclerView.LayoutManager layoutManager;
    OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Log.d(TAG, "onCreate: Started");
        requests = new ArrayList<>();
        setTitle("Order Details");
            /*Init Firebase*/
        database = FirebaseDatabase.getInstance();
        Orders = database.getReference().child("Orders");
        orderyQuery = Orders.orderByKey();
        getOrders();
            /*Load category*/
        recycler_orders = (RecyclerView) findViewById(R.id.recycler_orders);
        layoutManager = new LinearLayoutManager(this);
        recycler_orders.setHasFixedSize(true);
        recycler_orders.setLayoutManager(new LinearLayoutManager(this));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                loadOrderAdapter();
            }
        },5000);

    }

    private void getOrders() {
        Orders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("first snap",dataSnapshot.toString());
                for(DataSnapshot uid: dataSnapshot.getChildren()){
                    Log.d("Second snap",uid.toString());
                    for(DataSnapshot singleData: uid.getChildren()){

                        String address= singleData.getKey();
                        String status = singleData.child("status").getValue(String.class);
                        Log.d("TAG", address + " / " + status);
                        Request r = new Request();
                        r.setAddress(address);
                        r.setStatus(status);
                        requests.add(new Request(address,status));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadOrderAdapter() {
        adapter = new OrderAdapter(requests,this);
        adapter.notifyDataSetChanged();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recycler_orders.setAdapter(adapter);
            }
        });

    }
}
