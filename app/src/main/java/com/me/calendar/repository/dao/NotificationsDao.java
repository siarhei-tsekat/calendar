package com.me.calendar.repository.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.me.calendar.repository.Store;
import com.me.calendar.repository.model.Event;
import com.me.calendar.repository.model.Notification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificationsDao {
    public static final String table_name = "notifications";

    public static String create_table_sql = """
            CREATE TABLE notifications (
            event_id INTEGER,
            event_date_time TEXT
            )
            """;

    private Store store;

    public NotificationsDao(Store store) {
        this.store = store;
    }

    public void addNotification(Notification notification) {
        SQLiteDatabase db = this.store.getWritableDatabase();

        LocalDateTime date = notification.getLocalDateTime();
        ContentValues values = new ContentValues();
        values.put("event_id", notification.getEventId());
        values.put("event_date_time", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        db.insert(table_name, null, values);
    }

    public void deleteById(long eventId) {
        SQLiteDatabase db = this.store.getWritableDatabase();

        db.delete(table_name, "event_id=?", new String[]{String.valueOf(eventId)});
    }

    public void deleteAllNotifications() {
        SQLiteDatabase db = this.store.getWritableDatabase();
        db.execSQL("Delete from  " + table_name);
    }

    public void updateNotificationForEvent(Event editEvent) {

    }

    public void dropTable() {
        SQLiteDatabase db = this.store.getWritableDatabase();
        db.execSQL("Drop TABLE " + table_name);
    }
}
