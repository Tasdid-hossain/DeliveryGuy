package com.example.deliveryguy;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView textProductName;
    public ImageView imageViewProduct;
    public TextView textProductPrice;

    private ItemClickListener itemClickListener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        textProductName = (TextView) itemView.findViewById(R.id.product_name);
        imageViewProduct = (ImageView) itemView.findViewById(R.id.product_image);
        textProductPrice = (TextView) itemView.findViewById(R.id.product_price);

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
