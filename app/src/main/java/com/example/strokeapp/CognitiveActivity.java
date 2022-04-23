package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class CognitiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognitive);


        ImageButton homeButton = (ImageButton) findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(CognitiveActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ImageButton rehabButton = (ImageButton) findViewById(R.id.RehabButton);
        rehabButton.setOnClickListener(view -> {
            Intent intent = new Intent(CognitiveActivity.this, RehabActivity.class);
            startActivity(intent);
        });

        ImageButton profileButton = (ImageButton) findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(CognitiveActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        ImageButton calendarButton = (ImageButton) findViewById(R.id.CalendarButton);
        calendarButton.setOnClickListener(view -> {
            Intent intent = new Intent(CognitiveActivity.this, CalendarActivity.class);
            startActivity(intent);
        });

        ImageButton speechButton = findViewById(R.id.speechB);
        speechButton.setOnClickListener(view -> {
            Intent intent = new Intent(CognitiveActivity.this, SpeechSoundActivity.class);
            startActivity(intent);
        });

        ImageButton memoryButton = findViewById(R.id.memoryB);
        memoryButton.setOnClickListener(view -> {
            Intent intent = new Intent(CognitiveActivity.this, CardGameActivity.class);
            startActivity(intent);
        });
    }
}