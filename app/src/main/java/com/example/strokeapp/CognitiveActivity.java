package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CognitiveActivity extends AppCompatActivity {

    private Button speechButton, memoryButton, locB;
    private ImageButton homeButton, calendarButton, rehabButton, profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognitive);


        homeButton = (ImageButton) findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CognitiveActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        rehabButton = (ImageButton) findViewById(R.id.RehabButton);
        rehabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CognitiveActivity.this, RehabActivity.class);
                startActivity(intent);
            }
        });

        profileButton = (ImageButton) findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CognitiveActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        calendarButton = (ImageButton) findViewById(R.id.CalendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CognitiveActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        speechButton = (Button) findViewById(R.id.speechB);
        speechButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(CognitiveActivity.this, SpeechActivity.class);
                Intent intent = new Intent(CognitiveActivity.this, SpeechSoundActicity.class);
                startActivity(intent);
            }
        });

        memoryButton = (Button) findViewById(R.id.memoryB);
        memoryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CognitiveActivity.this, CardGameActivity.class);
                startActivity(intent);
            }
        });


    }
}