package com.me.calendar.service;

import android.Manifest;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.icu.util.Calendar;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.me.calendar.App;
import com.me.calendar.R;
import com.me.calendar.repository.model.Event;
import com.me.calendar.repository.model.EventNotification;
import com.me.calendar.repository.model.EventRepeat;
import com.me.calendar.repository.model.PaletteColors;
import com.me.calendar.screen.EditEventActivity;

import java.time.LocalDate;
import java.time.LocalTime;

public class NotificationService extends IntentService {

    public static final String TAG = NotificationService.class.getSimpleName();

    private static final int POLL_INTERVAL = 1000 * 6; // 60 sec

    public static Intent newIntent(Context context) {
        return new Intent(context, NotificationService.class);
    }

    public NotificationService(String name) {
        super(name);
        setIntentRedelivery(true);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent intent = NotificationService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getForegroundService(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 12);
            calendar.set(Calendar.MINUTE, 23);
            calendar.set(Calendar.SECOND, 1);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent intent = NotificationService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);
        return pendingIntent != null;
    }

    public NotificationService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService(service);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "Received an Intent: " + intent);
        Resources resources = getResources();

        Intent newInstance = EditEventActivity.newInstance(this, new Event(100L, "fake", LocalDate.now(), LocalTime.now(), EventRepeat.No, PaletteColors.Blue));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, newInstance, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, App.Notification_channel_id)
                .setTicker(resources.getString(R.string.new_event_title))
                .setSmallIcon(R.drawable.notification_event)
                .setContentTitle(resources.getString(R.string.new_event_title))
                .setContentText(resources.getString(R.string.new_event_text))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startForeground(1, notification);
//        NotificationManagerCompat.from(this).notify(0, notification);
//
//            sendBroadcast(new Intent(ACTION_SHOW_NOTIFICATION), PERM_PRIVATE);

//        showBackgroundNotification(0, notification);
    }
}
