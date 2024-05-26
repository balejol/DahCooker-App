package com.example.foodapplication;

import static com.example.foodapplication.RecipesPage.RecipesList;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.media.MediaPlayer;

public class EditRecipePage extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private MediaPlayer cancelSFX;
    private MediaPlayer favSFX;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //paleidzia sfx
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        switch (item.getItemId()) {
            case R.id.item1:
                //Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(EditRecipePage.this, MainActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.item2:
                Intent ingredientsIntent = new Intent(EditRecipePage.this, IngredientsActivity.class);
                startActivity(ingredientsIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.myRecipes:
                Intent myRecipesIntent = new Intent(EditRecipePage.this, RecipesPage.class);
                startActivity(myRecipesIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.favoriteRecipes:
                Intent favoriteRecipeIntent = new Intent (EditRecipePage.this, FavoriteRecipesPage.class);
                startActivity(favoriteRecipeIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.item3:
                Intent addRecipeIntent = new Intent(EditRecipePage.this, AddRecipePage.class);
                startActivity(addRecipeIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    };

    Button bt_cancelCreateRecipe;
    Button bt_saveRecipe;
    Button bt_addIngredient;
    Button bt_pickImage;
    Button bt_removeImage;
    EditText et_recipeName;
    EditText et_preparation;
    EditText et_notes;
    ImageView iw_recipeImagePreview;
    ArrayList<Ingredient> Ingredients;
    ActivityResultLauncher<Intent> imageResultLauncher;
    Boolean ImageSelected = false;
    private static final String[] measurements = new String[]{"-", "unit", "l", "ml", "kg", "g", "mg", "tsp", "tbsp"};
    //int idd = 0;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe_page);

        mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);
        cancelSFX = MediaPlayer.create(this, R.raw.cancleclickf);
        favSFX = MediaPlayer.create(this, R.raw.favsound);

        //ingridientų sąrašas
        Ingredients = new ArrayList<Ingredient>();
        //randami laukai
        et_recipeName = (EditText) findViewById(R.id.recipeNameTextField);
        //randami mygtukai
        bt_cancelCreateRecipe = (Button) findViewById(R.id.cancelCreateRecipeButton);
        bt_saveRecipe = (Button) findViewById((R.id.saveRecipeButton));
        bt_addIngredient = (Button) findViewById((R.id.addIngredient));
        bt_pickImage = (Button) findViewById(R.id.pickAnImageButton);
        bt_removeImage = (Button) findViewById((R.id.removeAnImageButton));
        //randami edit text
        et_notes = (EditText) findViewById(R.id.notesField);
        et_preparation = (EditText) findViewById(R.id.preparationField);
        //randamas imageview
        iw_recipeImagePreview = (ImageView) findViewById(R.id.RecipeImage);
        RegisterImageResult();
        //metodai
        bt_cancelCreateRecipe.setOnClickListener(Cancel);
        bt_saveRecipe.setOnClickListener(SaveRecipeOnClick);
        bt_addIngredient.setOnClickListener(AddIngredientField);
        bt_pickImage.setOnClickListener(view -> PickAnImage());
        bt_removeImage.setOnClickListener(view -> RemoveImage());

        id = getIntent().getIntExtra("RecipeId", -1);

        if(id == -1)
        {
            Toast.makeText(EditRecipePage.this, "No recipe was picked", Toast.LENGTH_LONG).show();
            return;
        }

        LoadInformation(id);
    }

    //užkrauna į teksto laukus redaguojamo recepto informaciją
    private void LoadInformation(final int id)
    {
        Recipe recipe = RecipesPage.RecipesList.GetRecipe(id);

        //istatome pavadinima
        et_recipeName.setText(recipe.GetRecipeName());

        //atvaizduojame ivestus ingredientus
        for(int i = 0; i < recipe.GetAmountOfIngredients(); i++)
        {
            AddIngredientField(recipe.GetIngredient(i));
        }

        //ivedame paruosima
        et_preparation.setText(recipe.GetRecipePreparation());
        //ivedame pastabas
        if(!recipe.GetRecipeNotes().equals(""))
            et_notes.setText(recipe.GetRecipeNotes());
        //ivedame fotografiją
        Drawable recipeImageDrawable = new BitmapDrawable(getResources(), recipe.GetRecipeImage());
        iw_recipeImagePreview.setImageDrawable(recipeImageDrawable);
        ImageSelected = true;
        bt_removeImage.setVisibility(View.VISIBLE);
    }

    private void AddIngredientField(Ingredient ing)
    {
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
/*        idd = idd + 1;
        ingName.setId(idd *2);
        ingAmount.setId(idd *3);
        ingMeasurement.setId(idd *4);
        ingRemoveButton.setId(idd);*/

        //ingName
        ingName.setHint("Ingredient");
        ingName.setText(ing.GetName());
        ingName.setGravity(Gravity.CENTER);
        ingName.setWidth(450);
        ingName.setMaxWidth(450);
        ingName.setMaxLines(1);
        ingName.setFilters(new InputFilter[]
                {new InputFilter.LengthFilter(100)});
        oneIngredientLayout.addView(ingName);

        //ingAmount
        ingAmount.setHint("Amt");
        ingAmount.setText(Double.toString(ing.GetAmount()));
        ingAmount.setGravity(Gravity.CENTER);
        ingAmount.setWidth(150);
        ingAmount.setMaxWidth(150);
        ingAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
        ingAmount.setMaxLines(1);
        ingAmount.setFilters(new InputFilter[]
                {new InputFilter.LengthFilter(10)});
        oneIngredientLayout.addView(ingAmount);

        //ingMeasurement
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                EditRecipePage.this,
                android.R.layout.simple_spinner_item,
                measurements);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingMeasurement.setAdapter(adapter);
        int valuePosition = adapter.getPosition(ing.GetMeasurement());
        ingMeasurement.setSelection(valuePosition);
        ingMeasurement.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ingMeasurement.setGravity(Gravity.CENTER);
        ingMeasurement.setLayoutParams(new LinearLayout.LayoutParams(270, LinearLayout.LayoutParams.MATCH_PARENT));
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

    private void RemoveImage(){
        iw_recipeImagePreview.setImageResource(R.drawable.recipe_photo_placeolder);
        bt_removeImage.setVisibility(View.INVISIBLE);
        ImageSelected = false;
    }
    private void PickAnImage() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        imageResultLauncher.launch(intent);
    }
    private void RegisterImageResult(){
        imageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try{
                            Uri imageUri = o.getData().getData();
                            iw_recipeImagePreview.setImageURI(imageUri);
                            bt_removeImage.setVisibility(View.VISIBLE);
                            ImageSelected = true;
                        }
                        catch(Exception e){
                            Toast.makeText(EditRecipePage.this, "No image is selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    View.OnClickListener Cancel = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(getBaseContext(), RecipeInformationPage.class);
            intent.putExtra("RecipeId", id);
            //paleidzia sfx
            if (cancelSFX != null) {
                cancelSFX.start();
            }
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    };

    View.OnClickListener AddIngredientField = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {

            //paleidzia sfx
            if (favSFX != null) {
                favSFX.start();
            }

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
/*            idd = idd + 1;
            ingName.setId(idd *2);
            ingAmount.setId(idd *3);
            ingMeasurement.setId(idd *4);
            ingRemoveButton.setId(idd);*/

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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    EditRecipePage.this,
                    android.R.layout.simple_spinner_item,
                    measurements);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ingMeasurement.setAdapter(adapter);
            ingMeasurement.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            ingMeasurement.setGravity(Gravity.CENTER);
            ingMeasurement.setLayoutParams(new LinearLayout.LayoutParams(270, LinearLayout.LayoutParams.MATCH_PARENT));
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
            //paleidzia sfx
            if (cancelSFX != null) {
                cancelSFX.start();
            }
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
            //paleidzia sfx
            if (favSFX != null) {
                favSFX.start();
            }
            String recipeName = et_recipeName.getText().toString();
            String preparationText = et_preparation.getText().toString();
            String notesText = et_notes.getText().toString();
            Bitmap image = ((BitmapDrawable)iw_recipeImagePreview.getDrawable()).getBitmap();

            List<Ingredient> IngredientList = new ArrayList<>();

            LinearLayout ingredientsLayout = (LinearLayout) findViewById((R.id.ingredientLayout));

            //jei recepto pavadinimo laukas tuščias
            if(recipeName.equals(""))
            {
                Toast.makeText(EditRecipePage.this,
                        "Please enter recipe name", Toast.LENGTH_LONG).show();
                return;
            }

            //jei nėra ingridientų laukų
            if(ingredientsLayout.getChildCount() == 0)
            {
                Toast.makeText(EditRecipePage.this,
                        "Please enter information about ingredients", Toast.LENGTH_LONG).show();
                return;
            }

            //jei yra ingredientų laukai
            for(int i = 0; i < ingredientsLayout.getChildCount(); i++)
            {
                //paimamas layout kuris laiko ingredientų layout'us atitinkamą ingrediento layout'ą
                LinearLayout oneIngredientLayout = (LinearLayout) ingredientsLayout.getChildAt(i);
                //paimamas ingrediento vardas, kiekis ir matavimo vienetus
                EditText et_ingredientName = (EditText) oneIngredientLayout.getChildAt(0);
                EditText et_ingredientAmount = (EditText) oneIngredientLayout.getChildAt(1);
                Spinner sp_ingredientMeasurement = (Spinner) oneIngredientLayout.getChildAt(2);

                //jei bent vieno ingrediento pavadinimo langas tuščias
                if(et_ingredientName.getText().toString().equals(""))
                {
                    Toast.makeText(EditRecipePage.this,
                            "Please enter ingredient name", Toast.LENGTH_LONG).show();
                    return;
                }

                //jei bent vieno ingrediento dydžio kiekis tuščias
                if(et_ingredientAmount.getText().toString().equals(""))
                {
                    Toast.makeText(EditRecipePage.this,
                            "Please enter ingredient amount", Toast.LENGTH_LONG).show();
                    return;
                }

                //jei bent vieno ingrediento matavimo vienetai nepasirinkti
                if(sp_ingredientMeasurement.getSelectedItem().equals("-"))
                {
                    Toast.makeText(EditRecipePage.this,
                            "Please choose ingredient measurements", Toast.LENGTH_LONG).show();
                    return;
                }

                String ingredientName = et_ingredientName.getText().toString();
                String ingredientAmount = et_ingredientAmount.getText().toString();
                String ingredientMeasurement = sp_ingredientMeasurement.getSelectedItem().toString();

                Ingredient temporaryIngredient = new Ingredient(
                        ingredientName,
                        Double.parseDouble(ingredientAmount),
                        ingredientMeasurement);

                IngredientList.add(temporaryIngredient);
            }

            //jei neirasytas paruosimas
            if(preparationText.equals(""))
            {
                Toast.makeText(EditRecipePage.this,
                        "Please enter prepraration section", Toast.LENGTH_LONG).show();
                return;
            }

            //jei nepasirinktas paveikslėlis
            if(ImageSelected == false)
            {
                Toast.makeText(EditRecipePage.this,
                        "Please select image", Toast.LENGTH_LONG).show();
                return;
            }

            if(IngredientList.size() > 0 && recipeName != null)
            {
                Recipe recipe = RecipesPage.RecipesList.GetRecipe(id);
                recipe.ChangeRecipe(recipeName, IngredientList, preparationText, notesText, image);

                Intent intent = new Intent(getBaseContext(), RecipeInformationPage.class);
                intent.putExtra("RecipeId", id);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                Toast.makeText(EditRecipePage.this, "Changed saved.",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(EditRecipePage.this, "Failed to save changes.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };
}