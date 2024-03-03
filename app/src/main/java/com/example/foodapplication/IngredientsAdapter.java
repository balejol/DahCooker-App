package com.example.foodapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private ArrayList<IngredientItem> ingredientList;


    public IngredientsAdapter(ArrayList<IngredientItem> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public int getQuantity(int position) {
        return ingredientList.get(position).getQuantity();
    }

    public void updateQuantity(int position, int quantity) {
        IngredientItem item = ingredientList.get(position);
        item.setQuantity(quantity);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientItem ingredientItem = ingredientList.get(position);
        holder.textViewIngredientName.setText(ingredientItem.getIngredientName());
        holder.editTextQuantity.setText(String.valueOf(ingredientItem.getQuantity()));
        holder.editTextQuantity.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {

                int quantity = Integer.parseInt(holder.editTextQuantity.getText().toString());
                updateQuantity(position, quantity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIngredientName;
        EditText editTextQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIngredientName = itemView.findViewById(R.id.textViewIngredientName);
            editTextQuantity = itemView.findViewById(R.id.editTextQuantity);
        }
    }

    public void updateData(ArrayList<IngredientItem> newIngredientList) {
        ingredientList.clear();
        ingredientList.addAll(newIngredientList);
        notifyDataSetChanged();
    }

}