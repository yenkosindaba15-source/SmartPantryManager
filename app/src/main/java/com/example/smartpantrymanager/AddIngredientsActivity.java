package com.example.smartpantrymanager;

import android.os.Bundle;
import androidx.appcompat.app.*;
import android.widget.*;

public class AddIngredientsActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etQuantity;
    private EditText etUnit;
    private Button btnSave;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);

        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        btnSave = findViewById(R.id.btnSave);

        databaseHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(e -> {
            String name = etName.getText().toString().trim();
            String quantityText = etQuantity.getText().toString().trim();
            String unit = etUnit.getText().toString().trim();

            if(name.isEmpty() || quantityText.isEmpty() || unit.isEmpty()){
                Toast.makeText(AddIngredientsActivity.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                return;
            }
            int quantity = Integer.parseInt(quantityText);

            Ingredient ingredient = new Ingredient(name, quantity, unit);
            databaseHelper.insertIngredient(ingredient);

            Toast.makeText(AddIngredientsActivity.this, "Ingredient saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}