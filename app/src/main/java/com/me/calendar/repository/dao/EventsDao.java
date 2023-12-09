package com.me.calendar.repository.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.me.calendar.model.Event;
import com.me.calendar.repository.Store;

public class EventsDao {

    public static String create_table_sql = "CREATE TABLE events (day INTEGER, month INTEGER, year INTEGER, event_name TEXT, event_note TEXT)";

    private final Store store;

    public EventsDao(Store store) {
        this.store = store;
    }

    public void addNewEvent(Event event) {

        SQLiteDatabase db = this.store.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("day", event.getDay());
        values.put("month", event.getMonth());
        values.put("year", event.getYear());
        values.put("event_name", event.getEventName());
        values.put("event_note", event.getEventNote().orElse(""));

        db.insert("events", null, values);
    }
}
