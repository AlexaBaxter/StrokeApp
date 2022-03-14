package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {

    private ImageButton homeButton, calendarButton, rehabButton, profileButton;
    private TextView [] locTV, objTV;
    private EditText [] locET, objET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locTV = new TextView[3];
        objTV = new TextView[3];
        locET = new EditText[3];
        objET = new EditText[3];

        locTV[0] = (TextView) findViewById(R.id.loc1);
        objTV[0] = (TextView) findViewById(R.id.obj1);
        locTV[1] = (TextView) findViewById(R.id.loc2);
        objTV[1] = (TextView) findViewById(R.id.obj2);
        locTV[2] = (TextView) findViewById(R.id.loc3);
        objTV[2] = (TextView) findViewById(R.id.obj3);
        objET[0] = (EditText) findViewById(R.id.objET1);
        locET[0] = (EditText) findViewById(R.id.locET1);
        objET[1] = (EditText) findViewById(R.id.objET2);
        locET[1] = (EditText) findViewById(R.id.locET2);
        objET[2] = (EditText) findViewById(R.id.objET3);
        locET[2] = (EditText) findViewById(R.id.locET3);

        homeButton = (ImageButton) findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        rehabButton = (ImageButton) findViewById(R.id.RehabButton);
        rehabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this, RehabActivity.class);
                startActivity(intent);
            }
        });

        profileButton = (ImageButton) findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        calendarButton = (ImageButton) findViewById(R.id.CalendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }


}