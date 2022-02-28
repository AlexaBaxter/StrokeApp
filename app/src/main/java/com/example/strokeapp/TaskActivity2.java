package com.example.strokeapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskActivity2 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private TextView dateText;
    private String date, time, name, category;
    private int [] dateArr = new int [3]; // 0 = month, 1 = day, 2 = year
    private int color;
    private int repeatType; // -1 = never, 0 = daily, 1 = weekly
    private int notifType; // 0 = scheduled, 1 = 10 min, 2 = 30 min, 3 = 1 hr, 4 = 1 day, 5 = never
    private final String [] MONTHS = new String [] {"Jan.", "Feb.", "Mar.", "Apr.", "May",
            "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};
    TasksManager manager;
    SharedPreferences userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);

        manager = TasksManager.getInstance();
        manager.initialize(getApplicationContext());

        userData = this.getSharedPreferences("Task2", 0);
        SharedPreferences.Editor editor = userData.edit();

        TextView nameText = findViewById(R.id.taskText);
        TextView categoryText = findViewById(R.id.categoryText);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            name = extras.getString("name");
            category = extras.getString("category");
            color = extras.getInt("background");
            dateArr = extras.getIntArray("date");
            if(!extras.getBoolean("back")) {
                editor.clear();
                editor.apply();
            }
        }
        nameText.setText("Task: " + name);
        categoryText.setText("Category: " + category);
        categoryText.setBackgroundColor(color);

        setDate(dateArr[0], dateArr[1], dateArr[2]);

        time = userData.getString("time", "");
        if(time.equals("")) setTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE));

        Button dateBtn = findViewById(R.id.dateBtn);
        Button timeBtn = findViewById(R.id.timeBtn);

        dateBtn.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                    dateArr[2], dateArr[0], dateArr[1]
            );
            datePickerDialog.show();
        });

        timeBtn.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, this,
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE),
                    false
            );
            timePickerDialog.show();
        });

        dateText = findViewById(R.id.dateText);
        setDateText();

        repeatType = userData.getInt("repeat", -1);
        Spinner repeatSpinner = findViewById(R.id.repeatSpinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.repeat_array, R.layout.spinner_item);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_item);
        repeatSpinner.setAdapter(adapter1);
        repeatSpinner.setSelection(repeatType+1);
        repeatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                repeatType = position-1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        notifType = userData.getInt("notif", -1);
        Spinner notifSpinner = findViewById(R.id.notifSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.notif_array, R.layout.spinner_item);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        notifSpinner.setAdapter(adapter2);
        notifSpinner.setSelection(notifType);
        notifSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                notifType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        Button backBtn = findViewById(R.id.taskBackBtn);
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TaskActivity2.this, TaskActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("date", dateArr);
            final List<String> CATEGORIES = new ArrayList<>(
                    List.of("medications", "cognitive rehab", "physical rehab",
                            "appointments", "mental health check-in", "other/personal event")
            );
            intent.putExtra("categoryIndex", CATEGORIES.indexOf(category));
            intent.putExtra("back", true);
            editor.putString("time", time);
            editor.putInt("repeat", repeatType);
            editor.putInt("notif", notifType);
            editor.commit();
            startActivity(intent);
        });

        Button addBtn = findViewById(R.id.nextTaskBtn);
        addBtn.setOnClickListener(v -> {
            Task task = new Task(name, category, date, time, repeatType, notifType);
            manager.addTask(task);
            Intent intent = new Intent(TaskActivity2.this, CalendarActivity.class);
            intent.putExtra("dateArr", dateArr);
            startActivity(intent);
        });

        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TaskActivity2.this, CalendarActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        setDate(month, dayOfMonth, year);
        setDateText();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        setTime(hourOfDay, minute);
        setDateText();
    }

    private void setDate(int month, int day, int year) {
        date = MONTHS[month] + " " + day + ", " + year;
        dateArr[0] = month;
        dateArr[1] = day;
        dateArr[2] = year;
    }

    private void setTime(int hr, int min) {
        String ampm = "AM";
        if(hr == 0) hr = 12;
        else if(hr >= 12) {
            ampm = "PM";
            if(hr != 12) {
                hr -= 12;
            }
        }
        String minStr = min + "";
        if((min + "").length() < 2)
            minStr = "0" + min;
        time = hr + ":" + minStr + " " + ampm;
    }

    private void setDateText() {
        String text = "Your task is scheduled for: \n" + date + " at " + time;
        dateText.setText(text);
    }
}