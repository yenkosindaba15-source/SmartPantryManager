package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ExpiringSoonActivity extends AppCompatActivity {
    private TextView ExpiringSoon;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expiring_soon);

        ExpiringSoon = findViewById(R.id.ExpiringSoon);

        databaseHelper = new DatabaseHelper(this);

        loadExpiringSoonIngredients();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExpiringSoonIngredients();
    }

    private void loadExpiringSoonIngredients() {
        ArrayList<Ingredient> ingredients = databaseHelper.getExpiringSoonIngredients();

        if (ingredients.isEmpty()) {
            ExpiringSoon.setText("No ingredients are expiring within the next 7 days.");
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (Ingredient ingredient : ingredients) {
            builder.append(ingredient.getName());
            builder.append("\nExpires: ");
            builder.append(ingredient.getExpiryDate());
            builder.append("\n\n");
        }
        ExpiringSoon.setText(builder.toString().trim());
    }
}