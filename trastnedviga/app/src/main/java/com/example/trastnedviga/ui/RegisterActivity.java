package com.example.trastnedviga.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.trastnedviga.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.Button;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button registerButton;
    private TextInputEditText usernameInput, phoneInput, passwordInput;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        registerButton = findViewById(R.id.register_btn);
        usernameInput = findViewById(R.id.register_username_input);
        phoneInput = findViewById(R.id.register_phone_input);
        passwordInput = findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        registerButton.setOnClickListener(v -> CreateAccount());
    }

    private void CreateAccount() {
        String username = usernameInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();

         if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
        }

       else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Введите номер", Toast.LENGTH_SHORT).show();
        }

      else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
        }


      else {
          loadingBar.setTitle("Создание аккаунта");
          loadingBar.setMessage("Пожалуйста, подождите...");
          loadingBar.setCanceledOnTouchOutside(false);
          loadingBar.show();
          ValidatePhone(username,phone,password);
         }
    }

    private void ValidatePhone(final String username, final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone", phone );
                    userDataMap.put("name", username );
                    userDataMap.put("password", password );

                    RootRef.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(task -> {
                                if(task.isSuccessful()) {
                                    loadingBar.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Регистрация прошла успешно.", Toast.LENGTH_SHORT).show();

                                    Intent loginIntent = new Intent(RegisterActivity.this, LoginActiviy.class);
                                    startActivity(loginIntent);
                                }
                                else {
                                    loadingBar.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Ошибка.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Номер" + phone + "уже зарегистрирован", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(RegisterActivity.this, LoginActiviy.class);
                    startActivity(loginIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}