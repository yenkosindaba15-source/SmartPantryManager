package com.example.smartpantrymanager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.widget.TextView;

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
            databaseHelper.insertRecipe(new Recipe("Cheese Omlette", "A fluffy omlette made with eggs, cheese and butter.",
                    "1. Beat the eggs.\n" +
                            "2. Add grated cheese.\n" +
                            "3. Melt the butter in a pan\n" +
                            "4. Pour the butter in the egg mixture\n" +
                            "5. Cook until golden\n" +
                            "6. Serve warm." ));

            databaseHelper.insertRecipe(new Recipe("Chicken Rice Bowl", "Chicken breast served with rice and onions.",
                    "1. Cook the rice.\n" +
                            "2. Fry the onions.\n" +
                            "3. Fry the chicken breast into the onions\n" +
                            "4. Slice the chicken breast\n" +
                            "5. Serve over rice"));

            databaseHelper.insertRecipe(new Recipe("Tomato Sandwich", "Bread with tomatoes and butter.",
                    "1. Spread butter to the bread.\n" +
                            "2. Slice the tomatoes.\n" +
                            "3. Put the tomatoes on the bread\n" +
                            "4. Close the sandwich\n" +
                            "5. Serve."));

            databaseHelper.insertRecipe(new Recipe("Buttered Toast", "Toasted bread with butter.",
                    "1. Toast the bread.\n" +
                            "2. Spread butter on the toast.\n" +
                            "3. Serve."));

            databaseHelper.insertRecipe(new Recipe("Vegetable Rice", "Rice cooked with onions and tomatoes.",
                    "1. Cook the rice\n" +
                            "2. Chop onions and tomatoes.\n" +
                            "3. Fry the onions until soft.\n" +
                            "4. Add tomatoes to the onions.\n" +
                            "5. Mix them with rice.\n" +
                            "6. Serve."));

            databaseHelper.insertRecipe(new Recipe("Egg Sandwich", "A sandwich made with eggs and bread.",
                    "1. Fry the eggs.\n" +
                            "2. Toast the bread.\n" +
                            "3. Place the eggs between the bread.\n" +
                            "4. Serve."));

            databaseHelper.insertRecipe(new Recipe("Cheese Toast", "Toast topped with melted cheese.",
                    "1. Toast the bread.\n" +
                            "2. Add cheese on top.\n" +
                            "3. Allow the cheese to melt.\n" +
                            "4. Serve."));

            databaseHelper.insertRecipe(new Recipe("Fried Rice", "Rice fried with onions.",
                    "1. Fry the rice.\n" +
                            "2. Slice the onions then fry them\n" +
                            "3. Add rice.\n" +
                            "4. Stir and serve."));

            databaseHelper.insertRecipe(new Recipe("Chicken Sandwich", "Bread served with chicken breast.",
                    "1. Cook the chicken.\n" +
                            "2. Slice the chicken.\n" +
                            "3. Place on bread.\n" +
                            "4. Serve."));

            databaseHelper.insertRecipe(new Recipe("Tuna Mayo Sandwich", "A sandwich filled with tuna and mayonnaise.",
                    "1. Drain the tin fish.\n" +
                            "2. Mix with mayonnaise.\n" +
                            "3. Spread onto bread.\n" +
                            "4. Close the sandwich.\n" +
                            "5. Serve."));

            databaseHelper.insertRecipe(new Recipe("Macaroni And Cheese", "Creamy macaroni with melted cheese.",
                    "1. Boil macaroni.\n" +
                            "2. Drain water when its ready.\n" +
                            "3. Add grated cheese and milk.\n" +
                            "4. Stir until melted.\n" +
                            "5. Serve hot."));

            databaseHelper.insertRecipe(new Recipe("Creamy Potato Salad", "Potatoes mixed with mayonnaise.",
                            "1. Boil the potatoes.\n" +
                                    "2. Allow them to cool after boiled.\n" +
                                    "3. Add mayonnaise on the cooled potatoes.\n" +
                                    "4. Mix thoroughly.\n" +
                                    "5. Serve chilled."));

            databaseHelper.insertRecipe(new Recipe("Macaroni Salad", "Cold macaroni mixed with vegetables and mayonnaise.",
                    "1. Boil the macaroni.\n" +
                            "2. Chop tomatoes and onions.\n" +
                            "3. Mix with mayonnaise.\n" +
                            "4. Chill and serve."));

            databaseHelper.insertRecipe(new Recipe("Chicken Macaroni", "Chicken mixed with cooked macaroni.",
                    "1. Cook the macaroni.\n" +
                            "2. Cook the chicken breast.\n" +
                            "3. Cut the chicken breast into dices.\n" +
                            "4. Mix it with macaroni.\n" +
                            "5. Serve hot."));

            databaseHelper.insertRecipe(new Recipe("Savoury Mince And Rice", "Seasoned mince served over rice.",
                    "1. Cook the rice.\n" +
                            "2. Fry onions until soft.\n" +
                            "3. Add mince to the onions and cook thoroughly.\n" +
                            "4. Stir in tomatoes.\n" +
                            "5. Serve over rice."));

            databaseHelper.insertRecipe(new Recipe("Sausage And Potato Fry", "Fried sausages served with crispy potatoes.",
                    "1. Peel and slice the potatoes.\n" +
                            "2. Fry until golden.\n" +
                            "3. Fry sausages thoroughly.\n" +
                            "4. Combine and serve."));

            databaseHelper.insertRecipe(new Recipe("Vegetable Macaroni", "Macaroni mixed with fresh vegetables.",
                    "1. Boil the macaroni.\n" +
                            "2. Chop tomatoes, onions and green peppers.\n" +
                            "3. Fry vegetables lightly.\n" +
                            "4. Mix with macaroni.\n" +
                            "5. Serve warm."));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(1, "Eggs"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(1, "Cheese"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(1, "Butter"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(2, "Chicken Breast"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(2, "Rice"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(2, "Onions"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(3, "Bread"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(3, "Tomatoes"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(3, "Butter"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(4, "Bread"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(4, "Butter"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(5, "Rice"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(5, "Onions"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(5, "Tomatoes"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(6, "Eggs"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(6, "Bread"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(7, "Bread"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(7, "Cheese"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(8, "Rice"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(8, "Onions"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(9, "Chicken Breast"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(9, "Bread"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(10, "Tin Fish"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(10, "Mayonnaise"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(10, "Bread"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(11, "Macaroni"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(11, "Cheese"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(11, "Milk"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(12, "Potatoes"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(12, "Mayonnaise"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(13, "Macaroni"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(13, "Tomatoes"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(13, "Onions"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(13, "Mayonnaise"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(14, "Chicken Breast"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(14, "Macaroni"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(15, "Mince"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(15, "Rice"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(15, "Onions"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(15, "Tomatoes"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(16, "Sausages"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(16, "Potatoes"));

            databaseHelper.insertRecipeIngredient(new RecipeIngredient(17, "Macaroni"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(17, "Green Peppers"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(17, "Onions"));
            databaseHelper.insertRecipeIngredient(new RecipeIngredient(17, "Tomatoes"));
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