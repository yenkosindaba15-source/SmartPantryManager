package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {
    private TextView RecipeName;
    private TextView Ingredients;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        RecipeName = findViewById(R.id.RecipeName);
        Ingredients = findViewById(R.id.Ingredients);

        databaseHelper = new DatabaseHelper(this);

        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        if (recipe != null) {
            RecipeName.setText(recipe.getName());

            ArrayList<String> ingredients = databaseHelper.getIngredientsForRecipe(recipe.getId());

            StringBuilder builder = new StringBuilder();

            for (String ingredient : ingredients) {
                builder.append("• ").append(ingredient).append("\n");
            }

            Ingredients.setText(builder.toString());
        }
    }
}