package com.me.calendar.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

public class Event implements Parcelable {

    public static ArrayList<Event> events = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date) {
        ArrayList<Event> dayEvents = new ArrayList<>();

        for (Event event : events) {
            if (event.getDate().equals(date)) {
                dayEvents.add(event);
            }
        }
        return dayEvents;
    }

    public static ArrayList<Event> eventsForMonth(LocalDate date) {
        ArrayList<Event> dayEvents = new ArrayList<>();

        int year = date.getYear();
        Month month = date.getMonth();

        for (Event event : events) {
            if (event.getDate().getYear() == year && event.getDate().getMonth().equals(month)) {
                dayEvents.add(event);
            }
        }
        return dayEvents;
    }

    // fix me
    public static ArrayList<Event> eventsForWeek(LocalDate date) {
        ArrayList<Event> dayEvents = new ArrayList<>();

        int year = date.getYear();
        Month month = date.getMonth();

        for (Event event : events) {
            if (event.getDate().getYear() == year && event.getDate().getMonth().equals(month)) {
                dayEvents.add(event);
            }
        }
        return dayEvents;
    }

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time) {
        ArrayList<Event> dayEvents = new ArrayList<>();

        for (Event event : events) {

            int eventHour = event.time.getHour();
            int cellHour = time.getHour();

            if (event.getDate().equals(date) && eventHour == cellHour) {
                dayEvents.add(event);
            }
        }
        return dayEvents;
    }

    private long eventId;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Event(long eventId, String name, LocalDate date, LocalTime time) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Event(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<Event> CREATOR = new Parcelable.Creator<Event>() {

        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[0];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(eventId);
        dest.writeString(name);
        dest.writeSerializable(date);
        dest.writeSerializable(time);
    }

    private void readFromParcel(Parcel in) {

        eventId = in.readLong();
        name = in.readString();
        date = (LocalDate) in.readSerializable();
        time = (LocalTime) in.readSerializable();
    }
}
