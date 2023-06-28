package com.example.trastnedviga.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trastnedviga.Model.ProductModel;
import com.example.trastnedviga.R;
import com.example.trastnedviga.ui.Users.ProductActivity;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    private ArrayList<ProductModel> productModelArrayList;
    private Context context;

    public ProductsAdapter(ArrayList<ProductModel> productModelArrayList) {
        this.productModelArrayList = productModelArrayList;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ProductsViewHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_item, parent, false)
        );
    }

    public void setProductModelArrayList(ArrayList<ProductModel> productModelArrayList) {
        this.productModelArrayList = productModelArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        ProductModel productModel = productModelArrayList.get(position);
        Glide.with(context)
                .asBitmap()
                .load(productModel.getImage())
                .dontAnimate()
                .placeholder(R.drawable.menu)
                .into(holder.itemImage);
        holder.productDescription.setText(productModel.getPname());
        holder.lopprise.setText(productModel.getPrice());
        Log.d("TAGCHECKREFINADAPTER", "onCreate:    " + productModel.getImage());
        Log.d("TAGCHECKREFINADAPTERMODEL", "onCreate:    " + productModel.getPname());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("product", productModel);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productModelArrayList.size();
    }

    public static class ProductsViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView productDescription;
        TextView lopprise;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.product_img);
            productDescription = itemView.findViewById(R.id.product_description);
            lopprise = itemView.findViewById(R.id.price_og);
        }
    }
}
