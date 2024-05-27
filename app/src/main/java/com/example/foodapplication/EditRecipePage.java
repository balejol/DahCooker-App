package com.example.foodapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class EditRecipePage extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 1001;
    private EditText recipeNameTextField, preparationField, notesField;
    private ImageView iv_RecipeImage;
    private Recipe currentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe_page);

        // Initialize UI elements
        recipeNameTextField = findViewById(R.id.recipeNameTextField);
        preparationField = findViewById(R.id.preparationField);
        notesField = findViewById(R.id.notesField);
        iv_RecipeImage = findViewById(R.id.RecipeImage);
        Button btn_Cancel = findViewById(R.id.cancelCreateRecipeButton);
        Button btn_Save = findViewById(R.id.saveRecipeButton);

        // Check for permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        } else {
            loadRecipeDetails();
        }

        // Set up button click listeners
        btn_Cancel.setOnClickListener(v -> finish());
        btn_Save.setOnClickListener(v -> {
            // Save recipe logic
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadRecipeDetails();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadRecipeDetails() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("recipe")) {
            currentRecipe = intent.getParcelableExtra("recipe");
            if (currentRecipe != null) {
                recipeNameTextField.setText(currentRecipe.getName());
                preparationField.setText(currentRecipe.getPreparation());
                notesField.setText(currentRecipe.getNotes());

                // Handle image loading with permissions check
                String imageUrl = currentRecipe.getImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    try {
                        Uri imageUri = Uri.parse(imageUrl);
                        // Granting URI permission
                        getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        iv_RecipeImage.setImageURI(imageUri);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Cannot load image. Permission required.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    iv_RecipeImage.setImageResource(R.drawable.recipe_photo_placeholder);
                }

                // Load ingredients
                loadIngredients();
            }
        }
    }

    private void loadIngredients() {
        List<Recipe.Ingredient> ingredients = currentRecipe.getIngredients();
        LinearLayout ingredientsLayout = findViewById(R.id.ingredientLayout);

        for (Recipe.Ingredient ingredient : ingredients) {
            TextView textView = new TextView(this);
            textView.setText(ingredient.getName() + ": " + ingredient.getQuantity());
            textView.setPadding(10, 10, 10, 10);
            ingredientsLayout.addView(textView);
        }
    }
}
