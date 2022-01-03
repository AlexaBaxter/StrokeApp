package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PhysicalActivity extends AppCompatActivity {

    Button homeButton, armButton, walkButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical);

        homeButton = (Button) findViewById(R.id.HomeButtonP);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhysicalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        armButton = (Button) findViewById(R.id.armButton);
        armButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhysicalActivity.this, ArmActivity.class);
                startActivity(intent);
            }
        });

        walkButton = (Button) findViewById(R.id.walkingButton);
        walkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhysicalActivity.this, WalkingActivity.class);
                startActivity(intent);
            }
        });
    }
}