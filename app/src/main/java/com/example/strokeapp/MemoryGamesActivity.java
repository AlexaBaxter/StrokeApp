package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MemoryGamesActivity extends AppCompatActivity {

    private Button cardButton, homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_games);

        cardButton = (Button) findViewById(R.id.cardB);
        homeButton = (Button) findViewById(R.id.homeB);

        cardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoryGamesActivity.this, CardGameActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoryGamesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}