package com.example.smartpantrymanager;

import android.os.Bundle;
import androidx.appcompat.app.*;
import android.widget.*;

public class AddIngredientsActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etQuantity;
    private EditText etUnit;
    private EditText etExpiryDate;
    private Button btnSave;
    private TextView titleHeading;

    private DatabaseHelper databaseHelper;
    private Ingredient selectedIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);

        titleHeading = findViewById(R.id.titleHeading);
        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        btnSave = findViewById(R.id.btnSave);

        databaseHelper = new DatabaseHelper(this);

        if (getIntent().hasExtra("ingredient")) {
            selectedIngredient = (Ingredient) getIntent().getSerializableExtra("ingredient");

            if (selectedIngredient != null) {
                etName.setText(selectedIngredient.getName());
                etQuantity.setText(String.valueOf(selectedIngredient.getQuantity()));
                etUnit.setText(selectedIngredient.getUnit());
                etExpiryDate.setText(selectedIngredient.getExpiryDate());
                titleHeading.setText("Update Ingredient");
                btnSave.setText("Update Ingredient");
            }
        }
        btnSave.setOnClickListener(e -> saveIngredient());
    }
    private void saveIngredient() {
        String name = etName.getText().toString().trim();
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();

        String quantityText = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiryDate = etExpiryDate.getText().toString().trim();

        if (name.isEmpty() || quantityText.isEmpty() || unit.isEmpty() || expiryDate.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(quantityText);

        if (quantity <= 0) {
            Toast.makeText(this, "Quantity must be greater than zero", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedIngredient == null) {
            Ingredient newIngredient = new Ingredient(name, quantity, unit, expiryDate);
            long result = databaseHelper.insertIngredient(newIngredient);

            if (result != -1) {
                Toast.makeText(this, "Ingredient saved", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Ingredient could not be saved", Toast.LENGTH_SHORT).show();
            }

        } else {
            selectedIngredient.setName(name);
            selectedIngredient.setQuantity(quantity);
            selectedIngredient.setUnit(unit);
            selectedIngredient.setExpiryDate(expiryDate);

            int rowsUpdated = databaseHelper.updateIngredient(selectedIngredient);

            if (rowsUpdated > 0) {
                Toast.makeText(this, "Ingredient updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Ingredient could not be updated", Toast.LENGTH_SHORT).show();
            }
        }
    }
}