package com.example.foodapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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