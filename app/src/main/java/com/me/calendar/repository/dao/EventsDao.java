package com.me.calendar.repository.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.me.calendar.repository.Store;
import com.me.calendar.repository.model.Event;
import com.me.calendar.repository.model.EventRepeat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

public class EventsDao {

    public static final String table_name = "events";

    public static String create_table_sql = """
            CREATE TABLE events (
            event_id INTEGER,
            event_timestamp TEXT,
            event_name TEXT,
            event_repeat INTEGER,
            event_repeat_from TEXT,
            event_repeat_till TEXT,
            event_color INTEGER
            )""";

    private final Store store;

    public EventsDao(Store store) {
        this.store = store;
    }

    public void addNewEvent(Event event) {

        SQLiteDatabase db = this.store.getWritableDatabase();

        LocalDateTime timestamp = event.getLocalDateTime();

        ContentValues values = new ContentValues();
        values.put("event_id", event.getEventId());
        values.put("event_timestamp", timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        values.put("event_name", event.getName());
        values.put("event_repeat", event.getEventRepeat().getRepeat().getId());
        values.put("event_repeat_from", event.getEventRepeat().getFrom().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        values.put("event_repeat_till", event.getEventRepeat().getTill().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        values.put("event_color", event.getColor());

        db.insert(table_name, null, values);
    }

    public void deleteById(long eventId) {

        SQLiteDatabase db = this.store.getWritableDatabase();

        db.delete(table_name, "event_id=?", new String[]{String.valueOf(eventId)});
    }

    public ArrayList<Event> selectByDateTime(LocalDate date, LocalTime time) {

        SQLiteDatabase db = this.store.getReadableDatabase();
        String formattedDate = LocalDateTime.of(date, time).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
        Cursor cursor = db.rawQuery("SELECT * from " + table_name + " where strftime('%Y-%m-%d %H', event_timestamp)='" + formattedDate + "'", null);

        ArrayList<Event> res = new ArrayList<>();

        while (cursor.moveToNext()) {

            long event_id = cursor.getLong(0);
            LocalDateTime dateTime = LocalDateTime.parse(cursor.getString(1), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String event_name = cursor.getString(2);
            EventRepeat.Repeat repeat = EventRepeat.Repeat.fromId(cursor.getInt(3));

            LocalDateTime longFrom = LocalDateTime.parse(cursor.getString(4), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime longTill = LocalDateTime.parse(cursor.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            EventRepeat eventRepeat = new EventRepeat(repeat, longFrom, longTill);
            int eventColor = cursor.getInt(6);
            Event event = new Event(event_id, event_name, dateTime, eventRepeat, eventColor);
            res.add(event);
        }

        cursor.close();

        return res;
    }

    public void deleteAllEvents() {
        SQLiteDatabase db = this.store.getWritableDatabase();
        db.execSQL("Delete from  " + table_name);
    }

    public ArrayList<Event> selectForMonth(LocalDate date) {

        SQLiteDatabase db = this.store.getReadableDatabase();
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        Cursor cursor = db.rawQuery("SELECT * from " + table_name + " where strftime('%Y-%m', event_timestamp)='" + formattedDate + "'", null);

        ArrayList<Event> res = new ArrayList<>();

        while (cursor.moveToNext()) {

            long event_id = cursor.getLong(0);
            LocalDateTime dateTime = LocalDateTime.parse(cursor.getString(1), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String event_name = cursor.getString(2);
            EventRepeat.Repeat repeat = EventRepeat.Repeat.fromId(cursor.getInt(3));

            LocalDateTime longFrom = LocalDateTime.parse(cursor.getString(4), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime longTill = LocalDateTime.parse(cursor.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            EventRepeat eventRepeat = new EventRepeat(repeat, longFrom, longTill);
            int eventColor = cursor.getInt(6);
            Event event = new Event(event_id, event_name, dateTime, eventRepeat, eventColor);
            res.add(event);
        }

        cursor.close();

        return res;
    }

    public ArrayList<Event> selectForWeekAndTime(LocalDate date, LocalTime time) {

        SQLiteDatabase db = this.store.getReadableDatabase();
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        Cursor cursor = db.rawQuery("SELECT * from " + table_name + " where strftime('%Y-%m', event_timestamp)='" + formattedDate + "'", null);

        ArrayList<Event> res = new ArrayList<>();

        while (cursor.moveToNext()) {

            long event_id = cursor.getLong(0);
            LocalDateTime dateTime = LocalDateTime.parse(cursor.getString(1), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            if (!inCurrentWeek(date, dateTime)) continue;
            if (dateTime.getHour() != time.getHour()) continue;

            String event_name = cursor.getString(2);
            EventRepeat.Repeat repeat = EventRepeat.Repeat.fromId(cursor.getInt(3));

            LocalDateTime longFrom = LocalDateTime.parse(cursor.getString(4), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime longTill = LocalDateTime.parse(cursor.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            EventRepeat eventRepeat = new EventRepeat(repeat, longFrom, longTill);
            int eventColor = cursor.getInt(6);
            Event event = new Event(event_id, event_name, dateTime, eventRepeat, eventColor);
            res.add(event);
        }

        cursor.close();

        return res;
    }

    public void updateEvent(Event editEvent) {

    }

    public void dropTable() {
        SQLiteDatabase db = this.store.getWritableDatabase();
        db.execSQL("Drop TABLE " + table_name);
    }

    private boolean inCurrentWeek(LocalDate date, LocalDateTime eventDate) {

        int dayOfMonth_a = date.getDayOfMonth();
        int dayOfMonth_b = eventDate.getDayOfMonth();

        if (Math.abs(dayOfMonth_a - dayOfMonth_b) > 7) return false;

        int weekOfYear_a = date.get(WeekFields.of(Locale.getDefault()).weekOfYear());
        int weekOfYear_b = eventDate.get(WeekFields.of(Locale.getDefault()).weekOfYear());

        return weekOfYear_a == weekOfYear_b;
    }
}
