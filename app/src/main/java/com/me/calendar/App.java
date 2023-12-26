package com.me.calendar;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import com.me.calendar.repository.EventService;
import com.me.calendar.repository.Store;
import com.me.calendar.repository.dao.EventsDao;
import com.me.calendar.repository.dao.NotificationsDao;

public class App extends Application {

    public static final String Notification_channel_id = "calendar_channel_id";
    private EventService eventService;

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        eventService = new EventService(getApplicationContext());

        NotificationChannel notificationChannel = new NotificationChannel(Notification_channel_id, "calendar notification channel", NotificationManager.IMPORTANCE_DEFAULT);
        getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);

    }

    public EventService getEventService() {
        return eventService;
    }
}
