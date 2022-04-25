package com.example.strokeapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{

    private String dateSelected;
    private static final String [] MONTHS = new String [] {"Jan.", "Feb.", "Mar.", "Apr.", "May",
            "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};
    private List<Task> tasks, dailyTasks;
    //private List<List<Task>> weeklyTasks;
    private int [] dateArr; // 0: month, 1: day, 2: year
    private AlertDialog dialog;
    private TasksManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        createNotificationChannel();

        initializeButtons();

        manager = TasksManager.getInstance();
        updateTasks();

        dateArr = new int [3];
        setDate(Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.YEAR));

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            dateArr = extras.getIntArray("dateArr");
            dateSelected =  MONTHS[dateArr[0]] + " " + dateArr[1] + ", " + dateArr[2];
        }

        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(v -> {
            Task t = new Task();
            manager.addTask(t);
            addTaskDialog(t, true);
        });

        showTasks();
    }

    private void initializeButtons()
    {
        ImageButton homeButton = findViewById(R.id.HomeButtonC);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ImageButton rehabButton = findViewById(R.id.RehabButtonC);
        rehabButton.setOnClickListener(view -> {
            Intent intent = new Intent(CalendarActivity.this, RehabActivity.class);
            startActivity(intent);
        });

        ImageButton profileButton = findViewById(R.id.ProfileButtonC);
        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(CalendarActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        ImageButton calendarButton = findViewById(R.id.CalendarButtonC);
        calendarButton.setOnClickListener(view -> {
            Intent intent = new Intent(CalendarActivity.this, CalendarActivity.class);
            startActivity(intent);
        });
    }

    private void updateTasks() {
        manager.initialize(getApplicationContext());
        tasks = manager.getTasks();
        for(Task t : tasks)
            if(t.getNotifTypeNum() != 5)
                updateAlarm(t);
        /*weeklyTasks = manager.getWeeklyTasks();
        for(List<Task> list : weeklyTasks)
            for(Task t : list)
                if(!t.isNotified()) updateAlarm(t);*/
    }

    private void setDate(int month, int day, int year) {
        dateSelected =  MONTHS[month] + " " + day + ", " + year;
        dateArr[0] = month;
        dateArr[1] = day;
        dateArr[2] = year;
    }

    private void showTasks() {
        TextView tasksTitle = findViewById(R.id.tasksTitleText);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, dateArr[0]);
        calendar.set(Calendar.DAY_OF_MONTH, dateArr[1]);
        calendar.set(Calendar.YEAR, dateArr[2]);
        long millis = calendar.getTimeInMillis();

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setDate(millis);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            setDate(month, dayOfMonth, year);
            if(year == Calendar.getInstance().get(Calendar.YEAR) &&
                    month == Calendar.getInstance().get(Calendar.MONTH) &&
                    dayOfMonth == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                tasksTitle.setText("Tasks for today (" + dateSelected + "):");
            else tasksTitle.setText("Tasks for " + dateSelected + ":");
            showTasks();
        });

        LinearLayout tasksLayout = findViewById(R.id.tasksLayout);
        tasksLayout.removeAllViews();

        List<Task> tasksToday = new ArrayList<>();
        for(Task t : tasks) {
            if(dateSelected.equals(t.getDate()) || t.getRepeatType() == 0)
                tasksToday.add(t);
        }
        //tasksToday.addAll(weeklyTasks.get(calendar.get(Calendar.DAY_OF_WEEK)-1));
        Collections.sort(tasksToday);

        if(tasksToday.size() == 0) {
            Button btn = createTaskText("No tasks", 6);
            tasksLayout.addView(btn);
        }

        final String [] REPEAT_TYPE = new String [] {"daily", "weekly"};
        for (int i = 0; i < tasksToday.size(); i++) {
            Task t = tasksToday.get(i);
            String text = t.getTime() + ": " + t.getName();
            if (t.getRepeatType() != -1) {
                text += "\n(Repeats " + REPEAT_TYPE[t.getRepeatType()] + ")";
            }
            Button btn = createTaskText(text, t.getCategoryNum());
            tasksLayout.addView(btn);
            btn.setOnClickListener(v -> {
                createTaskDialog(t);
            });
        }
    }

    private Button createTaskText(String text, int categoryNum) {
        final int [] COLORS = new int [] {
                getResources().getColor(R.color.medsColor), getResources().getColor(R.color.cogColor),
                getResources().getColor(R.color.physColor), getResources().getColor(R.color.apptColor),
                getResources().getColor(R.color.mentColor), getResources().getColor(R.color.otherColor),
                getResources().getColor(R.color.noTasks)
        };

        Button btn = new Button(this);
        btn.setText(text);
        btn.setBackgroundColor(COLORS[categoryNum]);
        btn.setPadding(20, 0, 20, 0);
        btn.setTextSize(12);
        btn.setTextColor(getResources().getColor(R.color.black));
        btn.setGravity(Gravity.CENTER_VERTICAL);
        btn.setAllCaps(false);
        btn.setTypeface(ResourcesCompat.getFont(this, R.font.sarabun));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 20);
        btn.setLayoutParams(params);
        return btn;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("task notification", name, importance);
            channel.setDescription(description);
            NotificationManager notifMgr = getSystemService(NotificationManager.class);
            notifMgr.createNotificationChannel(channel);
        }
    }

    private void updateAlarm(Task t) {
        long alarmTime = t.setAlarmTime();
        Intent alarmIntent = new Intent(CalendarActivity.this, NotificationReceiver.class);
        alarmIntent.putExtra("name", t.getName());
        alarmIntent.putExtra("time", t.getTime());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(CalendarActivity.this, 0,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        long time = System.currentTimeMillis();
        if(t.getRepeatType() == -1) {
            if(System.currentTimeMillis() < alarmTime) {
                alarmMgr.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
                t.setNotified(true);
            }
            else alarmMgr.cancel(pendingIntent);
        }
        else {
            long interval = AlarmManager.INTERVAL_DAY;
            /*if(t.getRepeatType() == 1)
                interval *= 7;*/
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, interval, pendingIntent);
            t.setNotified(true);
        }
        t.setPendingIntent(pendingIntent);
    }

    private void createTaskDialog(Task t) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View popUpView = getLayoutInflater().inflate(R.layout.task_popup, null);

        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        Button editBtn = popUpView.findViewById(R.id.editTaskBtn);
        editBtn.setOnClickListener(v -> {
            dialog.dismiss();
            addTaskDialog(t, false);
        });

        TextView confirmDelete = popUpView.findViewById(R.id.confirmDelete);
        LinearLayout btnLayout = popUpView.findViewById(R.id.btnLayout);
        LinearLayout delLayout = popUpView.findViewById(R.id.delBtnLayout);

        Button deleteBtn = popUpView.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(v -> {
            confirmDelete.setVisibility(View.VISIBLE);
            delLayout.setVisibility(View.VISIBLE);
            btnLayout.setVisibility(View.GONE);
        });

        Button noBtn = popUpView.findViewById(R.id.noDelBtn);
        noBtn.setOnClickListener(v -> {
            confirmDelete.setVisibility(View.GONE);
            delLayout.setVisibility(View.GONE);
            btnLayout.setVisibility(View.VISIBLE);
        });

        Button yesBtn = popUpView.findViewById(R.id.yesDelBtn);
        yesBtn.setOnClickListener(v -> {
            manager.deleteTask(t);
            showTasks();
            AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarmMgr.cancel(t.getPendingIntent());
            dialog.dismiss();
        });

        String text = "Name: " + t.getName() + "\nDate: " + t.getDate() +
                "\nTime: " + t.getTime() + "\nCategory: " + t.getCategory();
        if(t.getNotifTypeNum() != 5) text += "\nSend reminder " + t.getNotifType();
        TextView textView = popUpView.findViewById(R.id.taskDetailsText);
        textView.setText(text);
    }

    private RadioButton selected;
    private boolean btnClicked;

    private void addTaskDialog(Task t, boolean isNew) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View popUpView = getLayoutInflater().inflate(R.layout.activity_task, null);

        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        RadioButton[] radioButtons = new RadioButton[]{
                popUpView.findViewById(R.id.medsRB), popUpView.findViewById(R.id.cogRB),
                popUpView.findViewById(R.id.physRB), popUpView.findViewById(R.id.appRB),
                popUpView.findViewById(R.id.mentalRB), popUpView.findViewById(R.id.otherRB)
        };

        Button nextBtn = popUpView.findViewById(R.id.nextTaskBtn);
        EditText enterName = popUpView.findViewById(R.id.enterName);

        if(t.getCategoryNum() != -1) {
            selected = radioButtons[t.getCategoryNum()];
            selected.setChecked(true);
            if(!t.getName().equals("")) nextBtn.setEnabled(true);
        }

        for(RadioButton btn : radioButtons) {
            btn.setOnClickListener(v -> {
                selected = btn;
                if(enterName.getText().toString().trim().length() != 0)
                    nextBtn.setEnabled(true);
                btnClicked = true;
            });
        }

        enterName.setText(t.getName());
        enterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextBtn.setEnabled(btnClicked && getText(enterName).length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        nextBtn.setOnClickListener(v -> {
            dialog.dismiss();
            t.setName(getText(enterName));
            t.setCategory(getText(selected));
            addTaskDialog2(t, isNew);
        });

        Button cancelBtn = popUpView.findViewById(R.id.cancelBtn1);
        cancelBtn.setOnClickListener(v -> {
            dialog.dismiss();
            manager.deleteTask(t);
        });
    }

    private TextView dateText;
    private String time;
    private int repeatType; // -1 = never, 0 = daily, 1 = weekly
    private int notifType; // 0 = scheduled, 1 = 10 min, 2 = 30 min, 3 = 1 hr, 4 = 1 day, 5 = never

    private void addTaskDialog2(Task t, boolean isNew) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View popUpView = getLayoutInflater().inflate(R.layout.activity_task2, null);

        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        TextView nameText = popUpView.findViewById(R.id.taskText);
        TextView categoryText = popUpView.findViewById(R.id.categoryText);

        String name = t.getName();
        String category = t.getCategory();
        nameText.setText("Task: " + name);
        categoryText.setText("Category: " + category);
        categoryText.setBackgroundColor(((ColorDrawable) selected.getBackground()).getColor());

        setDate(dateArr[0], dateArr[1], dateArr[2]);

        time = t.getTime();
        if(time.equals("")) setTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE));

        Button dateBtn = popUpView.findViewById(R.id.dateBtn);
        Button timeBtn = popUpView.findViewById(R.id.timeBtn);

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

        dateText = popUpView.findViewById(R.id.dateText);
        setDateText();

        repeatType = t.getRepeatType();
        Spinner repeatSpinner = popUpView.findViewById(R.id.repeatSpinner);
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

        notifType = t.getNotifTypeNum();
        Spinner notifSpinner = popUpView.findViewById(R.id.notifSpinner);
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

        Button backBtn = popUpView.findViewById(R.id.taskBackBtn);
        backBtn.setOnClickListener(v -> {
            dialog.dismiss();
            addTaskDialog(t, isNew);
        });

        Button addBtn = popUpView.findViewById(R.id.nextTaskBtn);
        addBtn.setOnClickListener(v -> {
            t.setName(name);
            t.setCategory(category);
            t.setDate(dateSelected);
            t.setTime(time);
            t.setRepeatType(repeatType);
            t.setNotifTypeNum(notifType);
            dialog.dismiss();
            manager.savePreferences();
            updateTasks();
            showTasks();
        });

        Button cancelBtn = popUpView.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(v -> {
            manager.deleteTask(t);
            dialog.dismiss();
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
        String text = "Your task is scheduled for: \n" + dateSelected + " at " + time;
        dateText.setText(text);
    }

    private String getText (EditText editText) {
        return editText.getText().toString().trim();
    }

    private String getText(RadioButton button) {
        return button.getText().toString().trim();
    }
}