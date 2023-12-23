package com.me.calendar;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import com.me.calendar.repository.Store;
import com.me.calendar.repository.dao.EventsDao;
import com.me.calendar.service.NotificationService;

public class App extends Application {

    public static final String Notification_channel_id = "calendar_channel_id";
    private Store appDatabase;
    private EventsDao eventsDao;

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        appDatabase = new Store(getApplicationContext());

        eventsDao = new EventsDao(appDatabase);

        NotificationChannel notificationChannel = new NotificationChannel(Notification_channel_id, "calendar_channel_name", NotificationManager.IMPORTANCE_DEFAULT);
        getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);

//        if (!NotificationService.isServiceAlarmOn(this)) {
//        NotificationService.setServiceAlarm(this, true);
//        }
    }

    public EventsDao getEventsDao() {
        return eventsDao;
    }
}
