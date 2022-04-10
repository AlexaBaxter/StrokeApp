package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    public ImageButton rehabButton, homeButton, profileButton, calendarButton, mentalButton, emergencyButton, memoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButtons();


    }

    public void initializeButtons()
    {
        homeButton = (ImageButton) findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        rehabButton = (ImageButton) findViewById(R.id.RehabButton);
        rehabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RehabActivity.class);
                startActivity(intent);
            }
        });

        profileButton = (ImageButton) findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        calendarButton = (ImageButton) findViewById(R.id.CalendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        mentalButton = (ImageButton) findViewById(R.id.MentalHealthButton);
        mentalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MentalActivity.class);
                startActivity(intent);
            }
        });

        emergencyButton = (ImageButton) findViewById(R.id.EmergencyButton);
        emergencyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EmergencyActivity.class);
                startActivity(intent);
            }
        });

        memoryButton = (ImageButton) findViewById(R.id.MemoryButton);
        memoryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MemoryActivity.class);
                startActivity(intent);
            }
        });
    }
}