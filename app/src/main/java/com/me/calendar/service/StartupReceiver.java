package com.me.calendar.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.me.calendar.repository.model.Event;
import com.me.calendar.repository.model.EventRepeat;
import com.me.calendar.repository.model.PaletteColors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class StartupReceiver extends BroadcastReceiver {

    public static final String TAG = StartupReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Received broadcast intent: " + intent);
//        NotificationService.setServiceAlarm(context, true);

        ReminderManager reminderManager = new ReminderManager(context);
        Event event = new Event(1L, "event name, urlaub", LocalDate.now(), LocalTime.now(), EventRepeat.No, PaletteColors.Blue);
        reminderManager.setReminder(event);
    }
}
