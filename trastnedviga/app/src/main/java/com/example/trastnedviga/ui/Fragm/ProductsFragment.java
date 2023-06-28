package com.example.trastnedviga.ui.Fragm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trastnedviga.Model.ProductModel;
import com.example.trastnedviga.R;
import com.example.trastnedviga.adapters.ProductsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductsFragment extends Fragment {
    private ScrollView scrollView;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        ArrayList<ProductModel> productModelArrayList = new ArrayList<>();
        ProductsAdapter productsAdapter = new ProductsAdapter(productModelArrayList);
        RecyclerView recyclerProducts = view.findViewById(R.id.products_recycler);
        recyclerProducts.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerProducts.setHasFixedSize(true);
        recyclerProducts.setAdapter(productsAdapter);
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products")
                .child("products_node");


        productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    productsRef.child(snap.getKey()).get().addOnSuccessListener(dataSnapshot -> {
                        ProductModel productModel = dataSnapshot.getValue(ProductModel.class);
                        if (productModel != null && !productModelArrayList.contains(productModel))
                            productModelArrayList.add(productModel);
                        productsAdapter.setProductModelArrayList(productModelArrayList);
                        productsAdapter.notifyDataSetChanged();
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }
}