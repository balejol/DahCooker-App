package com.example.foodapplication;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.example.foodapplication.RecipesPage.RecipesList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
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
    String recipeName;
    List<EditText> allIngredients;
    ArrayList<Ingredient> Ingredients;
    private static final String[] measurements = new String[]{"vnt.", "l", "ml", "g", "mg", "a.š.", "v.š."};

    Spinner DropDownMeasurements;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //ingridientų sąrašas
        Ingredients = new ArrayList<Ingredient>();
        //randami laukai
        EditText recipeName = (EditText) findViewById(R.id.recipeNameTextField);
        //randami mygtukai
        cancelCreateRecipe = (Button) findViewById(R.id.cancelCreateRecipeButton);
        createRecipe = (Button) findViewById((R.id.createRecipeButton));
        addIngredient = (Button) findViewById((R.id.addIngredient));
        //metodai
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
            //HorizontalScrollView ingredientLayoutScrollable = (HorizontalScrollView)
            //        findViewById(R.id.ingredientLayoutScroll);

            //visu ingredientu sarasas
            LinearLayout ingredientLayout = (LinearLayout)
                    findViewById(R.id.ingredientLayout);
            //vieno ingrediento duomenų eilute
            LinearLayout oneIngredientLayout = new LinearLayout(getBaseContext());
            oneIngredientLayout.setOrientation(LinearLayout.HORIZONTAL);
            oneIngredientLayout.setMinimumWidth(400);
            oneIngredientLayout.setGravity(Gravity.CENTER);

            EditText ingName = new EditText(getBaseContext()); //ingrediento vardas
            EditText ingAmount = new EditText(getBaseContext()); //ingrediento kiekis
            Spinner ingMeasurement = new Spinner(getBaseContext()); //ingrediento matmuo
            Button ingRemoveButton = new Button(getBaseContext());
            id = id + 1;
            ingName.setId(id*2);
            ingAmount.setId(id*3);
            ingMeasurement.setId(id*4);
            ingRemoveButton.setId(id);

            //allIngredients.add(ingredient);
            //ingName
            ingName.setHint("Ingredient");
            ingName.setGravity(Gravity.CENTER);
            ingName.setWidth(450);
            ingName.setMaxWidth(450);
            ingName.setMaxLines(1);
            ingName.setFilters(new InputFilter[]
                    {new InputFilter.LengthFilter(100)});
            oneIngredientLayout.addView(ingName);

            //ingAmount
            ingAmount.setHint("Amt");
            ingAmount.setGravity(Gravity.CENTER);
            ingAmount.setWidth(150);
            ingAmount.setMaxWidth(150);
            ingAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
            ingAmount.setMaxLines(1);
            ingAmount.setFilters(new InputFilter[]
                    {new InputFilter.LengthFilter(10)});
            oneIngredientLayout.addView(ingAmount);

            //ingMeasurement
            ArrayAdapter<String>adapter = new ArrayAdapter<String>(
                    AddRecipePage.this,
                    android.R.layout.simple_spinner_item,
                    measurements);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ingMeasurement.setAdapter(adapter);
            ingMeasurement.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            ingMeasurement.setLayoutParams(new LinearLayout.LayoutParams(250, 50));
            //ingMeasurement.setMinimumHeight(MATCH_PARENT);
            oneIngredientLayout.addView(ingMeasurement);

            //ingRemoveButton
            ingRemoveButton.setOnClickListener(RemoveIngredient);
            ingRemoveButton.setBackgroundColor(Color.rgb(255, 127, 127));
            ingRemoveButton.setText("X");
            ingRemoveButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            ingRemoveButton.setBackgroundResource(R.drawable.button_color);
            oneIngredientLayout.addView(ingRemoveButton);

            //pridedama ingrediento eilute
            ingredientLayout.addView(oneIngredientLayout);
        }
    };
    View.OnClickListener RemoveIngredient = new View.OnClickListener()
    {
        @Override
        public void onClick(View btt)
        {
            int buttonId = btt.getId();
            LinearLayout ingredients = (LinearLayout) findViewById((R.id.ingredientLayout));
            LinearLayout ingredientLine = (LinearLayout) btt.getParent();
            ingredients.removeView(ingredientLine);
        }
    };
    View.OnClickListener SaveRecipeOnClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
/*            for(int i=0; i < allIngredients.size(); i++){
                Ingredients.add(allIngredients.get(i).getText().toString());
            }

            Recipe temporaryRecipe = new Recipe();
            temporaryRecipe.AddRecipe(recipeName, Ingredients);
            RecipesList.AddRecipe(temporaryRecipe);
            Intent intent = new Intent(getBaseContext(), RecipesPage.class);
            startActivity(intent);*/
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
