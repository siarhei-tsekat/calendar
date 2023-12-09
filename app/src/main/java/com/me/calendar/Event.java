package com.me.calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {

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

    private String name;
    private LocalDate date;
    private LocalTime time;


    public Event(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
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
}
