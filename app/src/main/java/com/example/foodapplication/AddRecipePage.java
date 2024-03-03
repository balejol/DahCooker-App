package com.example.foodapplication;

import static com.example.foodapplication.RecipesPage.RecipesList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.widget.EditText;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AddRecipePage extends AppCompatActivity {
    Button cancelCreateRecipe;
    Button createRecipe;
    Button addIngredient;
    EditText recipeNameField;
    String recipeName;
    EditText ingredientName;
    List<EditText> allIngredients;
    LinearLayout ingredientLayout;

    ArrayList<String> Ingredients;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //užrašas
        TextView textView = findViewById(R.id.textView);
        String data = getIntent().getStringExtra("Data");
        textView.setText(data);

        //ingridientų sąrašas
        Ingredients = new ArrayList<String>();
        allIngredients = new ArrayList<EditText>();
        id = 0;

        //randami laukai
        EditText recipeName = (EditText) findViewById(R.id.recipeNameTextField);

        //randami mygtukai
        Button cancelCreateRecipe = (Button) findViewById(R.id.cancelCreateRecipeButton);
        Button createRecipe = (Button) findViewById((R.id.createRecipeButton));
        Button addIngredient = (Button) findViewById((R.id.addIngredient));

        cancelCreateRecipe.setOnClickListener(Cancel);
        recipeName.addTextChangedListener(InsertRecipeNameTextWatcher);
        createRecipe.setOnClickListener(SaveRecipeOnClick);
        addIngredient.setOnClickListener(AddIngredientField);
    }

    View.OnClickListener Cancel = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            RecipesList.Remove(RecipesList.GetN() - 1);
            Intent intent = new Intent(getBaseContext(), RecipesPage.class);
            startActivity(intent);
        }
    };

    View.OnClickListener AddIngredientField = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            LinearLayout ingredientLayout = (LinearLayout) findViewById(R.id.ingredientLayout);
            EditText ingredient = new EditText(getBaseContext());
            id = id + 1;
            ingredient.setId(id);
            allIngredients.add(ingredient);
            ingredient.setHint("Ingredient");
            ingredient.setGravity(Gravity.CENTER);
            ingredientLayout.addView(ingredient);
        }
    };
    View.OnClickListener SaveRecipeOnClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            for(int i=0; i < allIngredients.size(); i++){
                Ingredients.add(allIngredients.get(i).getText().toString());
            }

            Recipe temporaryRecipe = new Recipe();
            temporaryRecipe.AddRecipe(recipeName, Ingredients);
            RecipesList.AddRecipe(temporaryRecipe);
            Intent intent = new Intent(getBaseContext(), RecipesPage.class);
            startActivity(intent);
        }
    };
    TextWatcher InsertRecipeNameTextWatcher = new TextWatcher()
    {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
        }
        @Override
        public void afterTextChanged(Editable s)
        {
            recipeName = s.toString();
        }
    };
}
