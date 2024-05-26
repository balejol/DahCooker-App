package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.media.MediaPlayer;

public class App_Intro extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mediaPlayer = MediaPlayer.create(this, R.raw.introsfx);

        //paleidzia sfx
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(App_Intro.this, MainActivity.class)); //Pakeisim veliau i reikiama pradini ekrana
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }, 3000);

    }
}