package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {
    private TextView RecipeName;
    private TextView Ingredients;
    private TextView RecipeMethod;
    private TextView SuggestedAdditionHeading;
    private TextView SuggestedAddition;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        RecipeName = findViewById(R.id.RecipeName);
        Ingredients = findViewById(R.id.Ingredients);
        RecipeMethod = findViewById(R.id.RecipeMethod);
        SuggestedAdditionHeading = findViewById(R.id.SuggestedAdditionHeading);
        SuggestedAddition = findViewById(R.id.SuggestedAddition);

        databaseHelper = new DatabaseHelper(this);

        String suggestedExtra = getIntent().getStringExtra("suggestedExtra");
        if(suggestedExtra != null && !suggestedExtra.isEmpty()){
            SuggestedAdditionHeading.setVisibility(View.VISIBLE);
            SuggestedAddition.setVisibility(View.VISIBLE);
            SuggestedAddition.setText(suggestedExtra);
        }

        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        if (recipe != null) {
            RecipeName.setText(recipe.getName());

            ArrayList<String> ingredients = databaseHelper.getIngredientsForRecipe(recipe.getId());

            StringBuilder builder = new StringBuilder();

            for (String ingredient : ingredients) {
                if(databaseHelper.ingredientExists(ingredient)){
                    builder.append("✓ ").append(ingredient).append("\n");
                }else{
                    builder.append("✖ ").append(ingredient).append("\n");
                }
            }

            Ingredients.setText(builder.toString());
            RecipeMethod.setText(recipe.getMethod());
        }
    }
}