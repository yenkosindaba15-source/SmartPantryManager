package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.*;
import androidx.recyclerview.widget.*;
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
        btnUpdateIngredient = findViewById(R.id.btnUpdateIngredient);
        btnDeleteIngredient = findViewById(R.id.btnDeleteIngredient);
        recyclerIngredients = findViewById(R.id.recyclerIngredients);
        databaseHelper = new DatabaseHelper(this);
        recyclerIngredients.setLayoutManager(new LinearLayoutManager(this));
        btnAddIngredient.setOnClickListener(v -> showAddConfirmation());
        btnUpdateIngredient.setOnClickListener(v -> enterUpdateMode());
        btnDeleteIngredient.setOnClickListener(v -> enterDeleteMode());

        loadIngredients();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateMode = false;
        deleteMode = false;

        loadIngredients();
    }

    private void showAddConfirmation() {
        new AlertDialog.Builder(this).setTitle("Add Ingredient").setMessage("Would you like to add a new ingredient?").setPositiveButton("Add", (dialog, which) -> {
            Intent intent = new Intent(MainActivity.this, AddIngredientsActivity.class);

            startActivity(intent);
        }).setNegativeButton("Cancel", null).show();
    }

    private void enterUpdateMode() {
        updateMode = true;
        deleteMode = false;

        Toast.makeText(this, "Select an ingredient to update", Toast.LENGTH_SHORT).show();

        loadIngredients();
    }

    private void enterDeleteMode() {
        deleteMode = true;
        updateMode = false;

        Toast.makeText(this, "Select an ingredient to delete", Toast.LENGTH_SHORT).show();

        loadIngredients();
    }

    private void loadIngredients() {
        ArrayList<Ingredient> ingredients = databaseHelper.getAllIngredients();
        boolean selectionMode = updateMode || deleteMode;
        adapter = new IngredientAdapter(ingredients, selectionMode, this::handleIngredientSelection);
        recyclerIngredients.setAdapter(adapter);
    }

    private void handleIngredientSelection(Ingredient ingredient) {
        if (updateMode) {
            openUpdateScreen(ingredient);

        } else if (deleteMode) {
            showDeleteConfirmation(ingredient);
        }
    }

    private void openUpdateScreen(Ingredient ingredient) {
        Intent intent = new Intent(MainActivity.this, AddIngredientsActivity.class);
        intent.putExtra("ingredient", ingredient);

        startActivity(intent);
    }

    private void showDeleteConfirmation(Ingredient ingredient) {
        new AlertDialog.Builder(this).setTitle("Delete Ingredient").setMessage("Are you sure you want to delete " + ingredient.getName() + "?").setPositiveButton("Delete", (dialog, which) -> {
            databaseHelper.deleteIngredient(ingredient.getId());
            Toast.makeText(this, ingredient.getName() + " deleted", Toast.LENGTH_SHORT).show();

            deleteMode = false;
            updateMode = false;

            loadIngredients();

        }).setNegativeButton("Cancel", (dialog, which) -> {
            deleteMode = false;
            updateMode = false;
            loadIngredients();
        }).show();
    }
}