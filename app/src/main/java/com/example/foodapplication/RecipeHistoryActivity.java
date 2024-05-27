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

public class RecipeHistoryActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private List<Recipe> recipeHistory;
    private TextView tw_recipeName;
    private TextView tw_recipeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_history);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String userId = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("recipes");

        recipeHistory = new ArrayList<>();
        tw_recipeName = findViewById(R.id.tw_recipeName); // Ensure this ID exists in the XML
        tw_recipeDate = findViewById(R.id.tw_recipeDate); // Ensure this ID exists in the XML
        loadRecipeHistory();
    }

    private void loadRecipeHistory() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recipeHistory.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Recipe recipe = postSnapshot.getValue(Recipe.class);
                    if (recipe != null) {
                        recipeHistory.add(recipe);
                    }
                }
                // Update UI with recipe history
                updateRecipeHistoryUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RecipeHistoryActivity.this, "Failed to load recipe history.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecipeHistoryUI() {
        // Update the UI with recipe history
        StringBuilder recipeHistoryText = new StringBuilder();
        for (Recipe recipe : recipeHistory) {
            recipeHistoryText.append(recipe.getName()).append(" - ").append(recipe.getCreationDate()).append("\n");
        }
        tw_recipeName.setText(recipeHistoryText.toString());
    }
}
