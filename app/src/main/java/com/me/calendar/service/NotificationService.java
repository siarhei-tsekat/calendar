package com.me.calendar.service;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.me.calendar.App;
import com.me.calendar.R;

public class NotificationService extends WakeReminderIntentService {

    public static final String TAG = NotificationService.class.getSimpleName();

    public NotificationService() {
        super(TAG);
    }

    @Override
    void doReminderWork(Intent intent) {
        Log.i(TAG, "Received an Intent: " + intent);

//        Event event = intent.getParcelableExtra("event");
//
        Resources resources = getResources();

//        Intent newInstance = EditEventActivity.newInstance(this, new Event(100L, "fake", LocalDate.now(), LocalTime.now(), EventRepeat.No, PaletteColors.Blue));
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, newInstance, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, App.Notification_channel_id)
                .setTicker(resources.getString(R.string.new_event_title))
                .setSmallIcon(R.drawable.notification_event)
                .setContentTitle(resources.getString(R.string.new_event_title))
//                .setContentText(event.getName() + " : " + CalendarUtils.formattedShortTime(event.getTime()))
                .setContentText("Hello")
                .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
                .setTimeoutAfter(1000 * 30)
                .build();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startForeground(2, notification);
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
