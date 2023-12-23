package com.me.calendar.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.me.calendar.repository.model.Event;

public class OnAlarmReceiver extends BroadcastReceiver {

    public static final String TAG = OnAlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Received broadcast intent: " + intent);

//        Event event = intent.getExtras().getParcelable("event");
        WakeReminderIntentService.acquireStaticLock(context);

        Intent intent1 = new Intent(context, NotificationService.class);
//        intent1.putExtra("event", event);
        context.startForegroundService(intent1);
    }
}
