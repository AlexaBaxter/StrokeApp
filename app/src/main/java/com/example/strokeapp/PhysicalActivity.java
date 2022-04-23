package com.example.strokeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class PhysicalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical);

        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PhysicalActivity.this, RehabActivity.class);
            startActivity(intent);
        });

        ImageButton armButton = findViewById(R.id.armButton);
        armButton.setOnClickListener(view -> {
            Intent intent = new Intent(PhysicalActivity.this, ArmActivity.class);
            startActivity(intent);
        });

        ImageButton walkButton = findViewById(R.id.walkingButton);
        walkButton.setOnClickListener(view -> {
            Intent intent = new Intent(PhysicalActivity.this, WalkingActivity.class);
            startActivity(intent);
        });

        ImageButton homeButton = findViewById(R.id.HomeButtonPR);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(PhysicalActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ImageButton rehabButton = findViewById(R.id.RehabButtonPR);
        rehabButton.setOnClickListener(view -> {
            Intent intent = new Intent(PhysicalActivity.this, RehabActivity.class);
            startActivity(intent);
        });

        ImageButton profileButton = findViewById(R.id.ProfileButtonPR);
        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(PhysicalActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        ImageButton calendarButton = findViewById(R.id.CalendarButtonPR);
        calendarButton.setOnClickListener(view -> {
            Intent intent = new Intent(PhysicalActivity.this, CalendarActivity.class);
            startActivity(intent);
        });
    }
}