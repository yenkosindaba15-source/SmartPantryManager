package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerRecipes;
    private RecipeAdapter recipeAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);

        recyclerRecipes = findViewById(R.id.recyclerSuggestedRecipes);

        databaseHelper = new DatabaseHelper(this);

        recyclerRecipes.setLayoutManager(new LinearLayoutManager(this));

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {
        ArrayList<Recipe> allRecipes = databaseHelper.getAllRecipes();
        ArrayList<Recipe> suggestedRecipes = new ArrayList<>();

        for(Recipe recipe : allRecipes){
            if(databaseHelper.canMakeRecipe(
                    recipe.getId()
            )){
                suggestedRecipes.add(recipe);
            }
        }

        recipeAdapter = new RecipeAdapter(suggestedRecipes, null);

        recyclerRecipes.setAdapter(recipeAdapter
        );
    }
}