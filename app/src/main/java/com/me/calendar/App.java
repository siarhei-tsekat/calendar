package com.me.calendar;

import android.app.Application;

import com.me.calendar.repository.Store;
import com.me.calendar.repository.dao.EventsDao;

public class App extends Application {

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
    }

    public EventsDao getEventsDao() {
        return eventsDao;
    }
}
