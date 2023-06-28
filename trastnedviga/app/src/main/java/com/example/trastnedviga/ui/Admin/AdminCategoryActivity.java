package com.example.trastnedviga.ui.Admin;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.trastnedviga.Model.LoadingDialog;
import com.example.trastnedviga.R;
import com.example.trastnedviga.ui.Users.HomeActivity;

public class AdminCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView redact;
    private ImageView perehod;
    private CardView  adm_card3,adm_card4,adm_card1,adm_card2,adm_card5,adm_card6,adm_card7;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadingDialog loadingDialog = new LoadingDialog(AdminCategoryActivity.this);

        setContentView(R.layout.activity_admin_category);

        CardView adm_card3;
        CardView adm_card4;
        CardView adm_card1;
        CardView adm_card2;
        CardView adm_card5;
        CardView adm_card6;
        CardView adm_card7;

        adm_card3 = (CardView) findViewById(R.id.adm_card3);
        adm_card4 = (CardView) findViewById(R.id.adm_card4);
        adm_card1 = (CardView) findViewById(R.id.adm_card1);
        adm_card2 = (CardView) findViewById(R.id.adm_card2);
        adm_card5 = (CardView) findViewById(R.id.adm_card5);
        adm_card6 = (CardView) findViewById(R.id.adm_card6);
        adm_card7 = (CardView) findViewById(R.id.adm_card7);

        adm_card3.setOnClickListener(this);
        adm_card6.setOnClickListener(this);
        adm_card4.setOnClickListener(this);
        adm_card5.setOnClickListener(this);
        adm_card7.setOnClickListener(this);


        redact = (findViewById(R.id.redact));


        redact.setOnClickListener(view -> showTypeOfRealtyDialog());


        perehod = findViewById(R.id.r_btn);

        adm_card2.setOnClickListener(v -> {
            LoadingDialog.startLoadingDialog();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.dissmissDialog();
                }
            }, 3000);
            Intent loginIntent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
            startActivity(loginIntent);

        });



        adm_card1.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
            intent.putExtra("category", "edit_info");
            startActivity(intent);
        });


    }




    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.adm_card3:
                intent = new Intent();
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/gerik.vdk?utm_medium=copy_link"));
                startActivity(intent);
                break;
            case R.id.adm_card6:
                intent = new Intent();
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/14OB1FTvI-p2UNwYQ0364cpIlOETlpB6H/view?usp=sharing"));
                startActivity(intent);
                break;
            case R.id.adm_card4:
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"dimaakulichd6618@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Есть предложение по улучшению приложения.");
                email.putExtra(Intent.EXTRA_TEXT, "Здравствуйте!");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Выберите почту:"));
                break;
                case R.id.adm_card5:
                    String url = "https://docs.google.com/spreadsheets/d/1OPmTMrbkzeKIFp_dl7C4dZYkZIMRiKLJriXQ6owBa8A/edit?resourcekey#gid=151998047";
                    intent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse(url));
                    startActivity(intent);
                break;
                case R.id.adm_card7:
                     intent = getPackageManager().getLaunchIntentForPackage("com.example.realestateappraisal");
                    startActivity(intent);;
                break;
        }

    }

    private void showTypeOfRealtyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this)
                .inflate(R.layout.layout_delete_note,
                        findViewById(R.id.layout_delete_note_container));
        builder.setView(view);
        AlertDialog dialogTypeOfRealty = builder.create();
        if (dialogTypeOfRealty.getWindow() != null) dialogTypeOfRealty.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        view.findViewById(R.id.text_type_of_product).setOnClickListener(view12 -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
            intent.putExtra("category", "redact");
            intent.putExtra("type", "product");
            startActivity(intent);
        });
        view.findViewById(R.id.text_type_of_rent).setOnClickListener(view12 -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
            intent.putExtra("category", "redact");
            intent.putExtra("type", "rent");
            startActivity(intent);
        });
        view.findViewById(R.id.text_cancel).setOnClickListener(view1 -> dialogTypeOfRealty.dismiss());
        dialogTypeOfRealty.show();

    }



}