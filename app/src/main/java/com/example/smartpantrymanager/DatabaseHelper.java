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
    private static final int DATABASE_VERSION = 5;
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

    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    public static final String COLUMN_FK_RECIPE_ID = "recipe_id";
    public static final String COLUMN_INGREDIENT_NAME = "ingredient_name";

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

        String createRecipeIngredientsTable = "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                COLUMN_FK_RECIPE_ID + " INTEGER, " +
                COLUMN_INGREDIENT_NAME + " TEXT NOT NULL)";

        db.execSQL(createRecipeIngredientsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);

        onCreate(db);
    }

    //create ingredient method
    public long insertIngredient(Ingredient Ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, Ingredient.getName());
        values.put(COLUMN_QUANTITY, Ingredient.getQuantity());
        values.put(COLUMN_UNIT, Ingredient.getUnit());

        return db.insert(TABLE_INGREDIENTS, null, values);
    }

    public long insertRecipeIngredient(RecipeIngredient recipeIngredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_FK_RECIPE_ID, recipeIngredient.getRecipeId());
        values.put(COLUMN_INGREDIENT_NAME, recipeIngredient.getIngredientName());

        long result = db.insert(TABLE_RECIPE_INGREDIENTS, null, values);

        db.close();

        return result;
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

    public ArrayList<String> getIngredientsForRecipe(int recipeId) {
        ArrayList<String> ingredients = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECIPE_INGREDIENTS + " WHERE " + COLUMN_FK_RECIPE_ID + "=?", new String[]{String.valueOf(recipeId)});

        if(cursor.moveToFirst()){
            do{
                ingredients.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENT_NAME)));
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ingredients;
    }

    public boolean canMakeRecipe(int recipeId) {
        ArrayList<String> recipeIngredients = getIngredientsForRecipe(recipeId);
        ArrayList<Ingredient> pantryIngredients = getAllIngredients();

        for(String recipeIngredient : recipeIngredients){
            boolean found = false;

            for(Ingredient pantryIngredient : pantryIngredients){
                if(recipeIngredient.equalsIgnoreCase(
                        pantryIngredient.getName()
                )){
                    found = true;
                    break;
                }
            }

            if(!found){
                return false;
            }
        }

        return true;
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