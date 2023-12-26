package com.me.calendar.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.me.calendar.ParcelableUtil;
import com.me.calendar.repository.model.Event;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class ReminderManager {

    public static final String EVENT_EXTRA_KEY = "event";

    private Context context;
    private AlarmManager alarmManager;

    public ReminderManager(Context context) {
        this.context = context;
        alarmManager = ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE));
    }

    public void setReminder(Event event) {
        Intent intent = new Intent(context, OnAlarmReceiver.class);
        byte[] bytes = ParcelableUtil.marshall(event);
        intent.putExtra(EVENT_EXTRA_KEY, bytes);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_MUTABLE);

        Calendar when = Calendar.getInstance();

        LocalDateTime localDateTime = event.getLocalDateTime();
        ZoneId zoneId = ZoneId.systemDefault();

        Date date = Date.from(localDateTime.atZone(zoneId).toInstant());
        when.setTime(date);

        alarmManager.set(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pendingIntent);
    }
}
