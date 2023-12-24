package com.me.calendar.service;

import static com.me.calendar.service.WakeReminderIntentService.ACTION_STOP_FOREGROUND_SERVICE;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.me.calendar.ParcelableUtil;
import com.me.calendar.repository.model.Event;

public class OnAlarmReceiver extends BroadcastReceiver {

    public static final String TAG = OnAlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Received broadcast intent: " + intent);

//        if (intent.getAction() != null && intent.getAction().equals(ACTION_STOP_FOREGROUND_SERVICE)) {
//            Intent intent1 = new Intent(context, NotificationService.class);
//            intent1.setAction(NotificationService.ACTION_SNOOZE_FOREGROUND_SERVICE);
//            PendingIntent pendingPrevIntent = PendingIntent.getService(NotificationService.class, 0, intent1, 0);
//            return;
//        }
        byte[] bytes = intent.getExtras().getByteArray(ReminderManager.EVENT_EXTRA_KEY);

        Event event = ParcelableUtil.unmarshall(bytes, Event.CREATOR);
        WakeReminderIntentService.acquireStaticLock(context);

        Intent intent1 = new Intent(context, NotificationService.class);
        intent1.putExtra(ReminderManager.EVENT_EXTRA_KEY, event);
        intent1.setAction(NotificationService.ACTION_START_FOREGROUND_SERVICE);
        context.startForegroundService(intent1);
//        context.startService(intent1);
    }
}
