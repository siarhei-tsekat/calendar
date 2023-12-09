package com.me.calendar.model;

import java.util.Optional;

public class Event {

    private final int day;
    private final int month;
    private final int year;
    private final String eventName;
    private final Optional<String> eventNote;

    public Event(int day, int month, int year, String eventName, Optional<String> eventNote) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.eventName = eventName;
        this.eventNote = eventNote;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getEventName() {
        return eventName;
    }

    public Optional<String> getEventNote() {
        return eventNote;
    }
}
