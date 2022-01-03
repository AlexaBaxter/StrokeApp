package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

public class WalkingActivity extends AppCompatActivity {

    private Button homeButton, backButton;
    private CheckBox exc1, exc2, exc3;
    private ImageView star1, star2, star3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);

        homeButton = (Button) findViewById(R.id.HomeButtonW);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalkingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        backButton = (Button) findViewById(R.id.backButtonW);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalkingActivity.this, PhysicalActivity.class);
                startActivity(intent);
            }
        });

        exc1 = (CheckBox)findViewById(R.id.checkBox1);
        exc2 = (CheckBox)findViewById(R.id.checkBox2);
        exc3 = (CheckBox)findViewById(R.id.checkBox3);
        star1 = (ImageView)findViewById(R.id.star1);
        star2 = (ImageView)findViewById(R.id.star2);
        star3 = (ImageView)findViewById(R.id.star3);

        exc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exc1.isChecked())
                    star1.setImageResource(R.drawable.filledstar);
                else
                    star1.setImageResource(R.drawable.emptystar);
            }
        });

        exc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exc2.isChecked())
                    star2.setImageResource(R.drawable.filledstar);
                else
                    star2.setImageResource(R.drawable.emptystar);
            }
        });

        exc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exc3.isChecked())
                    star3.setImageResource(R.drawable.filledstar);
                else
                    star3.setImageResource(R.drawable.emptystar);
            }
        });
    }
}