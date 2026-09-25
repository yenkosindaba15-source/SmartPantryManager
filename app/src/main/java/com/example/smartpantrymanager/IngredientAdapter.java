package com.example.smartpantrymanager;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    public interface OnIngredientSelectedListener {
        void onIngredientSelected(Ingredient ingredient);
    }

    private ArrayList<Ingredient> selectedIngredients;
    private final ArrayList<Ingredient> ingredients;
    private final boolean selectionMode;
    private final OnIngredientSelectedListener listener;

    public IngredientAdapter(ArrayList<Ingredient> ingredients, boolean selectionMode, OnIngredientSelectedListener listener) {
        this.ingredients = ingredients;
        this.selectionMode = selectionMode;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView IngredientName;
        TextView IngredientDetails;
        CheckBox checkIngredient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            IngredientName = itemView.findViewById(R.id.IngredientName);
            IngredientDetails = itemView.findViewById(R.id.IngredientDetails);
            checkIngredient = itemView.findViewById(R.id.checkIngredient);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.IngredientName.setText(ingredient.getName());
        holder.IngredientDetails.setText(ingredient.getQuantity() + " " + ingredient.getUnit());

        if (selectionMode) {
            holder.checkIngredient.setVisibility(View.VISIBLE);
        } else {
            holder.checkIngredient.setVisibility(View.GONE);
        }

        holder.checkIngredient.setChecked(selectedIngredients.contains(ingredient));

        holder.itemView.setOnClickListener(v -> {
            if (!selectionMode) {
                return;
            }

            holder.checkIngredient.setChecked(true);

            if (listener != null) {
                listener.onIngredientSelected(ingredient);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
