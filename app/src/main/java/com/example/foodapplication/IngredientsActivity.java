package com.example.foodapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IngredientsAdapter adapter;
    private ArrayList<IngredientItem> ingredientItems;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String userId = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("ingredients");

        recyclerView = findViewById(R.id.recyclerViewIngredients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientItems = new ArrayList<>();
        adapter = new IngredientsAdapter(this, ingredientItems);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ingredientItems.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    IngredientItem ingredientItem = postSnapshot.getValue(IngredientItem.class);
                    ingredientItems.add(ingredientItem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(IngredientsActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onAddCustomIngredientClicked(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_custom_ingredient, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final EditText nameEditText = dialogView.findViewById(R.id.editTextIngredientName);
        final EditText quantityEditText = dialogView.findViewById(R.id.editTextIngredientQuantity);
        final Spinner measurementSpinner = dialogView.findViewById(R.id.spinnerIngredientMeasurement);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.measurements_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measurementSpinner.setAdapter(adapter);

        dialogBuilder.setTitle("Add Custom Ingredient");
        dialogBuilder.setPositiveButton("Add", (dialog, which) -> {
            String name = nameEditText.getText().toString().trim();
            String quantity = quantityEditText.getText().toString().trim();
            String measurement = measurementSpinner.getSelectedItem().toString();

            if (name.isEmpty()) {
                Toast.makeText(IngredientsActivity.this, "Please enter an ingredient name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (quantity.isEmpty() || !quantity.matches("\\d+(\\.\\d+)?")) {
                Toast.makeText(IngredientsActivity.this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = databaseReference.push().getKey();
            IngredientItem ingredientItem = new IngredientItem(id, name, quantity, measurement);
            databaseReference.child(id).setValue(ingredientItem);
            Toast.makeText(IngredientsActivity.this, "Ingredient added", Toast.LENGTH_SHORT).show();
        });
        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    public void onBackPressed(View view) {
        // Navigate back to the main activity
        Intent intent = new Intent(IngredientsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
