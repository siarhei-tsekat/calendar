package com.me.calendar.service;


import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.me.calendar.App;
import com.me.calendar.CalendarUtils;
import com.me.calendar.R;
import com.me.calendar.repository.model.Event;
import com.me.calendar.repository.model.EventRepeat;
import com.me.calendar.repository.model.PaletteColors;
import com.me.calendar.screen.EditEventActivity;

import java.time.LocalDate;
import java.time.LocalTime;

public class NotificationService extends WakeReminderIntentService {

    public static final String TAG = NotificationService.class.getSimpleName();

    @Override
    void doReminderWork(Intent intent) {
        Log.i(TAG, "Received an Intent: " + intent);

        Event event = intent.getParcelableExtra(ReminderManager.EVENT_EXTRA_KEY);

        Resources resources = getResources();

        Intent newInstance = EditEventActivity.newInstance(this, new Event(100L, "fake", LocalDate.now(), LocalTime.now(), EventRepeat.No, PaletteColors.Blue));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, newInstance, PendingIntent.FLAG_IMMUTABLE);

        Intent snoozeIntent = new Intent(this, OnAlarmReceiver.class);
        snoozeIntent.setAction(ACTION_STOP_FOREGROUND_SERVICE);
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, App.Notification_channel_id)
                .setTicker(resources.getString(R.string.new_event_title))
                .setSmallIcon(R.drawable.notification_event)
                .setContentTitle(resources.getString(R.string.new_event_title))
                .setContentText(event.getName() + " : " + CalendarUtils.formattedShortTime(event.getTime()))
//                .setContentText("Hello")
                .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setLights(PaletteColors.LightBlue, 5000, 5000)
                .setTimeoutAfter(30_000L)
                .addAction(R.drawable.snooze_btn, "snooze for 10 min", snoozePendingIntent)
                .build();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startForeground(1, notification);
        stopForegroundService();

//        NotificationManagerCompat.from(this).notify(0, notification);
//        new NotificationCompat.Builder(this).setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
    }

    //    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        Log.i(TAG, "Received an Intent: " + intent);
//
//        Event event = intent.getParcelableExtra("event", Event.class);
//
//        Resources resources = getResources();
//
//        Intent newInstance = EditEventActivity.newInstance(this, new Event(100L, "fake", LocalDate.now(), LocalTime.now(), EventRepeat.No, PaletteColors.Blue));
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, newInstance, PendingIntent.FLAG_IMMUTABLE);
//
//        Notification notification = new NotificationCompat.Builder(this, App.Notification_channel_id)
//                .setTicker(resources.getString(R.string.new_event_title))
//                .setSmallIcon(R.drawable.notification_event)
//                .setContentTitle(resources.getString(R.string.new_event_title))
//                .setContentText(event.getName() + " : " + CalendarUtils.formattedShortTime(event.getTime()))
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .build();
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
////        startForeground(1, notification);
//        NotificationManagerCompat.from(this).notify(0, notification);
////
////            sendBroadcast(new Intent(ACTION_SHOW_NOTIFICATION), PERM_PRIVATE);
//
////        showBackgroundNotification(0, notification);
//    }
}
