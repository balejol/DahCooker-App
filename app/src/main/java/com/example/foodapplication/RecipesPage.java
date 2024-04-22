package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;
public class RecipesPage extends AppCompatActivity
{
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
                Intent ingredientsIntent = new Intent(RecipesPage.this, IngredientsActivity.class);
                startActivity(ingredientsIntent);
                return true;
            case R.id.item3:
                Intent recipeIntent = new Intent(RecipesPage.this, RecipesPage.class);
                startActivity(recipeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    };

    Button _button;
    Button backButton;

    //Receptų sąrašas
    public static Recipes RecipesList = new Recipes();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        _button = (Button) findViewById(R.id.button);
        backButton = (Button) findViewById(R.id.backButtonRecipes);

        LinearLayout recipeLayout = (LinearLayout) findViewById(R.id.recipeListLayout);

        for(int i = 0; i < RecipesList.GetN(); i++)
        {
            LinearLayout oneRecipeLayout = new LinearLayout(getBaseContext());
            oneRecipeLayout.setOrientation(LinearLayout.VERTICAL);
            oneRecipeLayout.setMinimumWidth(400);
            oneRecipeLayout.setGravity(Gravity.CENTER);
            oneRecipeLayout.setGravity(View.TEXT_ALIGNMENT_CENTER);
            oneRecipeLayout.setBackgroundResource(R.drawable.box_shape_yellow);
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 10, 0, 10);
            oneRecipeLayout.setPadding(30, 30, 30, 30);
            oneRecipeLayout.setLayoutParams(params);

            //recipe name
            TextView tw_recipeName = new TextView(getBaseContext());
            tw_recipeName.setText(RecipesList.GetName(i));
            tw_recipeName.setTextSize(30);
            tw_recipeName.setTextColor(Color.BLACK);
            tw_recipeName.setWidth(450);
            tw_recipeName.setMaxWidth(450);
            oneRecipeLayout.addView(tw_recipeName);

            //"ingredients:"
            TextView tw_text = new TextView(getBaseContext());
            tw_text.setGravity(Gravity.CENTER);
            tw_text.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tw_text.setText("Ingredients:");
            tw_text.setTextSize(20);
            tw_text.setTextColor(Color.BLACK);
            oneRecipeLayout.addView(tw_text);

            //ingredient list
            TextView tw_ingredients = new TextView(getBaseContext());
            tw_ingredients.setTextColor(Color.BLACK);
            String temporaryIngredientsList = "";
            if(RecipesList.GetRecipe(i).GetAmountOfIngredients() == 0)
            {
                temporaryIngredientsList = "There are no ingredients \n";
            }
            for(int k = 0; k < RecipesList.GetRecipe(i).GetAmountOfIngredients(); k++)
            {
                temporaryIngredientsList = temporaryIngredientsList + "- " +
                        RecipesList.GetRecipe(i).GetIngredient(k).GetName() + " " +
                        RecipesList.GetRecipe(i).GetIngredient(k).GetAmount() + " " +
                        RecipesList.GetRecipe(i).GetIngredient(k).GetMeasurement() +"\n";
            }
            tw_ingredients.setText(temporaryIngredientsList);
            oneRecipeLayout.addView(tw_ingredients);

            //remove button
            Button ingRemoveButton = new Button(getBaseContext());
            ingRemoveButton.setOnClickListener(RemoveRecipe);
            ingRemoveButton.setBackgroundColor(Color.rgb(255, 127, 127));
            ingRemoveButton.setText("REMOVE");
            ingRemoveButton.setTextColor(Color.WHITE);
            ingRemoveButton.setGravity(Gravity.CENTER);
            ingRemoveButton.setLayoutParams(new LinearLayout.LayoutParams(300, 100));
            ingRemoveButton.setBackgroundResource(R.drawable.button_color);
            oneRecipeLayout.addView(ingRemoveButton);

            //favorite button
            Button ingFavouriteButton = new Button(getBaseContext());
            ingFavouriteButton.setOnClickListener(FavouriteRecipe);
            ingFavouriteButton.setBackgroundColor(Color.rgb(255, 127, 127));
            ingFavouriteButton.setText("FAVOURITE");
            ingFavouriteButton.setTextColor(Color.WHITE);
            ingFavouriteButton.setGravity(Gravity.CENTER);
            ingFavouriteButton.setLayoutParams(new LinearLayout.LayoutParams(300, 100));
            ingFavouriteButton.setBackgroundResource(R.drawable.button_color);
            oneRecipeLayout.addView(ingFavouriteButton);

            recipeLayout.addView(oneRecipeLayout);
        }

/*        for(int i = 0; i < RecipesList.GetN(); i++) {
            TextView recipeTitle = new TextView(this);
            recipeTitle.setText("RECIPE - " + RecipesList.GetName(i));
            recipeTitle.setGravity(Gravity.CENTER);
            recipeLayout.addView(recipeTitle);

            for (int j = 0; j < RecipesList.GetRecipe(i).GetAmountOfIngredients(); j++)
            {
                TextView ingredients = new TextView(this);
                ingredients.setText(RecipesList.GetRecipe(i).GetIngredient(j) + "\n");
                ingredients.setGravity(Gravity.LEFT);
                recipeLayout.addView(ingredients);
            }
        }*/

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        _button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), AddRecipePage.class);
                //intent.putExtra("Data", "Please enter information about a recipe");
                startActivity(intent);
            }
        });
    }
    View.OnClickListener RemoveRecipe = new View.OnClickListener()
    {

        @Override
        public void onClick(View btt)
        {
            AlertDialog dialog = new AlertDialog.Builder(RecipesPage.this)
                    .setTitle("Confirmation")
                    .setMessage("Are you sure you want to remove this recipe?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Toast.makeText(RecipesPage.this, "Recipe removed", Toast.LENGTH_SHORT).show();
                            LinearLayout recipes = (LinearLayout) findViewById((R.id.recipeListLayout));
                            LinearLayout recipeLine = (LinearLayout) btt.getParent();
                            recipes.removeView(recipeLine);
                            TextView name = (TextView) recipeLine.getChildAt(0);
                            String removeName = (String) name.getText();

                            for (int i = 0; i < RecipesList.GetN(); i++) {
                                if (RecipesList.GetRecipe(i).GetRecipeName() == removeName) {
                                    RecipesList.Remove(i);
                                }
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.button_color);
        }

    };

    View.OnClickListener FavouriteRecipe = new View.OnClickListener() {

        @Override
        public void onClick(View btt) {
            LinearLayout recipes = (LinearLayout) findViewById((R.id.recipeListLayout));
            LinearLayout recipeLine = (LinearLayout) btt.getParent();

            if(btt.getBackground().getConstantState().
                    equals(ContextCompat.getDrawable(RecipesPage.this, R.drawable.button_color).getConstantState()))
            {
                recipes.removeView(recipeLine);
                recipes.addView(recipeLine, 0);
                btt.setBackgroundColor(Color.MAGENTA);
            }
            else
            {
                recipes.removeView(recipeLine);
                recipes.addView(recipeLine, recipes.getChildCount());
                btt.setBackgroundResource(R.drawable.button_color);
            }
        }
    };
}