package com.example.deliveryguy;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder>{
    private ArrayList<Request> listData = new ArrayList<>();
    private OrderActivity context;

    public OrderAdapter(ArrayList<Request> listData, OrderActivity c) {
        this.listData = listData;
        this.context = c;
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, int position) {
        holder.orderTime.setText(listData.get(position).getAddress());
        holder.orderStatus.setText(listData.get(position).getStatus());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Toast.makeText(context, listData.get(position).toString(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, DeliveryActivity.class);
                i.putExtra("key", listData.get(position).getAddress());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
