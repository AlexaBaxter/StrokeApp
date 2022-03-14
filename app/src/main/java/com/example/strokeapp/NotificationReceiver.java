package com.example.strokeapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String name = "";
        String time = "";
        if(extras != null) {
            name = extras.getString("name");
            time = extras.getString("time");
        }
        Intent notifIntent = new Intent(context, CalendarActivity.class);
        notifIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "task notification")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Task Reminder")
                .setContentText("Task scheduled for today at " + time + ": " + name)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notifMgr = NotificationManagerCompat.from(context);
        notifMgr.notify(0, builder.build());
    }
}
