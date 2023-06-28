package com.example.trastnedviga.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trastnedviga.Model.User;
import com.example.trastnedviga.Prevalent.Prevalent;
import com.example.trastnedviga.R;
import com.example.trastnedviga.ui.Admin.AdminCategoryActivity;
import com.example.trastnedviga.ui.Users.HomeActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActiviy extends AppCompatActivity {
    private Button loginButton;
    private TextInputEditText usernameInput, loginPhoneInput, loginPasswordInput;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink;
    private String parentDbName = "Users";
    private CheckBox checkBoxRememberMe;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activiy);



        loginButton = findViewById(R.id.join_btn);
        loginPhoneInput = findViewById(R.id.login_phone_input);
        loginPasswordInput = findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);
        checkBoxRememberMe = findViewById(R.id.login_checkbox);
        Paper.init(this);

        AdminLink = findViewById(R.id.admin_panel_link);
        NotAdminLink = findViewById(R.id.not_admin_panel_link);
        loginButton.setOnClickListener(v -> loginUser());

        AdminLink.setOnClickListener(v -> {
            AdminLink.setVisibility(View.INVISIBLE);
            NotAdminLink.setVisibility(View.VISIBLE);
            loginButton.setText("Вход для админа");
            parentDbName = "Admins";
        });

        NotAdminLink.setOnClickListener(v -> {
            AdminLink.setVisibility(View.VISIBLE);
            NotAdminLink.setVisibility(View.INVISIBLE);
            loginButton.setText("Войти");
            parentDbName = "Users";
        });
    }

    private void loginUser() {
        String phone = loginPhoneInput.getText().toString();
        String password = loginPasswordInput.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Введите номер", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Вход в приложение");
            loadingBar.setMessage("Пожалуйста, подождите...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateUser(phone, password);
        }
    }

    private void ValidateUser(final String phone, final String password) {
        if (checkBoxRememberMe.isChecked()) {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()) {
                    User userData = dataSnapshot.child(parentDbName).child(phone).getValue(User.class);
                    if (userData.getPhone().equals(phone)) {
                        if (userData.getPassword().equals(password)) {
                            if (parentDbName.equals("Users")) {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActiviy.this, "Успешный вход!", Toast.LENGTH_SHORT).show();

                                Intent homeIntent = new Intent(LoginActiviy.this, HomeActivity.class);
                                startActivity(homeIntent);
                            } else if (parentDbName.equals("Admins")) {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActiviy.this, "Успешный вход!", Toast.LENGTH_SHORT).show();

                                Intent homeIntent = new Intent(LoginActiviy.this, AdminCategoryActivity.class);
                               startActivity(homeIntent);
                            }
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActiviy.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActiviy.this, "  Аккаунт с номером  " + phone + "  не существует  ", Toast.LENGTH_SHORT).show();
                    Intent registerIntent = new Intent(LoginActiviy.this, RegisterActivity.class);
                    startActivity(registerIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
