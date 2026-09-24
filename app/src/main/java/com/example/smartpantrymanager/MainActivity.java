package com.example.smartpantrymanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.*;
import androidx.recyclerview.widget.*;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btnAddIngredient;
    private Button btnUpdateIngredient;
    private Button btnDeleteIngredient;
    private RecyclerView recyclerIngredients;
    private IngredientAdapter adapter;
    private DatabaseHelper databaseHelper;

    private boolean updateMode = false;
    private boolean deleteMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnAddIngredient.setOnClickListener(e -> {
            Intent intent = new Intent(MainActivity.this, AddIngredientsActivity.class);
            startActivity(intent);
        });

        btnUpdateIngredient = findViewById(R.id.btnUpdateIngredient);
        btnUpdateIngredient.setOnClickListener(e -> {
            updateMode = true;
            deleteMode = false;

            Toast.makeText(this, "Select an ingredient to update", Toast.LENGTH_SHORT).show();
        });

        btnDeleteIngredient = findViewById(R.id.btnDeleteIngredient);
        btnDeleteIngredient.setOnClickListener(e -> {
            deleteMode = true;
            updateMode = false;

            Toast.makeText(this, "Select ingredient(s) to delete", Toast.LENGTH_SHORT).show();
        });

        recyclerIngredients = findViewById(R.id.recyclerIngredients);
        recyclerIngredients.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);

        loadIngredients();
    }

    @Override
    protected void onResume(){
        super.onResume();

        loadIngredients();
    }
    private void loadIngredients(){
        ArrayList<Ingredient> ingredients = databaseHelper.getAllIngredients();

        adapter = new IngredientAdapter(this, ingredients, ingredient -> {
            new AlertDialog.Builder(this).setTitle("Delete Ingredient").setMessage("Are you sure you want to delete " + ingredient.getName() + " ?").setPositiveButton("Delete", (dialog, which) -> {
                databaseHelper.deleteIngredient(ingredient.getId());

                loadIngredients();
            }).setNegativeButton("Cancel", null).show();
        });

        recyclerIngredients.setAdapter(adapter);
    }

    public boolean isUpdateMode(){
        return updateMode;
    }
}