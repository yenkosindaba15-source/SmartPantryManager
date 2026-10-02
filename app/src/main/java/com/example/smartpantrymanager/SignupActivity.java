package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText Username;
    private EditText Email;
    private EditText Password;

    private Button btnSignup;
    private Button btnGoToLogin;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Username = findViewById(R.id.Username);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);

        btnSignup = findViewById(R.id.btnSignup);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);

        databaseHelper = new DatabaseHelper(this);

        btnSignup.setOnClickListener(e -> signUp());

        btnGoToLogin.setOnClickListener(e -> finish());
    }

    private void signUp() {
        String username = Username.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(databaseHelper.usernameExists(username)) {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(username, email, password);

        databaseHelper.insertUser(user);

        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();

        finish();
    }
}