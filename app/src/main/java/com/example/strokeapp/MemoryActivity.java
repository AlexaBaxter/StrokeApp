package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MemoryActivity extends AppCompatActivity {

    private ImageButton rehabButton, homeButton, profileButton, calendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        homeButton = findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        rehabButton = findViewById(R.id.RehabButton);
        rehabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoryActivity.this, RehabActivity.class);
                startActivity(intent);
            }
        });

        profileButton = findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoryActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        calendarButton = findViewById(R.id.CalendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoryActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        ImageButton objectButton = findViewById(R.id.objectButton);
        objectButton.setOnClickListener(view -> {
            Intent intent = new Intent(MemoryActivity.this, ObjectIdentifierActivity.class);
            startActivity(intent);
        });

        ImageButton locationButton = findViewById(R.id.locationButton);
        locationButton.setOnClickListener(v -> {
            Intent intent = new Intent(MemoryActivity.this, LocationActivity.class);
            startActivity(intent);
        });

        locationButton = (ImageButton) findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoryActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });
    }
}