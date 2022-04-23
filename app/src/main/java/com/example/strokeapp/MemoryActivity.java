package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MemoryActivity extends AppCompatActivity {

    public ImageButton homeButton, objectButton, locationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        homeButton = (ImageButton) findViewById(R.id.homeButtonMem);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        objectButton = (ImageButton) findViewById(R.id.objectButton);
        objectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoryActivity.this, ObjectIdentifierActivity.class);
                startActivity(intent);
            }
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