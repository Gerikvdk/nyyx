package com.example.trastnedviga.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trastnedviga.Model.RentModel;
import com.example.trastnedviga.R;
import com.example.trastnedviga.ui.Users.ProductActivity;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class RentAdapter extends RecyclerView.Adapter<RentAdapter.ProductsViewHolder> {

    private DatabaseReference rentsRef;
    private ArrayList<RentModel> rentsModelsArrayList;
    private Context context;

    public RentAdapter(ArrayList<RentModel> rentModelArrayList) {
        this.rentsModelsArrayList = rentModelArrayList;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ProductsViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rent_item, parent, false)
        );
    }

    public void setProductModelArrayList(ArrayList<RentModel> rentModelsArrayList) {
        this.rentsModelsArrayList = rentModelsArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        RentModel rentModel = rentsModelsArrayList.get(position);
        Glide.with(context)
                .asBitmap()
                .load(rentModel.getRefImage())
                .dontAnimate()
                .placeholder(R.drawable.menu)
                .into(holder.itemImage);
        holder.productescription.setText(rentModel.getRname());
        holder.lodprise.setText(rentModel.getPrice());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("rent", rentModel);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return rentsModelsArrayList.size();
    }

    public static class ProductsViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView productescription;
        TextView lodprise;


        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.rent_img);
            productescription = itemView.findViewById(R.id.rent_description);
            lodprise = itemView.findViewById(R.id.price_log);
        }
    }
}
