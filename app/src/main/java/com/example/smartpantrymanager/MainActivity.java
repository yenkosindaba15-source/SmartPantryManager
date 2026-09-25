package com.example.smartpantrymanager;

import android.content.Intent;
import android.health.connect.datatypes.units.Length;
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
    private ArrayList<Ingredient> selectedIngredients = new ArrayList<>();
    private Button btnDeleteSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnUpdateIngredient = findViewById(R.id.btnUpdateIngredient);
        btnDeleteIngredient = findViewById(R.id.btnDeleteIngredient);
        recyclerIngredients = findViewById(R.id.recyclerIngredients);
        btnDeleteSelected = findViewById(R.id.btnDeleteSelected);

        databaseHelper = new DatabaseHelper(this);

        recyclerIngredients.setLayoutManager(new LinearLayoutManager(this));

        btnAddIngredient.setOnClickListener(v -> showAddConfirmation());
        btnUpdateIngredient.setOnClickListener(v -> enterUpdateMode());
        btnDeleteIngredient.setOnClickListener(v -> enterDeleteMode());

        btnDeleteSelected.setOnClickListener(e -> {
            if(selectedIngredients.isEmpty()){
                Toast.makeText(this, "No ingredients selected", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder names = new StringBuilder();

            for(Ingredient ingredient : selectedIngredients){
                names.append(ingredient.getName()).append("\n");
            }
            new AlertDialog.Builder(this).setTitle("Delete Ingredients").setMessage("Are you sure you want to delete:\n\n" + names).setPositiveButton("Delete", (dialog, which) ->{
                for(Ingredient ingredient : selectedIngredients){
                    databaseHelper.deleteIngredient(ingredient.getId());
                }
                selectedIngredients.clear();
                deleteMode = false;
                btnDeleteSelected.setVisibility(Button.GONE);
                loadIngredients();
            }).setNegativeButton("Cancel", null).show();
        });

        loadIngredients();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateMode = false;
        deleteMode = false;

        btnDeleteSelected.setVisibility(Button.GONE);

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

        selectedIngredients.clear();

        btnDeleteSelected.setVisibility(Button.VISIBLE);

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
            return;
        }
        if (deleteMode) {
            if(!selectedIngredients.contains(ingredient)){
                selectedIngredients.remove(ingredient);
            }else{
                selectedIngredients.add(ingredient);
            }
            Toast.makeText(this, selectedIngredients.size() + " selected", Toast.LENGTH_SHORT).show();

            adapter.notifyDataSetChanged();
        }
    }
    private void openUpdateScreen(Ingredient ingredient) {
        Intent intent = new Intent(MainActivity.this, AddIngredientsActivity.class);
        intent.putExtra("ingredient", ingredient);

        startActivity(intent);
    }
}