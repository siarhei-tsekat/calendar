package com.me.calendar.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupReceiver extends BroadcastReceiver {

    public static final String TAG = StartupReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Received broadcast intent: " + intent);

//        ReminderManager reminderManager = new ReminderManager(context);
//        Event event = new Event(1L, "event name, urlaub", LocalDateTime.now(), new EventRepeat(EventRepeat.Repeat.No), PaletteColors.Blue);
//        reminderManager.setReminder(event);
    }
}
