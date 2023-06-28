package com.example.trastnedviga.ui.Users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trastnedviga.Model.User;
import com.example.trastnedviga.Prevalent.Prevalent;
import com.example.trastnedviga.R;
import com.example.trastnedviga.ui.LoginActiviy;
import com.example.trastnedviga.ui.RegisterActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button joinButton, loginButton;
    private ProgressDialog loadingBar;
    private com.rey.material.widget.Button btn1, btn2, btn3;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        btn1.setOnClickListener(v -> {
            Toast.makeText(this, "Скоро появится", Toast.LENGTH_SHORT).show();
        });
        btn2.setOnClickListener(v -> {
            Toast.makeText(this, "Скоро появится", Toast.LENGTH_SHORT).show();
        });
        btn3.setOnClickListener(v -> {
            Toast.makeText(this, "Скоро появится", Toast.LENGTH_SHORT).show();
        });



        joinButton = findViewById(R.id.main_join_btn);
        loginButton = findViewById(R.id.main_login_btn);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        loginButton.setOnClickListener(v -> {
            Intent loginIntent = new Intent(MainActivity.this, LoginActiviy.class);
            startActivity(loginIntent);
        });

        joinButton.setOnClickListener(v -> {
            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey != "" && UserPasswordKey != ""){
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey) ){
                ValidateUser(UserPhoneKey,UserPasswordKey);

                loadingBar.setTitle("Вход в приложение");
                loadingBar.setMessage("Пожалуйста, подождите...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }
    }

    private void ValidateUser(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists())
                {
                    User userData = dataSnapshot.child("Users").child(phone).getValue(User.class);

                    if(userData.getPhone().equals(phone))
                    {
                        if(userData.getPassword().equals(password))
                        {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Успешный вход!", Toast.LENGTH_SHORT).show();

                            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                        }
                        else {
                            loadingBar.dismiss();
                        }
                    }
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Аккаунт с номером " + phone + "не существует", Toast.LENGTH_SHORT).show();

                    Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}