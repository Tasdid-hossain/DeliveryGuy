package com.example.deliveryguy;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView orderTime, orderStatus;
    private ItemClickListener itemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        orderTime = (TextView) itemView.findViewById(R.id.order_time);
        orderStatus = (TextView) itemView.findViewById(R.id.order_status);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(itemView, getAdapterPosition(), false);
    }
}

