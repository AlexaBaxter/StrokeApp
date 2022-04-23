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

public class ArmActivity extends AppCompatActivity {

    private CheckBox [] checkboxes;
    private ImageView [] stars;
    private TextView levelInstruct, levelTitle;
    private ImageView [] imageViews;
    private int level = 1;
    public ImageButton rehabButton, homeButton, profileButton, calendarButton;

    private final String[][] tasks = {
            {"Tabletop Circles: Lace fingers around water bottle and make large circular " +
                    "movements on the table. Make 10 circles.",
            "Unweighted Bicep Curls: Start with your elbow on a table with your arm bent at 90-degrees. Then, curl " +
                    "your arm up just a little, and then release it back down just a little. Slowly repeat 10 times.",
            "Open Arm: hold a water bottle with your affected hand and keep your elbows close to your sides. Then, " +
                    "open your arms up so that your forearms come out to your sides while keeping your elbows pinned " +
                    "to your side. Return to center and slowly repeat 10 times."},
            {"Weight lean: Start by sitting on the edge of a bed or sofa. Gently prop yourself up on one arm about " +
                    "one foot away from your body. Gently lean onto your arm. Hold the stretch for 10 seconds, and then " +
                    "return to center. Repeat 3 times on each arm.",
                    "Sideways Push: Push a water bottle laterally across the table using the back of your hand. " +
                            "Repeat 5 times on each arm",
                    "Forward Push: Push a water bottle forwards across the table using the knuckles of your hand. " +
                            "Repeat 5 times on each arm."}};
    private final int[][] images = {{R.drawable.armcircle, R.drawable.bicep, R.drawable.out},
            {R.drawable.lean, R.drawable.sidepush, R.drawable.forwardpush}};
    private final String[] instructions = {"Sit at a table and have a water bottle.",
            "Sit on a sofa or bed for exercise one and at a table for exercise two and three."};
    private final String[] title = {"Level 1", "Level 2"};

    ProgressManager manager;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arm);
        initializeButtons();
        manager = ProgressManager.getInstance();
        manager.initialize(getApplicationContext());

        checkboxes = new CheckBox [] {findViewById(R.id.checkBox1),
                findViewById(R.id.checkBox2), findViewById(R.id.checkBox3)};
        stars = new ImageView [] {findViewById(R.id.star1),
                findViewById(R.id.star2), findViewById(R.id.star3)};

        levelInstruct = findViewById(R.id.instructionsA);
        levelTitle = findViewById(R.id.levelText);

        imageViews = new ImageView [] {findViewById(R.id.imageView1),
                findViewById(R.id.imageView2), findViewById(R.id.imageView3)};

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        level = sharedPref.getInt("level", 0);
        selectLevel();

        for(int i = 0; i < checkboxes.length; i++) {
            CheckBox cb = checkboxes[i];
            int x = i;
            cb.setOnClickListener(v -> {
                if(cb.isChecked()) {
                    stars[x].setImageResource(R.drawable.filledstar);
                    manager.changeStars(true, x, level+2);
                }
                else {
                    stars[x].setImageResource(R.drawable.emptystar);
                    manager.changeStars(false, x, level+2);
                }
            });
        }

        Button level1Button = findViewById(R.id.lv1Button);
        Button level2Button = findViewById(R.id.lvl2Button);
        level1Button.setOnClickListener(v -> {
            level=0;
            selectLevel();
        });

        level2Button.setOnClickListener(v -> {
            level=1;
            selectLevel();
        });

        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ArmActivity.this, PhysicalActivity.class);
            startActivity(intent);
        });
    }

    private void selectLevel()
    {
        levelTitle.setText(title[level]);
        levelInstruct.setText(instructions[level]);

        for(int i = 0; i < checkboxes.length; i++)
            checkboxes[i].setText(tasks[level][i]);
        for(int i = 0; i < images.length; i++)
            imageViews[i].setImageResource(images[level][i]);

        for(ImageView star : stars) star.setImageResource(R.drawable.emptystar);
        for(CheckBox cb : checkboxes) cb.setChecked(false);

        for(int i = 0; i < 3; i++) {
            if(manager.starIsFilled(level+2, i)) {
                stars[i].setImageResource(R.drawable.filledstar);
                checkboxes[i].setChecked(true);
            }
        }
    }

    public void initializeButtons()
    {
        homeButton = findViewById(R.id.HomeButtonA);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ArmActivity.this, MainActivity.class);
            startActivity(intent);
        });

        rehabButton = findViewById(R.id.RehabButtonA);
        rehabButton.setOnClickListener(view -> {
            Intent intent = new Intent(ArmActivity.this, RehabActivity.class);
            startActivity(intent);
        });

        profileButton = findViewById(R.id.ProfileButtonA);
        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(ArmActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        calendarButton = findViewById(R.id.CalendarButtonA);
        calendarButton.setOnClickListener(view -> {
            Intent intent = new Intent(ArmActivity.this, CalendarActivity.class);
            startActivity(intent);
        });
    }
}