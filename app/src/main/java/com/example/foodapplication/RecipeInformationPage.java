package com.example.foodapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeInformationPage extends AppCompatActivity {

    private TextView tv_RecipeName, tv_Preparation, tv_Notes;
    private ImageView iv_RecipeImage;
    private Recipe currentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_information_page);

        // Initialize UI elements
        tv_RecipeName = findViewById(R.id.tv_RecipeName);
        iv_RecipeImage = findViewById(R.id.iv_RecipeImage);
        tv_Preparation = findViewById(R.id.tv_Preparation);
        tv_Notes = findViewById(R.id.tv_Notes);

        // Get the intent and recipe data
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("recipe")) {
            currentRecipe = intent.getParcelableExtra("recipe");
            displayRecipeDetails();
        }

        // Set up button click listeners (these methods should be defined below)
        Button btn_Back = findViewById(R.id.btn_Back);
        Button btn_Edit = findViewById(R.id.btn_Edit);
        btn_Back.setOnClickListener(this::onBackButtonClicked);
        btn_Edit.setOnClickListener(this::onEditButtonClicked);
    }

    public void onBackButtonClicked(View view) {
        finish();
    }

    public void onEditButtonClicked(View view) {
        Intent editIntent = new Intent(RecipeInformationPage.this, EditRecipePage.class);
        editIntent.putExtra("recipe", currentRecipe);
        startActivity(editIntent);
    }

    private void displayRecipeDetails() {
        if (currentRecipe != null) {
            tv_RecipeName.setText(currentRecipe.getName());
            tv_Preparation.setText(currentRecipe.getPreparation());
            tv_Notes.setText(currentRecipe.getNotes());

            String imageUrl = currentRecipe.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(iv_RecipeImage);
            } else {
                iv_RecipeImage.setImageResource(R.drawable.recipe_photo_placeholder);
            }

            // Display ingredients
            displayIngredients();
        }
    }

    private void displayIngredients() {
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
