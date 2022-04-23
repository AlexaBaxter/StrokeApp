package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class RehabActivity extends AppCompatActivity {
    public ImageButton rehabButton, homeButton, profileButton, calendarButton, physButton, cogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rehab);
        initializeButtons();
    }

    public void initializeButtons()
    {
        homeButton = findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RehabActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        rehabButton = findViewById(R.id.RehabButton);
        rehabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RehabActivity.this, RehabActivity.class);
                startActivity(intent);
            }
        });

        profileButton = findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RehabActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        calendarButton = findViewById(R.id.CalendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RehabActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        physButton = findViewById(R.id.PhysicalButton);
        physButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RehabActivity.this, PhysicalActivity.class);
                startActivity(intent);
            }
        });

        cogButton = findViewById(R.id.CognitiveButton);
        cogButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RehabActivity.this, CognitiveActivity.class);
                startActivity(intent);
            }
        });
    }
}