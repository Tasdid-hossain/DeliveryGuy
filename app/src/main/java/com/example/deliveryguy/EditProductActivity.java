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

public class EditProductActivity extends AppCompatActivity {
    String ProductKey;
    Button updateProduct, cancelProduct;
    EditText edit_categoryID, edit_description, edit_image, edit_name, edit_price, edit_image1, edit_image2, edit_image3, edit_image4;
    FirebaseDatabase database;
    DatabaseReference prodref;
    ProductModel currentProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        cancelProduct = (Button) findViewById(R.id.cancel_product);
        updateProduct = (Button) findViewById(R.id.update_product);
        edit_categoryID = (EditText) findViewById(R.id.input_categoryID);
        edit_description = (EditText) findViewById(R.id.input_description);
        edit_image = (EditText) findViewById(R.id.input_image);
        edit_name = (EditText) findViewById(R.id.input_nameProduct);
        edit_image = (EditText) findViewById(R.id.input_image);
        edit_price = (EditText) findViewById(R.id.input_price);
        edit_image1 = (EditText) findViewById(R.id.input_image1);
        edit_image2 = (EditText) findViewById(R.id.input_image2);
        edit_image3 = (EditText) findViewById(R.id.input_image3);
        edit_image4 = (EditText) findViewById(R.id.input_image4);
        setTitle("Edit or Remove Product");
        ProductKey = getIntent().getStringExtra("ProductKey");
        /*Init Firebase*/
        database = FirebaseDatabase.getInstance();
        prodref = database.getReference().child("Product").child(ProductKey);
        readData();

        updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeData();
            }
        });
        cancelProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditProductActivity.this, MainActivity.class);
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
        prodref.setValue(productModelModel);
        Intent i = new Intent(EditProductActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void readData() {
        prodref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentProduct = dataSnapshot.getValue(ProductModel.class);
                edit_categoryID.setText(currentProduct.getCategoryID());
                edit_description.setText(currentProduct.getDescription());
                edit_image.setText(currentProduct.getImage());
                edit_name.setText(currentProduct.getName());
                edit_price.setText(currentProduct.getPrice());
                ImageListModel imageListModel = currentProduct.getImageList();
                edit_image1.setText(imageListModel.getImage1());
                edit_image2.setText(imageListModel.getImage2());
                edit_image3.setText(imageListModel.getImage3());
                edit_image4.setText(imageListModel.getImage4());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
