package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    private EditText Username;
    private EditText Password;
    private Button btnSaveChanges;

    private DatabaseHelper databaseHelper;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        databaseHelper = new DatabaseHelper(this);

        String username = getIntent().getStringExtra("username");

        currentUser = databaseHelper.getUserByUsername(username);

        if(currentUser != null){
            Username.setText(currentUser.getUsername());
            Password.setText(currentUser.getPassword());
        }
        btnSaveChanges.setOnClickListener(e -> saveChanges());
    }

    private void saveChanges(){
        String username = Username.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        currentUser.setUsername(username);

        currentUser.setPassword(password);

        databaseHelper.updateUser(currentUser);

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

        finish();
    }
}