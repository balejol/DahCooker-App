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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.widget.EditText;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AddRecipePage extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Intent ingredientsIntent = new Intent(AddRecipePage.this, IngredientsActivity.class);
                startActivity(ingredientsIntent);
                return true;
            case R.id.item3:
                Intent recipeIntent = new Intent(AddRecipePage.this, RecipesPage.class);
                startActivity(recipeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    };

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
            if(RecipesList.GetN() != 0)
                RecipesList.Remove(RecipesList.GetN() - 1);
            Intent intent = new Intent(getBaseContext(), RecipesPage.class);
            startActivity(intent);
        }
    };

    View.OnClickListener AddIngredientField = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            LinearLayout ingredientLayout = (LinearLayout)
                    findViewById(R.id.ingredientLayout);
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
