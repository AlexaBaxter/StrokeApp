package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SpeechActivity extends AppCompatActivity {

    private Button homeButton, soundButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        homeButton = (Button) findViewById(R.id.homeB);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpeechActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        soundButton = (Button) findViewById(R.id.soundB);
        soundButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpeechActivity.this, SpeechSoundActicity.class);
                startActivity(intent);
            }
        });
    }
}