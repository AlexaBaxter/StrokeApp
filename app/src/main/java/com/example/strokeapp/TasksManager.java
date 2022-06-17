package com.example.strokeapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TasksManager {

    private static TasksManager self;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean isInitialized;
    private List<Task> tasks;
    //private List<List<Task>> weeklyTasks;

    private TasksManager() { }

    public void initialize(Context context) {
        if(!isInitialized) {
            tasks = new ArrayList<>();
            //weeklyTasks = new ArrayList<>();
            //for(int i = 0; i < 7; i++) weeklyTasks.add(new ArrayList<>());
            preferences = context.getSharedPreferences("TasksManager", 0);
            editor = preferences.edit();
            loadPreferences();
            //editor.clear();
            //editor.apply();
            isInitialized = true;
        }
    }

    public static TasksManager getInstance() {
        if (self == null) {
            self = new TasksManager();
        }
        return self;
    }

    public void addTask(Task t) {
        tasks.add(t);
        savePreferences();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    /*public List<List<Task>> getWeeklyTasks() {
        return weeklyTasks;
    }*/

    public void deleteTask(Task t) {
        tasks.remove(t);
        //for(List<Task> day : weeklyTasks) day.remove(t);
        savePreferences();
    }

    public void savePreferences() {
        for(int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            editor.putString("name" + i, t.getName());
            editor.putString("category" + i, t.getCategory());
            editor.putString("date" + i, t.getDate());
            editor.putString("time"+ i, t.getTime());
            editor.putInt("repeatType" + i, t.getRepeatType());
            editor.putInt("notifType" + i, t.getNotifTypeNum());
            editor.putBoolean("notified" + i, t.isNotified());
        }
        editor.putInt("numTasks", tasks.size());
        editor.commit();
    }

    private void loadPreferences() {
        int numTasks = preferences.getInt("numTasks", 0);
        for(int i = 0; i < numTasks; i++) {
            String name = preferences.getString("name" + i, "");
            String category = preferences.getString("category" + i, "");
            String date = preferences.getString("date" + i, "");
            String time = preferences.getString("time" + i, "");
            int repeatType = preferences.getInt("repeatType" + i, -1);
            int notifType = preferences.getInt("notifType" + i, 0);
            Task task = new Task(name, category, date, time, repeatType, notifType);
            task.setNotified(preferences.getBoolean("notified" + i, false));
            tasks.add(task);
        }
    }

    private int saveTasks(List<Task> list, int count) {
        for(int i = 0; i < list.size(); i++) {
            Task t = list.get(i);
            int n = i + count;
            editor.putString("name" + n, t.getName());
            editor.putString("category" + n, t.getCategory());
            editor.putString("date" + n, t.getDate());
            editor.putString("time"+ n, t.getTime());
            editor.putInt("repeatType" + n, t.getRepeatType());
            editor.putInt("notifType" + n, t.getNotifTypeNum());
            editor.putBoolean("notified" + n, t.isNotified());
            count++;
        }
        return count;
    }
}
