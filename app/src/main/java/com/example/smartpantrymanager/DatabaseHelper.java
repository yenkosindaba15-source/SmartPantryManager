package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Ingredients constants
    private static final String DATABASE_NAME = "pantry.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_INGREDIENTS = "ingredients";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_UNIT = "unit";

    //Recipe constants
    public static final String TABLE_RECIPES = "recipes";
    public static final String COLUMN_RECIPE_ID = "id";
    public static final String COLUMN_RECIPE_NAME = "name";
    public static final String COLUMN_RECIPE_DESCRIPTION = "description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE " + TABLE_INGREDIENTS + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                        COLUMN_UNIT + " TEXT NOT NULL" +
                        ")";

        db.execSQL(createTable);

        String createRecipesTable = "CREATE TABLE " + TABLE_RECIPES + " (" +
                COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
                COLUMN_RECIPE_DESCRIPTION + " TEXT NOT NULL)";

        db.execSQL(createRecipesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    //create ingredient method
    public long insertIngredient(Ingredient ingredient) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, ingredient.getName());
        values.put(COLUMN_QUANTITY, ingredient.getQuantity());
        values.put(COLUMN_UNIT, ingredient.getUnit());

        return db.insert(TABLE_INGREDIENTS, null, values);
    }

    //read method
    public ArrayList<Ingredient> getAllIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INGREDIENTS, null);

        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                ingredient.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
                ingredient.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT)));

                ingredients.add(ingredient);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return ingredients;
    }

    public long insertRecipe(Recipe recipe){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_NAME, recipe.getName());
        values.put(COLUMN_RECIPE_DESCRIPTION, recipe.getDescription());

        long result = db.insert(TABLE_RECIPES, null, values);

        db.close();
        return result;
    }

    public ArrayList<Recipe> getAllRecipes(){
        ArrayList<Recipe> recipes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECIPES, null);

        if(cursor.moveToFirst()){
            do{
                Recipe recipe = new Recipe();

                recipe.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID)));
                recipe.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_NAME)));
                recipe.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_DESCRIPTION)));

                recipes.add(recipe);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return recipes;
    }

    //update method
    public int updateIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, ingredient.getName());
        values.put(COLUMN_QUANTITY, ingredient.getQuantity());
        values.put(COLUMN_UNIT, ingredient.getUnit());

        return db.update(TABLE_INGREDIENTS, values, COLUMN_ID + "=?", new String[]{String.valueOf(ingredient.getId())});
    }

    //delete method
    public void deleteIngredient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_INGREDIENTS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
}