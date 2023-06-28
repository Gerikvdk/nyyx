package com.example.trastnedviga.ui.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.trastnedviga.R;
import com.example.trastnedviga.ui.LoginActiviy;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    private TextView Close;
    private TextView Save;
    private EditText Name;
    private String curretUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Save = findViewById(R.id.save_set);
        Name = findViewById(R.id.settings_full_name);
        Close = findViewById(R.id.close_set);
        Close.setOnClickListener(v -> {
            Intent loginIntent = new Intent(Profile.this, HomeActivity.class);
            startActivity(loginIntent);
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 UpdateInformation();
            }
        }


        );
    }

    private void UpdateInformation() {
        String setname = Name.getText().toString();


        if (TextUtils.isEmpty(setname)) {
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap<String, Object> profilemap = new HashMap<>();

        }
    }
}