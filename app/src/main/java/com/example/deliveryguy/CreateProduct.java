package com.example.deliveryguy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateProduct extends AppCompatActivity {
    Button updateProduct, cancelProduct;
    EditText edit_categoryID, edit_description, edit_image, edit_name, edit_price, edit_image1, edit_image2, edit_image3, edit_image4;
    FirebaseDatabase database;
    DatabaseReference prodref;
    ProductModel currentProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        cancelProduct = (Button) findViewById(R.id.create_cancel);
        updateProduct = (Button) findViewById(R.id.create_product);
        edit_categoryID = (EditText) findViewById(R.id.create_categoryID);
        edit_description = (EditText) findViewById(R.id.create_description);
        edit_image = (EditText) findViewById(R.id.create_image);
        edit_name = (EditText) findViewById(R.id.create_nameProduct);
        edit_price = (EditText) findViewById(R.id.create_price);
        edit_image1 = (EditText) findViewById(R.id.create_image1);
        edit_image2 = (EditText) findViewById(R.id.create_image2);
        edit_image3 = (EditText) findViewById(R.id.create_image3);
        edit_image4 = (EditText) findViewById(R.id.create_image4);
        setTitle("Create Product");
        /*Init Firebase*/
        database = FirebaseDatabase.getInstance();
        prodref = database.getReference().child("Product");

        updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeData();
            }
        });
        cancelProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateProduct.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void writeData() {
        ProductModel productModelModel = new ProductModel();
        productModelModel.setCategoryID(edit_categoryID.getText().toString());
        productModelModel.setImage(edit_image.getText().toString());
        productModelModel.setName(edit_name.getText().toString());
        productModelModel.setDescription(edit_description.getText().toString());
        productModelModel.setPrice(edit_price.getText().toString());

        ImageListModel imageListModel = new ImageListModel();
        imageListModel.setImage1(edit_image1.getText().toString());
        imageListModel.setImage2(edit_image2.getText().toString());
        imageListModel.setImage3(edit_image3.getText().toString());
        imageListModel.setImage4(edit_image4.getText().toString());
        productModelModel.setImageList(imageListModel);
        prodref.push().setValue(productModelModel);
        Intent i = new Intent(CreateProduct.this, MainActivity.class);
        startActivity(i);
    }
}
