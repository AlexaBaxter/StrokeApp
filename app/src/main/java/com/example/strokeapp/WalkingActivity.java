package com.example.strokeapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WalkingActivity extends AppCompatActivity {

    private CheckBox [] checkboxes;
    private ImageView [] stars;
    private TextView levelInstruct, levelTitle;
    private ImageView [] imageViews;
    private int level = 0;
    private ImageButton rehabButton, homeButton, profileButton, calendarButton;
    private AlertDialog dialog;

    private final String[][] tasks = {{"Knee extensions: Extend one leg until its parallel to the ground. Then, " +
            "slowly lower your foot back down. Repeat 10 times on each leg (alternating).",
            "Seated marching: Lift leg up to chest, then put it back down. Repeat 10 times on each leg (alternating).",
            "Ankle Stretch: Start with one leg crossed over your other leg. Flex your foot back towards your shin, and " +
            "hold for 5 seconds. Repeat 5 times on each leg."},
            {"Forward walking: Follow a straight line on the ground and walk forwards. Repeat 5 times.",
            "Forward step-overs: Facing forward, take steps over sticks on the ground. Make sure to step using alternating feet. Repeat 5 times.",
            "Sideways step-overs: Facing sideways, take steps over sticks on the ground. Repeat 5 times."}};
    private final int[][] images = {{R.drawable.extention, R.drawable.marching, R.drawable.ankle},
            {R.drawable.straight, R.drawable.forward, R.drawable.side}};
    private final String[] instructions = {"Stay seated in a chair for all exercises.",
    "Use a cane or walking aid if necessary. Have a relative or caregiver next to you for support."};
    private final String[] title = {"Level 1", "Level 2"};

    private ProgressManager manager;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);
        initializeButtons();
        manager = ProgressManager.getInstance();
        manager.initialize(getApplicationContext());

        checkboxes = new CheckBox [] {findViewById(R.id.checkBox1),
                findViewById(R.id.checkBox2), findViewById(R.id.checkBox3)};
        stars = new ImageView [] {findViewById(R.id.star1),
                findViewById(R.id.star2), findViewById(R.id.star3)};

        levelInstruct = findViewById(R.id.instructionsW);
        levelTitle = findViewById(R.id.levelText);

        imageViews = new ImageView [] {findViewById(R.id.imageView1),
                findViewById(R.id.imageView2), findViewById(R.id.imageView3)};

        Button level1Button = findViewById(R.id.lv1Button);
        Button level2Button = findViewById(R.id.lvl2Button);

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        //level = sharedPref.getInt("level", 0);
        level = 0;
        selectLevel();

        for(int i = 0; i < checkboxes.length; i++) {
            CheckBox cb = checkboxes[i];
            int x = i;
            cb.setOnClickListener(v -> {
                if(cb.isChecked()) {
                    stars[x].setImageResource(R.drawable.filledstar);
                    Toast.makeText(WalkingActivity.this, "Good job!",
                            Toast.LENGTH_SHORT).show();
                    manager.changeStars(true, x, level);
                }
                else {
                    stars[x].setImageResource(R.drawable.emptystar);
                    manager.changeStars(false, x, level);
                }
            });
        }

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
            Intent intent = new Intent(WalkingActivity.this, PhysicalActivity.class);
            startActivity(intent);
        });

        ImageView [] imageViews = new ImageView [] {findViewById(R.id.imageView1),
                findViewById(R.id.imageView2), findViewById(R.id.imageView3)};
        for(int i = 0; i < imageViews.length; i++) {
            ImageView view = imageViews[i];
            final int x = i;
            view.setOnClickListener(v -> {
                createImageDialog(x);
            });
        }
    }

    private void selectLevel()
    {
        levelTitle.setText(title[level]);
        levelInstruct.setText(instructions[level]);

        for(int i = 0; i < checkboxes.length; i++)
            checkboxes[i].setText(tasks[level][i]);
        for(int i = 0; i < images[level].length; i++)
            imageViews[i].setImageResource(images[level][i]);

        for(ImageView star : stars) star.setImageResource(R.drawable.emptystar);
        for(CheckBox cb : checkboxes) cb.setChecked(false);

        for(int i = 0; i < 3; i++) {
            if(manager.starIsFilled(level, i)) {
                stars[i].setImageResource(R.drawable.filledstar);
                checkboxes[i].setChecked(true);
            }
        }
    }

    private void createImageDialog(int index) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View popUpView = getLayoutInflater().inflate(R.layout.image_popup, null);

        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        ImageView img = popUpView.findViewById(R.id.exerciseImage);
        img.setImageResource(images[level][index]);

        String [][] names = new String [][] {
                {"Knee Extensions", "Seated Marching", "Ankle Stretch"},
                {"Forward Walking", "Forward Step-Overs", "Sideways Step-Overs"}
        };

        TextView title = popUpView.findViewById(R.id.exerciseName);
        title.setText(names[level][index]);
    }

    public void initializeButtons()
    {
        homeButton = findViewById(R.id.HomeButtonW);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(WalkingActivity.this, MainActivity.class);
            startActivity(intent);
        });

        rehabButton = findViewById(R.id.RehabButtonW);
        rehabButton.setOnClickListener(view -> {
            Intent intent = new Intent(WalkingActivity.this, RehabActivity.class);
            startActivity(intent);
        });

        profileButton = findViewById(R.id.ProfileButtonW);
        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(WalkingActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        calendarButton = findViewById(R.id.CalendarButtonW);
        calendarButton.setOnClickListener(view -> {
            Intent intent = new Intent(WalkingActivity.this, CalendarActivity.class);
            startActivity(intent);
        });
    }
}