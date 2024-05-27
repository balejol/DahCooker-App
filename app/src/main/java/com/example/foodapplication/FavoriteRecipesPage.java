package com.example.foodapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteRecipesPage extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private List<Recipe> favoriteRecipes;
    private TextView favoriteRecipesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipes_page);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String userId = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("recipes");

        favoriteRecipes = new ArrayList<>();
        favoriteRecipesTextView = findViewById(R.id.favoriteRecipesTextView); // Ensure this ID exists in the XML
        loadFavoriteRecipes();
    }

    private void loadFavoriteRecipes() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoriteRecipes.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Recipe recipe = postSnapshot.getValue(Recipe.class);
                    if (recipe != null && recipe.isFavorite()) {
                        favoriteRecipes.add(recipe);
                    }
                }
                // Update UI with favorite recipes
                updateFavoriteRecipesUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FavoriteRecipesPage.this, "Failed to load favorite recipes.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFavoriteRecipesUI() {
        // Update the UI with favorite recipes
        StringBuilder favoriteRecipesText = new StringBuilder();
        for (Recipe recipe : favoriteRecipes) {
            favoriteRecipesText.append(recipe.getName()).append("\n");
        }
        favoriteRecipesTextView.setText(favoriteRecipesText.toString());
    }
}
