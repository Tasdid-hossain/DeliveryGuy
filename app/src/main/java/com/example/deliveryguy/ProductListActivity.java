package com.example.deliveryguy;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class ProductListActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference productRef;
    FirebaseRecyclerAdapter<ProductModel, ProductViewHolder> adapter;
    Query productQuery;
    RecyclerView prod_recycler;
    StaggeredGridLayoutManager mLayoutmanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productQuery = productRef;
        database = FirebaseDatabase.getInstance();
        productRef = database.getReference().child("Product");
        productQuery = productRef;
        mLayoutmanager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        prod_recycler = (RecyclerView) findViewById(R.id.prod_recycler);
        loadProduct();
        prod_recycler.setLayoutManager(new GridLayoutManager(this,2));
        prod_recycler.setAdapter(adapter);

    }

    private void loadProduct() {
        /*This is basically a query that will be included when creating the adapter. Matches the categoryID with the */
        FirebaseRecyclerOptions productOptions = new FirebaseRecyclerOptions.Builder<ProductModel>().setQuery(productQuery,
                ProductModel.class).build();

        /*The firebase recycler adapter. which takes the products depending on the category from the firebase.
         * It creates a recycler view and shows*/
        adapter = new FirebaseRecyclerAdapter<ProductModel, ProductViewHolder>(productOptions) {


            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull ProductModel model) {
                holder.textProductName.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.imageViewProduct);
                holder.textProductPrice.setText(model.getPrice());

                final ProductModel clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(ProductListActivity.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                        showDeleteDataDialog(adapter.getRef(position).getKey());
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
                return new ProductViewHolder(view);
            }
        };
    }

    private void showDeleteDataDialog(final String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductListActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("What do you wanna do?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                productRef.child(key).removeValue();
                /*Query mQuery = category.orderByChild("Name").equalTo(key);
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/
            }
        });
        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(ProductListActivity.this, EditProductActivity.class);
                i.putExtra("ProductKey", key);
                startActivity(i);
            }
        });


        builder.create().show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
