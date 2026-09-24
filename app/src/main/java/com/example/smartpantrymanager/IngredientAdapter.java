package com.example.smartpantrymanager;

import android.view.*;
import android.widget.TextView;
import android.content.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

interface OnIngredientLongClickListener{
    void onLongClick(Ingredient ingredient);
}
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private ArrayList<Ingredient> ingredients;
    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients, OnIngredientLongClickListener listener) {
        this.context = context;
        this.ingredients = ingredients;
        this.listener = listener;
    }
    private Context context;
    private OnIngredientLongClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView IngredientName;
        TextView IngredientDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            IngredientName = itemView.findViewById(R.id.IngredientName);
            IngredientDetails = itemView.findViewById(R.id.IngredientDetails);
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

        holder.itemView.setOnClickListener(e -> {
            if(context instanceof MainActivity){
                MainActivity mainActivity = (MainActivity) context;

                if(!mainActivity.isUpdateMode()){
                    return;
                }
            }
            Intent intent = new Intent(context, AddIngredientsActivity.class);
            intent.putExtra("ingredient", ingredient);
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(e -> {
            listener.onLongClick(ingredient);
            return true;
        });
    }
    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}