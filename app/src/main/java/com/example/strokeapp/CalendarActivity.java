package com.example.strokeapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

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
        manager.initialize(getApplicationContext());
        tasks = manager.getTasks();
        for(Task t : tasks)
            if(t.getNotifTypeNum() != 5) updateAlarm(t);
        dailyTasks = manager.getDailyTasks();
        for(Task t : dailyTasks)
            if(!t.isNotified() && t.getNotifTypeNum() != 5) updateAlarm(t);
        /*weeklyTasks = manager.getWeeklyTasks();
        for(List<Task> list : weeklyTasks)
            for(Task t : list)
                if(!t.isNotified()) updateAlarm(t);*/

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
            Intent intent = new Intent(CalendarActivity.this, TaskActivity.class);
            intent.putExtra("date", dateArr);
            startActivity(intent);
        });

        TextView tasksTitle = findViewById(R.id.tasksTitleText);
        tasksTitle.setText("Tasks for today (" + dateSelected + "):");
        showTasks();

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
    }

    public void initializeButtons()
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

    private void setDate(int month, int day, int year) {
        dateSelected =  MONTHS[month] + " " + day + ", " + year;
        dateArr[0] = month;
        dateArr[1] = day;
        dateArr[2] = year;
    }

    private void showTasks() {
        LinearLayout tasksLayout = findViewById(R.id.tasksLayout);
        tasksLayout.removeAllViews();

        List<Task> tasksToday = new ArrayList<>();
        boolean found = false;
        for(int i = 0; i < tasks.size(); i++) {
            if(dateSelected.equals(tasks.get(i).getDate())) {
                tasksToday.add(tasks.get(i));
                found = true;
            }
            else if(found && !dateSelected.equals(tasks.get(i).getDate()))
                break;
        }
        tasksToday.addAll(dailyTasks);
        Calendar calendar = Calendar.getInstance();
        calendar.set(dateArr[2], dateArr[0], dateArr[1]);
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
        btn.setPadding(30, 0, 30, 0);
        btn.setTextSize(16);
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
            Intent intent = new Intent(CalendarActivity.this, TaskActivity.class);
            intent.putExtra("edit", true);
            intent.putExtra("index", tasks.indexOf(t));
            intent.putExtra("type", t.getNotifType());
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
}