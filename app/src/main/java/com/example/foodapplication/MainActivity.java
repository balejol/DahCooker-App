package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecipesPage.class);
                startActivity(intent);
            }
        });

        // Find the "Ingredients" button by its ID
        Button buttonIngredients = findViewById(R.id.button_ingredients);

        // Set an OnClickListener to the button
        buttonIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to IngredientsActivity
                Intent intent = new Intent(MainActivity.this, IngredientsActivity.class);
                // Start IngredientsActivity
                startActivity(intent);
            }
        });


    }
}
