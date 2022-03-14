package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WalkingActivity extends AppCompatActivity {


    private CheckBox exc1, exc2, exc3;
    private ImageView star1, star2, star3;

    private TextView levelInstruct, levelTitle;

    private ImageView image1, image2, image3;

    private Button level1Button, level2Button;

    private int level = 1;

    public ImageButton rehabButton, homeButton, profileButton, calendarButton, mentalButton, emergencyButton;

    private String[][] tasks = {{"Knee extensions: Extend one leg until its parallel to the ground. Then, slowly lower your foot back down. Repeat 10 times on each leg (alternating).",
    "Seated marching: Lift leg up to chest, then put it back down. Repeat 10 times on each leg (alternating).",
    "Ankle Stretch: Start with one leg crossed over your other leg. Flex your foot back towards your shin, and hold for 5 seconds. Repeat 5 times on each leg."},
            {"Follow a straight line on the ground and walk forwards. Repeat 5 times.",
            "Facing forward, take steps over sticks on the ground. Make sure to step using alternating feet. Repeat 5 times.",
            "Facing sideways, take steps over sticks on the ground. Repeat 5 times."}       };
    private int[][] images = {{R.drawable.extention, R.drawable.marching, R.drawable.ankle},
            {R.drawable.straight, R.drawable.forward, R.drawable.side}};
    private String[] instructions = {"Stay seated in a chair for all excersizes in level 1.",
    "Use a cane or walking aid if necessary. Have a relative or caregiver next to you for support."};
    private String[] title = {"Level 1", "Level 2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);

       exc1 = (CheckBox)findViewById(R.id.checkBox1);
       exc2 = (CheckBox)findViewById(R.id.checkBox2);
       exc3 = (CheckBox)findViewById(R.id.checkBox3);
       star1 = (ImageView)findViewById(R.id.star1);
       star2 = (ImageView)findViewById(R.id.star2);
       star3 = (ImageView)findViewById(R.id.star3);

       levelInstruct = (TextView)findViewById(R.id.instructionsW);
       levelTitle = (TextView)findViewById(R.id.levelText);

        image1 = (ImageView)findViewById(R.id.imageView1);
        image2 = (ImageView)findViewById(R.id.imageView2);
        image3 = (ImageView)findViewById(R.id.imageView3);

        level1Button = (Button)findViewById(R.id.lv1Button);
        level2Button = (Button)findViewById(R.id.lvl2Button);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        selectLevel(sharedPref.getInt("level",1), sharedPref);

        exc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exc1.isChecked()) {
                    star1.setImageResource(R.drawable.filledstar);
                    editor.putString("level"+level+"star"+1, "true");
                }
                else {
                    star1.setImageResource(R.drawable.emptystar);
                    editor.putString("level"+level+"star"+1, "false");
                }
                editor.commit();
            }
        });

        exc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exc2.isChecked()) {
                    star2.setImageResource(R.drawable.filledstar);
                    editor.putString("level"+level+"star"+2, "true");
                }
                else {
                    star2.setImageResource(R.drawable.emptystar);
                    editor.putString("level"+level+"star"+2, "false");
                }
                editor.commit();
            }
        });

        exc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exc3.isChecked()) {
                    star3.setImageResource(R.drawable.filledstar);
                    editor.putString("level"+level+"star"+3, "true");
                }
                else {
                    star3.setImageResource(R.drawable.emptystar);
                    editor.putString("level"+level+"star"+3, "false");
                }
                editor.commit();
            }
        });

        level1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLevel(1, sharedPref);
                level=1;
            }
        });

        level2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLevel(2, sharedPref);
                level=2;
            }
        });
    }

    private void selectLevel(int level, SharedPreferences sharedPref)
    {

        levelTitle.setText(title[level-1]);
        levelInstruct.setText(instructions[level-1]);
        exc1.setText(tasks[level-1][0]);
        exc2.setText(tasks[level-1][1]);
        exc3.setText(tasks[level-1][2]);
        image1.setImageResource(images[level-1][0]);
        image2.setImageResource(images[level-1][1]);
        image3.setImageResource(images[level-1][2]);
        if(sharedPref.getString("level"+level+"star1","false").equals("false")) {
            star1.setImageResource(R.drawable.emptystar);
            exc1.setChecked(false);
        }
        else {
            star1.setImageResource(R.drawable.filledstar);
            exc1.setChecked(true);
        }
        if(sharedPref.getString("level"+level+"star2","false").equals("false")) {
            star2.setImageResource(R.drawable.emptystar);
            exc2.setChecked(false);
        }
        else {
            star2.setImageResource(R.drawable.filledstar);
            exc2.setChecked(true);
        }
        if(sharedPref.getString("level"+level+"star3","false").equals("false")) {
            star3.setImageResource(R.drawable.emptystar);
            exc3.setChecked(false);
        }
        else {
            star3.setImageResource(R.drawable.filledstar);
            exc3.setChecked(true);
        }
    }
    public void initializeButtons()
    {
        homeButton = (ImageButton) findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalkingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        rehabButton = (ImageButton) findViewById(R.id.RehabButton);
        rehabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalkingActivity.this, RehabActivity.class);
                startActivity(intent);
            }
        });

        profileButton = (ImageButton) findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalkingActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        calendarButton = (ImageButton) findViewById(R.id.CalendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalkingActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        mentalButton = (ImageButton) findViewById(R.id.MentalHealthButton);
        mentalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalkingActivity.this, MentalActivity.class);
                startActivity(intent);
            }
        });

        emergencyButton = (ImageButton) findViewById(R.id.EmergencyButton);
        emergencyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalkingActivity.this, EmergencyActivity.class);
                startActivity(intent);
            }
        });
    }
}