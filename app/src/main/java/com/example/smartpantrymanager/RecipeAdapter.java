package com.example.smartpantrymanager;

import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private final ArrayList<Recipe> recipes;

    public RecipeAdapter(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView RecipeName;
        TextView RecipeDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            RecipeName = itemView.findViewById(R.id.RecipeName);
            RecipeDescription = itemView.findViewById(R.id.RecipeDescription);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        holder.RecipeName.setText(recipe.getName());
        holder.RecipeDescription.setText(recipe.getDescription());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}