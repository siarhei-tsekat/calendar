package com.me.calendar.repository.model;

import java.time.LocalTime;
import java.util.List;

public class HourWeeklyEvents {
    LocalTime time;
    List<List<Event>> events;

    public HourWeeklyEvents(LocalTime time, List<List<Event>> events) {
        this.time = time;
        this.events = events;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<List<Event>> getEvents() {
        return events;
    }

    public void setEvents(List<List<Event>> events) {
        this.events = events;
    }
}
