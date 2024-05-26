package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    // - - - - MENU - - -
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
            // iš Settings į Main
            case R.id.item1:
                Intent mainIntent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš Settings į Ingredients
            case R.id.item2:
                Intent ingredientsIntent = new Intent(SettingsActivity.this, IngredientsActivity.class);
                startActivity(ingredientsIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš Settings į Add recipe
            case R.id.item3:
                Intent recipeIntent = new Intent(SettingsActivity.this, AddRecipePage.class);
                startActivity(recipeIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš Settings į My recipes
            case R.id.item4:
                Intent myrecipeIntent = new Intent(SettingsActivity.this, RecipesPage.class);
                startActivity(myrecipeIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš Settings į Favourite recipes
            case R.id.item5:
                Intent favouriteIntent = new Intent(SettingsActivity.this, FavoriteRecipesPage.class);
                startActivity(favouriteIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    };

    private MediaPlayer mediaPlayer;
    private MusicService musicService;
    private boolean isServiceBound = false;

    private SharedPreferences sharedPreferences; // SharedPreferences object

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) iBinder;
            musicService = binder.getService();
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceBound = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize MediaPlayer with your sound file
        mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Bind to MusicService
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        Switch musicSwitch = findViewById(R.id.switch1);

        // Retrieve the saved state of the switch
        musicSwitch.setChecked(sharedPreferences.getBoolean("music_switch_state", true));
        //musicSwitch.setChecked(true);

        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Save the state of the switch
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("music_switch_state", isChecked);
                editor.apply();

                //paleidzia sfx
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }

                if (isChecked)
                {
                    if (musicService != null) {
                        musicService.startMusic();
                    }
                }
                else
                {
                    if (musicService != null) {
                        musicService.stopMusic();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
        // Release the MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}