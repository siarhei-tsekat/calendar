package com.me.calendar.repository.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class HourWeeklyEvents {
    LocalTime time;
    Map<LocalDate, List<Event>> events;

    public HourWeeklyEvents(LocalTime time, Map<LocalDate, List<Event>> eventsDaily) {
        this.time = time;
        this.events = eventsDaily;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Map<LocalDate, List<Event>> getEvents() {
        return events;
    }

    public void setEvents(Map<LocalDate, List<Event>> events) {
        this.events = events;
    }
}
