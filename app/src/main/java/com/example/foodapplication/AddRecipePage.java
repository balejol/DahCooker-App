package com.example.foodapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddRecipePage extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText recipeNameTextField;
    private EditText preparationField;
    private EditText notesField;
    private LinearLayout ingredientLayout;
    private Button addIngredientButton;
    private Button createRecipeButton;
    private Button cancelButton;
    private Button pickAnImageButton;
    private ImageView recipeImageView;
    private DatabaseReference databaseReference;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String userId = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("recipes");

        recipeNameTextField = findViewById(R.id.recipeNameTextField);
        preparationField = findViewById(R.id.preparationField);
        notesField = findViewById(R.id.notesField);
        ingredientLayout = findViewById(R.id.ingredientLayout);
        addIngredientButton = findViewById(R.id.addIngredient);
        createRecipeButton = findViewById(R.id.createRecipeButton);
        cancelButton = findViewById(R.id.cancelButton);
        pickAnImageButton = findViewById(R.id.pickAnImageButton);
        recipeImageView = findViewById(R.id.RecipeImage);

        addIngredientButton.setOnClickListener(v -> addIngredientField());
        createRecipeButton.setOnClickListener(v -> createRecipe());
        cancelButton.setOnClickListener(v -> finish());
        pickAnImageButton.setOnClickListener(v -> openFileChooser());
    }

    private void addIngredientField() {
        View ingredientView = getLayoutInflater().inflate(R.layout.ingredient_entry, null);

        Spinner measurementSpinner = ingredientView.findViewById(R.id.ingredientMeasurementSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.measurements_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measurementSpinner.setAdapter(adapter);

        ingredientLayout.addView(ingredientView);
    }

    private void createRecipe() {
        String name = recipeNameTextField.getText().toString().trim();
        String preparation = preparationField.getText().toString().trim();
        String notes = notesField.getText().toString().trim();

        List<Recipe.Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientLayout.getChildCount(); i++) {
            View ingredientView = ingredientLayout.getChildAt(i);
            EditText ingredientNameField = ingredientView.findViewById(R.id.ingredientNameField);
            EditText ingredientQuantityField = ingredientView.findViewById(R.id.ingredientQuantityField);
            Spinner ingredientMeasurementSpinner = ingredientView.findViewById(R.id.ingredientMeasurementSpinner);

            String ingredientName = ingredientNameField.getText().toString().trim();
            String ingredientQuantity = ingredientQuantityField.getText().toString().trim();
            String ingredientMeasurement = ingredientMeasurementSpinner.getSelectedItem().toString();

            if (!ingredientName.isEmpty() && !ingredientQuantity.isEmpty() && !ingredientMeasurement.isEmpty()) {
                ingredients.add(new Recipe.Ingredient(ingredientName, ingredientQuantity, ingredientMeasurement));
            }
        }

        if (name.isEmpty() || preparation.isEmpty() || ingredients.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.push().getKey();
        Recipe recipe = new Recipe(id, name, preparation, ingredients, false, imageUri != null ? imageUri.toString() : "", notes, "");
        databaseReference.child(id).setValue(recipe);

        Toast.makeText(this, "Recipe created successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                recipeImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
