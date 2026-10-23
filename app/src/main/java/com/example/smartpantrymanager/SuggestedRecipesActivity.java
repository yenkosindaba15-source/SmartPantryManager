package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

public class SuggestedRecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerSuggestedRecipes;
    private TextView NoSuggestions;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        recyclerSuggestedRecipes = findViewById(R.id.recyclerSuggestedRecipes);

        NoSuggestions = findViewById(R.id.NoSuggestions);

        databaseHelper = new DatabaseHelper(this);

        recyclerSuggestedRecipes.setLayoutManager(new LinearLayoutManager(this));
    }
}