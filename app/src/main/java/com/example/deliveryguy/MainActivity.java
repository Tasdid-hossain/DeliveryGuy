package com.example.deliveryguy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_category, btn_add_category, btn_event, btn_editProd, btn_createProd, btn_orders;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Admin Panel");

        btn_category = (Button) findViewById(R.id.button_category);
        btn_add_category = (Button) findViewById(R.id.button_add_category);
        btn_event = (Button) findViewById(R.id.button_event);
        btn_editProd = (Button) findViewById(R.id.button_editProd);
        btn_createProd = (Button) findViewById(R.id.button_createProd);
        btn_orders = (Button) findViewById(R.id.button_order);

        btn_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MainActivity.this, OrderActivity.class);
                startActivity(i);
            }
        });
        btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CategoryActivity.class);
                startActivity(i);
            }
        });

        btn_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CreateCategoryActivity.class);
                startActivity(i);
            }
        });

        btn_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,EventActivity.class);
                startActivity(i);
            }
        });

        btn_editProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ProductListActivity.class);
                startActivity(i);
            }
        });

        btn_createProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MainActivity.this, CreateProduct.class);
                startActivity(i);
            }
        });
    }


}
