package com.example.foodapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipesPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipesAdapter adapter;
    private List<Recipe> recipeList;
    private DatabaseReference databaseReference;
    private Button backButtonRecipes;
    private Button addRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String userId = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("recipes");

        recyclerView = findViewById(R.id.recipesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeList = new ArrayList<>();
        adapter = new RecipesAdapter(recipeList);
        recyclerView.setAdapter(adapter);

        backButtonRecipes = findViewById(R.id.backButtonRecipes);
        addRecipeButton = findViewById(R.id.button);

        backButtonRecipes.setOnClickListener(v -> finish());

        addRecipeButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecipesPage.this, AddRecipePage.class);
            startActivity(intent);
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RecipesPage.this, "Failed to load recipes.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

        private List<Recipe> recipeList;

        RecipesAdapter(List<Recipe> recipeList) {
            this.recipeList = recipeList;
        }

        @NonNull
        @Override
        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.recipe_item, parent, false);
            return new RecipeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
            Recipe recipe = recipeList.get(position);
            holder.recipeNameTextView.setText(recipe.getName());
            Glide.with(RecipesPage.this)
                    .load(recipe.getImageUrl())
                    .into(holder.recipeImageView);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(RecipesPage.this, RecipeInformationPage.class);
                intent.putExtra("recipe", recipe);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return recipeList.size();
        }

        class RecipeViewHolder extends RecyclerView.ViewHolder {

            TextView recipeNameTextView;
            ImageView recipeImageView;

            RecipeViewHolder(@NonNull View itemView) {
                super(itemView);
                recipeNameTextView = itemView.findViewById(R.id.recipeNameTextView);
                recipeImageView = itemView.findViewById(R.id.recipeImageView);
            }
        }
    }
}
