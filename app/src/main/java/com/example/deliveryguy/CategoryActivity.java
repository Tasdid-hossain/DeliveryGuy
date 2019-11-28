package com.example.deliveryguy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class CategoryActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference category;
    Query categoryQuery;

    RecyclerView recycler_categories;
    GridLayoutManager layoutManager;
    FirebaseRecyclerAdapter<CategoryModel, CategoryViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setTitle("Edit or Remove Category");
        /*Init Firebase*/
        database = FirebaseDatabase.getInstance();
        category = database.getReference().child("Category");
        categoryQuery = category.orderByKey();

        /*Load category*/
        recycler_categories = (RecyclerView) findViewById(R.id.recycler_categories);
        layoutManager = new GridLayoutManager(this,2);
        recycler_categories.setHasFixedSize(true);
        recycler_categories.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        loadCategory();

        recycler_categories.setAdapter(adapter);

    }

    /*Create firebaserecycleroptions and firebase recycler adapter. Then load that into recyclerview*/
    private void loadCategory(){
        FirebaseRecyclerOptions categoryOptions = new FirebaseRecyclerOptions.Builder<CategoryModel>().setQuery(categoryQuery, CategoryModel.class).build();

        adapter = new FirebaseRecyclerAdapter<CategoryModel, CategoryViewHolder>(categoryOptions) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull final CategoryModel model) {
                holder.textCategoryName.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.imageViewCategory);

                final CategoryModel clickItem = model;

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        /*Get CategoryID and send to ProductActivity*/
                        /*Intent productIntent = new Intent(CategoryActivity.this, ProductListActivity.class);
                        productIntent.putExtra("CategoryID", adapter.getRef(position).getKey().toString());*/
                        Toast.makeText(CategoryActivity.this, ""+adapter.getRef(position).getKey(), Toast.LENGTH_SHORT).show();
                        showDeleteDataDialog(adapter.getRef(position).getKey());
                        //startActivity(productIntent);
                    }
                });
            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
                return new CategoryViewHolder(view);
            }

        };
    }

    private void showDeleteDataDialog(final String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("What do you wanna do?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                category.child(key).removeValue();
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
                Intent i = new Intent(CategoryActivity.this, EditCategoryActivity.class);
                i.putExtra("CategoryID", key);
                startActivity(i);
            }
        });


        builder.create().show();
    }

    /*Must include on start and on stop for firebaserecycleradapter*/
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
