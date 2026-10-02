package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private Button btnEditProfile;
    private Button btnExpire;
    private Button btnUnits;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnExpire = findViewById(R.id.btnExpire);
        btnUnits = findViewById(R.id.btnUnits);
        btnLogout = findViewById(R.id.btnLogout);

        String username = getIntent().getStringExtra("username");

        btnEditProfile.setOnClickListener(e -> {
            Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
            intent.putExtra("username", username);

            startActivity(intent);
        });

        btnUnits.setOnClickListener(e -> {
            Intent intent = new Intent(SettingsActivity.this, UnitsPreferenceActivity.class);

            startActivity(intent);
        });

        btnLogout.setOnClickListener(e -> {
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            startActivity(intent);

            finishAffinity();
        });
    }
}