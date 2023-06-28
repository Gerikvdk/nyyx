package com.example.trastnedviga.ui.Users;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.bumptech.glide.Glide;
import com.example.trastnedviga.Model.ProductModel;
import com.example.trastnedviga.Model.RentModel;
import com.example.trastnedviga.R;

import io.paperdb.Paper;

public class ProductActivity extends AppCompatActivity {

    private TextView titleTv, descriptionTv, costTv;
    private ImageView productRentImg;
    private ProductModel productModel = null;
    private RentModel rentModel = null;
    private ScrollView scrollView;
    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar toolbar;
    private Button contprod;
    private ImageButton btnnax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ScrollView scrollView = new ScrollView(this);
        if (getIntent().hasExtra("product"))
            productModel = (ProductModel) getIntent().getSerializableExtra("product");
        else if (getIntent().hasExtra("rent"))
            rentModel = (RentModel) getIntent().getSerializableExtra("rent");



        contprod = findViewById(R.id.contprod);
        contprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent loginIntent = new Intent(ProductActivity.this, ContactActivity.class);
                startActivity(loginIntent);
            }
        });

        btnnax = findViewById(R.id.btnnax);
        btnnax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent loginIntent = new Intent(ProductActivity.this, HomeActivity.class);
                startActivity(loginIntent);
            }
        });





        titleTv = findViewById(R.id.title_of_product_rent);
        descriptionTv = findViewById(R.id.description_of_product_rent);
        costTv = findViewById(R.id.cost_of_product_rent);
        productRentImg = findViewById(R.id.product_rent_img);

        if (rentModel != null) {
            titleTv.setText(rentModel.getRname());
            descriptionTv.setText(rentModel.getDescription());
            costTv.setText(rentModel.getPrice());
            Glide.with(this)
                    .asBitmap()
                    .load(rentModel.getRefImage())
                    .dontAnimate()
                    .placeholder(R.drawable.menu)
                    .into(productRentImg);
            Log.d("TAGCHECKREF", "onCreate:    " + rentModel.getRefImage());
        } else if (productModel != null) {
            titleTv.setText(productModel.getPname());
            descriptionTv.setText(productModel.getDescription());
            costTv.setText(productModel.getPrice());
            Glide.with(this)
                    .asBitmap()
                    .load(productModel.getImage())
                    .dontAnimate()
                    .placeholder(R.drawable.menu)
                    .into(productRentImg);
            Log.d("TAGCHECKREF", "onCreate:    " + productModel.getImage());
        }
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }


}