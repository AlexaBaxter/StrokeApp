package com.example.strokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class TaskActivity extends AppCompatActivity {

    private RadioButton selected;
    private Button nextBtn;
    private EditText enterName;
    private boolean btnClicked, back;
    private int [] date;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        name = "";
        int categoryIndex = -1;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            date = extras.getIntArray("date");
            name = extras.getString("name", "");
            categoryIndex = extras.getInt("categoryIndex", -1);
            back = extras.getBoolean("back", false);
        }

        RadioButton[] radioButtons = new RadioButton[]{
                findViewById(R.id.medsRB), findViewById(R.id.cogRB),
                findViewById(R.id.physRB), findViewById(R.id.appRB),
                findViewById(R.id.mentalRB), findViewById(R.id.otherRB)
        };

        nextBtn = findViewById(R.id.nextTaskBtn);
        enterName = findViewById(R.id.enterName);

        if(categoryIndex != -1) {
            selected = radioButtons[categoryIndex];
            selected.setChecked(true);
            if(!name.equals("")) nextBtn.setEnabled(true);
        }
        for(RadioButton btn : radioButtons) {
            btn.setOnClickListener(v -> {
                selected = btn;
                if(enterName.getText().toString().trim().length() != 0)
                    nextBtn.setEnabled(true);
                btnClicked = true;
            });
        }

        enterName.setText(name);
        enterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = enterName.getText().toString();
                if(btnClicked && enterName.getText().toString().trim().length() != 0)
                    nextBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TaskActivity.this, TaskActivity2.class);
            intent.putExtra("name", name);
            intent.putExtra("category", selected.getText().toString());
            intent.putExtra("background", ((ColorDrawable) selected.getBackground()).getColor());
            intent.putExtra("date", date);
            intent.putExtra("back", back);
            startActivity(intent);
        });

        Button cancelBtn = findViewById(R.id.cancelBtn1);
        cancelBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TaskActivity.this, CalendarActivity.class);
            startActivity(intent);
        });
    }

}