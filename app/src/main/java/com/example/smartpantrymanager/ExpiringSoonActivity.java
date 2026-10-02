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

        loadExpiringSoon();
    }

    private void loadExpiringSoon() {
        ArrayList<Ingredient> ingredients = databaseHelper.getExpiringSoonIngredients();

        StringBuilder builder = new StringBuilder();

        if (ingredients.isEmpty()) {
            builder.append("No ingredients are expiring within 7 days.");

        } else {
            for (Ingredient ingredient : ingredients) {
                builder.append(ingredient.getName());
                builder.append("\nExpires: ");
                builder.append(ingredient.getExpiryDate());
                builder.append("\n\n");
            }
        }
        ExpiringSoon.setText(builder.toString());
    }
}