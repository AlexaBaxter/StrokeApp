package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button cogButton, physButton, memButton, profileButton, calendarButton, mentalButton, emergencyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cogButton = (Button) findViewById(R.id.CogB);
        cogButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CognitiveActivity.class);
                startActivity(intent);
            }
        });

        physButton = (Button) findViewById(R.id.PhysB);
        physButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PhysicalActivity.class);
                startActivity(intent);
            }
        });

        memButton = (Button) findViewById(R.id.MemB);
        memButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MemoryActivity.class);
                startActivity(intent);
            }
        });

        profileButton = (Button) findViewById(R.id.ProfileB);
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        calendarButton = (Button) findViewById(R.id.CalendarB);
        calendarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        mentalButton = (Button) findViewById(R.id.MentalHealthB);
        mentalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MentalActivity.class);
                startActivity(intent);
            }
        });

        emergencyButton = (Button) findViewById(R.id.EmergencyB);
        emergencyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EmergencyActivity.class);
                startActivity(intent);
            }
        });
    }
}