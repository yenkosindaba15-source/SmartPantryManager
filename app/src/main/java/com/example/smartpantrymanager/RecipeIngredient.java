package com.example.smartpantrymanager;

public class RecipeIngredient {
    private int recipeId;
    private String ingredientName;

    public RecipeIngredient(int recipeId, String ingredientName) {
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getIngredientName() {
        return ingredientName;
    }
}