package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText Username;
    private EditText Password;

    private Button btnLogin;
    private Button btnSignup;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.Username);

        Password = findViewById(R.id.Password);

        btnLogin = findViewById(R.id.btnLogin);

        btnSignup = findViewById(R.id.btnSignup);

        databaseHelper = new DatabaseHelper(this);

        btnLogin.setOnClickListener(e -> loginUser());

        btnSignup.setOnClickListener(e -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);

            startActivity(intent);
                }
        );
    }

    private void loginUser() {
        String username = Username.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(databaseHelper.loginUser(username, password)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username", username);

            startActivity(intent);

            finish();

        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}