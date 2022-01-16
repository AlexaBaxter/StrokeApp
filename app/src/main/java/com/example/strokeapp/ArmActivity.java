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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ArmActivity extends AppCompatActivity {


    private CheckBox exc1, exc2, exc3;
    private ImageView star1, star2, star3;

    private TextView levelInstruct, levelTitle;

    private ImageView image1, image2, image3;

    private Button level1Button, level2Button, homeButton;

    private int level = 1;

    private String[][] tasks = {{"Tabletop Circle: Lace fingers around water bottle and make large circular movements on the table. Make 10 circles.",
            "Unweighted Bicep Curls: Start with your elbow on a table with your arm bent at 90-degrees. Then, curl your arm up just a little, and then release it back down just a little. Slowly repeat 10 times.",
            "Open Arm: hold a water bottle with your affected hand and keep your elbows close to your sides. Then, open your arms up so that your forearms come out to your sides while keeping your elbows pinned to your side. Return to center and slowly repeat 10 times."},
            {"Weight lean: Start by sitting on the edge of a bed or sofa. Gently prop yourself up on one arm about one foot away from your body. Gently lean onto your arm. Hold the stretch for 10 seconds, and then return to center. Repeat 3 times on each arm.",
                    "Sideways Push: Push a waterbottle laterally across the table using the back of your hand. Repeat 5 times on each arm",
                    "Forward Push: Push a waterbottle forwards across the table using the knuckles of your hand. Repeat 5 times on each arm."}       };
    private int[][] images = {{R.drawable.armcircle, R.drawable.bicep, R.drawable.out},
            {R.drawable.lean, R.drawable.sidepush, R.drawable.forwardpush}};
    private String[] instructions = {"Sit at a table and have a waterbottle.",
            "Sit on a sofa or bed for excersize one and at a table for excersize two and three."};
    private String[] title = {"Level 1", "Level 2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arm);

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
        homeButton = (Button) findViewById(R.id.HomeButtonW);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        selectLevel(sharedPref.getInt("level",1), sharedPref);

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArmActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
}