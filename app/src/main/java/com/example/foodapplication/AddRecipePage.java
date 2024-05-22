package com.example.foodapplication;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.example.foodapplication.RecipesPage.RecipesList;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
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

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class AddRecipePage extends AppCompatActivity {

    // - - - - MENU - - -
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
                Intent mainIntent = new Intent(AddRecipePage.this, MainActivity.class);
                startActivity(mainIntent);
                return true;
            case R.id.item2:
                Intent recipeIntent = new Intent(AddRecipePage.this, IngredientsActivity.class);
                startActivity(recipeIntent);
                return true;
            case R.id.item4:
                Intent settingsIntent = new Intent(AddRecipePage.this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    };

    //FirebaseDatabase database;
    //DatabaseReference recipesRef;
    Button bt_cancelCreateRecipe;
    Button bt_createRecipe;
    Button bt_addIngredient;
    Button bt_pickImage;
    Button bt_removeImage;
    EditText et_recipeName;
    EditText et_preparation;
    EditText et_notes;
    //String recipeName;
    ImageView iw_recipeImagePreview;
    ArrayList<Ingredient> Ingredients;
    ActivityResultLauncher<Intent> imageResultLauncher;
    Boolean ImageSelected = false;
    private static final String[] measurements = new String[]{"-", "unit", "l", "ml", "kg", "g", "mg", "tsp", "tbsp"};

    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //database
        //FirebaseApp.initializeApp(this);
        //database = FirebaseDatabase.getInstance();
        //recipesRef = database.getReference("recipes");

        //ingridientų sąrašas
        Ingredients = new ArrayList<Ingredient>();
        //randami laukai
        et_recipeName = (EditText) findViewById(R.id.recipeNameTextField);
        //randami mygtukai
        bt_cancelCreateRecipe = (Button) findViewById(R.id.cancelCreateRecipeButton);
        bt_createRecipe = (Button) findViewById((R.id.createRecipeButton));
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
        //et_recipeName.addTextChangedListener(InsertRecipeNameTextWatcher);
        bt_createRecipe.setOnClickListener(SaveRecipeOnClick);
        bt_addIngredient.setOnClickListener(AddIngredientField);
        bt_pickImage.setOnClickListener(view -> PickAnImage());
        bt_removeImage.setOnClickListener(view -> RemoveImage());
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
                           Toast.makeText(AddRecipePage.this, "No image is selected", Toast.LENGTH_SHORT).show();
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
/*            if(RecipesList.GetN() != 0)
                RecipesList.Remove(RecipesList.GetN() - 1);*/
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
            String recipeName = et_recipeName.getText().toString();
            String preparationText = et_preparation.getText().toString();
            String notesText = et_notes.getText().toString();
            Bitmap image = ((BitmapDrawable)iw_recipeImagePreview.getDrawable()).getBitmap();

            List<Ingredient> IngredientList = new ArrayList<>();

            LinearLayout ingredientsLayout = (LinearLayout) findViewById((R.id.ingredientLayout));

            //jei repepto pavadinimo laukas tuščias
            if(recipeName.equals(""))
            {
                Toast.makeText(AddRecipePage.this, "Please enter recipe name", Toast.LENGTH_LONG).show();
                return;
            }

            //jei nėra ingridientų laukų
            if(ingredientsLayout.getChildCount() == 0)
            {
                Toast.makeText(AddRecipePage.this, "Please enter information about ingredients", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(AddRecipePage.this, "Please enter ingredient name", Toast.LENGTH_LONG).show();
                    return;
                }

                //jei bent vieno ingrediento dydžio kiekis tuščias
                if(et_ingredientAmount.getText().toString().equals(""))
                {
                    Toast.makeText(AddRecipePage.this, "Please enter ingredient amount", Toast.LENGTH_LONG).show();
                    return;
                }

                //jei bent vieno ingrediento matavimo vienetai nepasirinkti
                if(sp_ingredientMeasurement.getSelectedItem().equals("-"))
                {
                    Toast.makeText(AddRecipePage.this, "Please choose ingredient measurements", Toast.LENGTH_LONG).show();
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

            //jei nepasirinktas paveikslėlis
            if(ImageSelected == false)
            {
                Toast.makeText(AddRecipePage.this, "Please select image", Toast.LENGTH_LONG).show();
                return;
            }

            boolean exist = false;

            for(int j = 0; j < RecipesList.GetN(); j++)
            {
                if(recipeName.equals(RecipesList.GetName(j)))
                {
                    Toast.makeText(AddRecipePage.this, "Such recipe name already exist.",
                            Toast.LENGTH_SHORT).show();
                    exist = true;
                }
            }

            if(exist == false)
            {
                if(IngredientList.size() > 0 && recipeName != null)
                {

                    Recipe recipe = new Recipe();
                    recipe.AddRecipe(recipeName, IngredientList, preparationText, notesText, image);
                    RecipesList.AddRecipe(recipe);

                    //nuvedama i receptų puslapį
                    Intent intent = new Intent(getBaseContext(), RecipesPage.class);
                    startActivity(intent);

                    Toast.makeText(AddRecipePage.this, "Recipe added.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddRecipePage.this, "Failed to add recipe.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    };

/*    TextWatcher InsertRecipeNameTextWatcher = new TextWatcher()
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
            //recipeName = s.toString();
        }
    };*/
}
