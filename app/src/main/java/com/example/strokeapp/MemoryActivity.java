package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MemoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        ImageButton homeButton = findViewById(R.id.homeButtonMem);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(MemoryActivity.this, MainActivity.class);
            startActivity(intent);
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