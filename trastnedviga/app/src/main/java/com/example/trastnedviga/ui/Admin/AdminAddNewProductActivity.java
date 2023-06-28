package com.example.trastnedviga.ui.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trastnedviga.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rey.material.widget.Button;
import com.rey.material.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String categoryName,
            Description, Price, Pname, saveCurrentDate, saveCurrentTime,
            productRandomKey, typeOfProduct;
    private String downloadImageUrl;
    private ImageView productImage;
    private TextInputEditText productName, productDescription, productPrice,productLoc;
    private Button addNewProductButton;
    private static final int GALLERYPICK = 3;
    private Uri ImageUri;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        init();
        ScrollView scrollView = new ScrollView(this);
        productImage.setOnClickListener(view -> OpenGallery());
        addNewProductButton.setOnClickListener(view -> ValidateProductData());
    }

    private void ValidateProductData() {
        Description = productDescription.getText().toString();
        Price = productPrice.getText().toString();
        Pname = productName.getText().toString();

        if (ImageUri == null) {
            Toast.makeText(this, "Добавьте изображение товара.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Description)) {
            Toast.makeText(this, "Добавьте описание товара.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Price)) {
            Toast.makeText(this, "Добавьте стоимость товара.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Pname)) {
            Toast.makeText(this, "Добавьте название товара.", Toast.LENGTH_SHORT).show();Toast.makeText(this, "Добавьте стоимость товара.", Toast.LENGTH_SHORT).show();
        } else {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {
        loadingBar.setTitle("Загрузка данных");
        loadingBar.setMessage("Пожалуйста, подождите...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ProductImageRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");


        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(e -> {
            String message = e.toString();
            Toast.makeText(AdminAddNewProductActivity.this, "Ошибка: " + message, Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
        }).addOnSuccessListener(taskSnapshot -> {
            Toast.makeText(AdminAddNewProductActivity.this, "Изображение успешно загружено.", Toast.LENGTH_SHORT).show();

            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) throw task.getException();
                downloadImageUrl = filePath.getDownloadUrl().toString();
                return filePath.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    downloadImageUrl = task.getResult().toString();
                    Toast.makeText(AdminAddNewProductActivity.this, "Фото сохранено", Toast.LENGTH_SHORT).show();
                    SaveProductInfoToDatabase();
                }
            });
        });
    }

    private void SaveProductInfoToDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();

        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", categoryName);
        productMap.put("price", Price);
        productMap.put("pname", Pname);


        if (typeOfProduct.equals("product")) {
            ProductsRef.child("products_node").child(productRandomKey).updateChildren(productMap)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this, "Товар добавлен", Toast.LENGTH_SHORT).show();
                            Intent loginIntent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                            startActivity(loginIntent);
                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Ошибка: " + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    });
        } else if (typeOfProduct.equals("rent")) {
            ProductsRef.child("rent_node").child(productRandomKey).updateChildren(productMap)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this, "Товар добавлен", Toast.LENGTH_SHORT).show();
                            Intent loginIntent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                            startActivity(loginIntent);
                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Ошибка: " + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    });
        }
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERYPICK);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERYPICK && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            ImageUri = data.getData();
            productImage.setImageURI(ImageUri);
            productImage.setImageURI(ImageUri);
        }
    }





    private void init() {
        categoryName = getIntent().getStringExtra("category");
        typeOfProduct = getIntent().getStringExtra("type");
        productImage = findViewById(R.id.select_product_image);
        productName = findViewById(R.id.product_name);
        productLoc = findViewById(R.id.product_loc);
        productDescription = findViewById(R.id.product_description);
        productPrice = findViewById(R.id.product_price);
        addNewProductButton = findViewById(R.id.btn_add_new_product);
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        loadingBar = new ProgressDialog(this);
    }
}