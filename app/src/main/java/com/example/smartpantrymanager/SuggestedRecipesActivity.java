package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerSuggestedRecipes;
    private TextView NoSuggestions;

    private RecipeAdapter recipeAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        recyclerSuggestedRecipes = findViewById(R.id.recyclerSuggestedRecipes);

        NoSuggestions = findViewById(R.id.NoSuggestions);

        databaseHelper = new DatabaseHelper(this);

        recyclerSuggestedRecipes.setLayoutManager(new LinearLayoutManager(this));

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {
        ArrayList<Recipe> suggestedRecipes = databaseHelper.getSuggestedRecipes();

        if (suggestedRecipes.isEmpty()) {
            recyclerSuggestedRecipes.setVisibility(View.GONE);
            NoSuggestions.setVisibility(View.VISIBLE);
        } else {
            recyclerSuggestedRecipes.setVisibility(View.VISIBLE);
            NoSuggestions.setVisibility(View.GONE);

            recipeAdapter = new RecipeAdapter(suggestedRecipes, recipe -> {
                Intent intent = new Intent(SuggestedRecipesActivity.this, RecipeDetailsActivity.class);
                intent.putExtra("recipe", recipe);

                startActivity(intent);});

            recyclerSuggestedRecipes.setAdapter(recipeAdapter);
        }
    }
}