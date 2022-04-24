package com.example.strokeapp;

import android.app.PendingIntent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Task implements Comparable<Task> {

    private String name;
    private String category;
    private String date;
    private String time;
    private String notifType;
    private int repeatType; // -1 = never, 0 = daily, 1 = weekly
    private int categoryNum;
    private int notifTypeNum; // 0 = scheduled, 1 = 10 min, 2 = 30 min, 3 = 1 hr, 4 = 1 day, 5 = never
    private boolean notified;

    private PendingIntent pendingIntent;
    private static final String [] MONTHS = new String [] {"Jan.", "Feb.", "Mar.", "Apr.", "May",
            "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};

    private static final int [] DAY_IN_MONTH = new int [] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private static final List<String> CATEGORIES = new ArrayList<>(
            List.of("medications", "cognitive rehab", "physical rehab",
                    "appointments", "mental health check-in", "other/personal event")
    );

    private static final String [] NOTIF_TYPES = new String [] {"at scheduled time", "10 min. before",
            "30 min. before", "1 day before", "never"};

    public Task() {
        name = "";
        category = "";
        date = "";
        time = "";
        categoryNum = -1;
        notifType = "";
        repeatType = -1;
        notifTypeNum = 0;
    }

    public Task(String n, String c, String d, String t, int rType, int nType) {
        name = n;
        category = c;
        date = d;
        time = t;
        categoryNum = CATEGORIES.indexOf(category);
        repeatType = rType;
        notifTypeNum = nType;
        notifType = NOTIF_TYPES[notifTypeNum];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        categoryNum = CATEGORIES.indexOf(category);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setRepeatType(int repeatType) {
        this.repeatType = repeatType;
    }

    public void setNotifTypeNum(int notifTypeNum) {
        this.notifTypeNum = notifTypeNum;
        notifType = NOTIF_TYPES[notifTypeNum];
    }

    public String getTime() {
        return time;
    }

    public int getCategoryNum() {
        return categoryNum;
    }

    public int getRepeatType() {
        return repeatType;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    public int getNotifTypeNum() {
        return notifTypeNum;
    }

    public String getNotifType() {
        return notifType;
    }

    public int getMonth() {
        String mStr = date.substring(0, date.indexOf(" "));
        int m = 0;
        for(int i = 0; i < MONTHS.length; i++) {
            if(mStr.equals(MONTHS[i])) {
                m = i;
                break;
            }
        }
        return m;
    }

    public int getDayOfMonth() {
        return Integer.parseInt(date.substring(date.indexOf(" ") + 1, date.indexOf(",")));
    }

    public int getYear() {
        return Integer.parseInt(date.substring(date.indexOf(",") + 2));
    }

    public int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getYear(), getMonth(), getDayOfMonth());
        return calendar.get(Calendar.DAY_OF_WEEK)-1;
    }

    @Override
    public int compareTo(Task o) {
        if(getYear() == o.getYear() || repeatType != -1) {
            if(getMonth() == o.getMonth() || repeatType != -1) {
                if(getDayOfMonth() == o.getDayOfMonth() || repeatType != -1) {
                    if(getHrInt() == o.getHrInt()) {
                        return Integer.compare(getMinInt(), o.getMinInt());
                    }
                    return Integer.compare(getHrInt(), o.getHrInt());
                }
                return Integer.compare(getDayOfMonth(), o.getDayOfMonth());
            }
            return Integer.compare(getMonth(), o.getMonth());
        }
        return Integer.compare(getYear(), o.getYear());
    }

    private int getHrInt() {
        int hr = Integer.parseInt(time.substring(0, time.indexOf(":")));
        String ampm = time.substring(time.indexOf(" ") + 1);
        if(ampm.equals("AM") && hr == 12) hr = 0;
        else if(ampm.equals("PM") && hr != 12) {
            hr += 12;
        }
        return hr;
    }

    private int getMinInt() {
        return Integer.parseInt(time.substring(time.indexOf(":") + 1, time.indexOf(" ")));
    }

    public long setAlarmTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, getHrInt());
        calendar.set(Calendar.MINUTE, getMinInt());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //if(repeatType == 1) calendar.set(Calendar.DAY_OF_WEEK, getDayOfWeek() + 1);
        if(repeatType == -1) {
            calendar.set(Calendar.DAY_OF_MONTH, getDayOfMonth());
            calendar.set(Calendar.MONTH, getMonth());
            calendar.set(Calendar.YEAR, getYear());
        }
        long time = calendar.getTimeInMillis();
        switch (notifTypeNum) {
            case 1:
                time -= 10 * 60 * 1000;
                break;
            case 2:
                time -= 30 * 60 * 1000;
                break;
            case 3:
                time -= 60 * 60 * 1000;
                break;
            case 4:
                time -= 24 * 60 * 60 * 1000;
                break;
        }
        return time;
    }
}
