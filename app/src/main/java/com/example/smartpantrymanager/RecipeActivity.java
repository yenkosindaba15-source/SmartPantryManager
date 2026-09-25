package com.example.smartpantrymanager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    private RecyclerView recyclerRecipes;
    private RecipeAdapter recipeAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recyclerRecipes = findViewById(R.id.recyclerRecipes);
        databaseHelper = new DatabaseHelper(this);
        recyclerRecipes.setLayoutManager(new LinearLayoutManager(this));

        insertSampleRecipes();

        loadRecipes();
    }

    private void insertSampleRecipes(){
        if(databaseHelper.getAllRecipes().isEmpty()){
            databaseHelper.insertRecipe(new Recipe("Cheese Omlette", "A fluffy omlette made with eggs, cheese and butter."));
            databaseHelper.insertRecipe(new Recipe("Chicken Rice Bowl", "Chicken breast served with rice and onions."));
            databaseHelper.insertRecipe(new Recipe("Tomato Sandwich", "Bread with tomatoes and butter."));
            databaseHelper.insertRecipe(new Recipe("Buttered Toast", "Toasted bread with butter."));
            databaseHelper.insertRecipe(new Recipe("Vegetable Rice", "Rice cooked with onions and tomatoes."));
        }
    }

    private void loadRecipes(){
        ArrayList<Recipe> recipes = databaseHelper.getAllRecipes();

        recipeAdapter = new RecipeAdapter(recipes, recipe -> {
            Intent intent = new Intent(RecipeActivity.this, RecipeDetailsActivity.class);

            intent.putExtra("recipe", recipe);
            startActivity(intent);
        });

        recyclerRecipes.setAdapter(recipeAdapter);
    }
}