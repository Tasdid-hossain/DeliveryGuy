package com.example.deliveryguy;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateCategoryActivity extends AppCompatActivity {

    EditText cat_image, cat_name;
    Button cat_submit;
    FirebaseDatabase database;
    DatabaseReference category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);

        cat_image= (EditText) findViewById(R.id.input_image);
        cat_name= (EditText) findViewById(R.id.input_name);
        cat_submit = (Button) findViewById(R.id.submit_category);
        setTitle("Create Category");
        /*Init Firebase*/
        database = FirebaseDatabase.getInstance();
        category = database.getReference().child("Category");

        cat_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryModel catModel = new CategoryModel();
                catModel.setName(cat_name.getText().toString());
                catModel.setImage(cat_image.getText().toString());
                category.push().setValue(catModel);
                Intent i = new Intent(CreateCategoryActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
