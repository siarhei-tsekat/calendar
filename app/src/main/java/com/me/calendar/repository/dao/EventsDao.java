package com.me.calendar.repository.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.me.calendar.repository.Store;
import com.me.calendar.repository.model.Event;

public class EventsDao {

    public static String create_table_sql = "CREATE TABLE events (day INTEGER, month INTEGER, year INTEGER, event_name TEXT, event_note TEXT)";

    private final Store store;

    public EventsDao(Store store) {
        this.store = store;
    }

    public void addNewEvent(Event event) {

        SQLiteDatabase db = this.store.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("day", event.getDate().getDayOfMonth());
        values.put("month", event.getDate().getMonth().getValue());
        values.put("year", event.getDate().getYear());
        values.put("event_name", event.getName());

        db.insert("events", null, values);
    }
}
