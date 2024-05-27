package com.example.foodapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private Context context;
    private List<IngredientItem> ingredientList;
    private DatabaseReference databaseReference;

    public IngredientsAdapter(Context context, List<IngredientItem> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ingredients");
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        IngredientItem ingredient = ingredientList.get(position);
        holder.nameTextView.setText(ingredient.getName());
        holder.quantityTextView.setText(ingredient.getQuantity());
        holder.measurementTextView.setText(ingredient.getMeasurement());

        holder.deleteButton.setOnClickListener(v -> {
            String ingredientId = ingredient.getId();
            if (ingredientId != null) {
                databaseReference.child(ingredientId).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView quantityTextView;
        TextView measurementTextView;
        Button deleteButton;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewIngredientName);
            quantityTextView = itemView.findViewById(R.id.textViewQuantity);
            measurementTextView = itemView.findViewById(R.id.textViewMeasurement);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
