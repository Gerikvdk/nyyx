package com.example.trastnedviga.ui.Fragm;

import android.os.Bundle;
import android.util.Log;
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
import com.example.trastnedviga.Model.RentModel;
import com.example.trastnedviga.R;
import com.example.trastnedviga.adapters.RentAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RentFragment extends Fragment {
    private ScrollView scrollView;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rent, container, false);
        ArrayList<RentModel> rentModelArrayList = new ArrayList<>();
        RentAdapter rentAdapter = new RentAdapter(rentModelArrayList);
        RecyclerView recyclerProducts = view.findViewById(R.id.rents_recycler);
        recyclerProducts.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerProducts.setHasFixedSize(true);
        recyclerProducts.setAdapter(rentAdapter);
        DatabaseReference rentsRef = FirebaseDatabase.getInstance().getReference().child("Products")
                .child("rent_node");

        rentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    rentsRef.child(snap.getKey()).get().addOnSuccessListener(dataSnapshot -> {
                        RentModel rentModel = dataSnapshot.getValue(RentModel.class);
                        if (rentModel != null && !rentModelArrayList.contains(rentModel))
                            rentModelArrayList.add(rentModel);
                        rentAdapter.setProductModelArrayList(rentModelArrayList);
                        rentAdapter.notifyDataSetChanged();
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