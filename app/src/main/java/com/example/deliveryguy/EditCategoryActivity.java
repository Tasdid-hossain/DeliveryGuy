package com.example.deliveryguy;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditCategoryActivity extends AppCompatActivity {
    String CategoryID;
    Button updateCategory;
    EditText updateName, updateImage;
    FirebaseDatabase database;
    DatabaseReference category;
    CategoryModel currentCategory;
    EditText categoryKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        updateCategory = (Button) findViewById(R.id.update_category);
        updateName = (EditText) findViewById(R.id.edit_name);
        updateImage = (EditText) findViewById(R.id.edit_image);
        categoryKey = (EditText) findViewById(R.id.categoryKey);
        setTitle("Edit or Remove Category");
        CategoryID = getIntent().getStringExtra("CategoryID");
        /*Init Firebase*/
        database = FirebaseDatabase.getInstance();
        category = database.getReference().child("Category").child(CategoryID);
        readData();

        updateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeData();
            }
        });
    }

    private void writeData() {
        CategoryModel catModel = new CategoryModel();
        catModel.setName(updateName.getText().toString());
        catModel.setImage(updateImage.getText().toString());
        category.setValue(catModel);
        Intent i = new Intent(EditCategoryActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void readData() {
        category.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentCategory = dataSnapshot.getValue(CategoryModel.class);
                updateName.setText(currentCategory.getName());
                updateImage.setText(currentCategory.getImage());
                categoryKey.setText(dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
