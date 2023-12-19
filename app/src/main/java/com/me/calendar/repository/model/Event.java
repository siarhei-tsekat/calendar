package com.me.calendar.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

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

    public static ArrayList<Event> eventsForWeekAndTime(LocalDate date, LocalTime time) {
        ArrayList<Event> dayEvents = new ArrayList<>();

        for (Event event : events) {

            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            int year = date.getYear();
            Month month = date.getMonth();
            boolean belongToCurrentWeek = inCurrentWeek(date, event.getDate());

            if (event.getDate().getYear() == year &&
                    event.getDate().getMonth().equals(month) &&
                    eventHour == cellHour &&
                    belongToCurrentWeek) {
                dayEvents.add(event);
            }
        }
        return dayEvents;
    }

    private static boolean inCurrentWeek(LocalDate date, LocalDate eventDate) {

        int dayOfMonth_a = date.getDayOfMonth();
        int dayOfMonth_b = eventDate.getDayOfMonth();

        if (Math.abs(dayOfMonth_a - dayOfMonth_b) > 7) return false;

        int weekOfYear_a = date.get(WeekFields.of(Locale.getDefault()).weekOfYear());
        int weekOfYear_b = eventDate.get(WeekFields.of(Locale.getDefault()).weekOfYear());

        return weekOfYear_a == weekOfYear_b;
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
    private EventRepeat eventRepeat;
    private EventNotification eventNotification;
    protected LocalDate localDateEventRepeatFrom;
    protected LocalDate localDateEventRepeatTill;
    protected int eventColor;

    public Event(long eventId, String name, LocalDate date, LocalTime time, EventRepeat eventRepeat, int eventColor) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.time = time;
        this.eventRepeat = eventRepeat;
        this.eventColor = eventColor;
    }

    public void setEventRepeat(EventRepeat eventRepeat) {
        this.eventRepeat = eventRepeat;
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

    public void setEventColor(int eventColor) {
        this.eventColor = eventColor;
    }

    public int getEventColor() {
        return eventColor;
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
        dest.writeInt(eventRepeat.getId());
        dest.writeInt(eventNotification.getId());
        dest.writeInt(eventColor);
        dest.writeSerializable(localDateEventRepeatFrom);
        dest.writeSerializable(localDateEventRepeatTill);
    }

    private void readFromParcel(Parcel in) {

        eventId = in.readLong();
        name = in.readString();
        date = (LocalDate) in.readSerializable();
        time = (LocalTime) in.readSerializable();
        eventRepeat = EventRepeat.fromId(in.readInt());
        eventNotification = EventNotification.fromId(in.readInt());
        eventColor = in.readInt();
        localDateEventRepeatFrom = (LocalDate) in.readSerializable();
        localDateEventRepeatTill = (LocalDate) in.readSerializable();
    }

    public EventRepeat getEventRepeat() {
        return eventRepeat;
    }

    public void setEventRepeatFrom(LocalDate date) {
        localDateEventRepeatFrom = date;
    }

    public void setEventRepeatTill(LocalDate date) {
        localDateEventRepeatTill = date;
    }

    public LocalDate getLocalDateEventRepeatFrom() {
        return localDateEventRepeatFrom;
    }

    public LocalDate getLocalDateEventRepeatTill() {
        return localDateEventRepeatTill;
    }

    public int getColor() {
        return eventColor;
    }

    public void setEventNotification(EventNotification eventNotification) {
        this.eventNotification = eventNotification;
    }

    public EventNotification getEventNotification() {
        return eventNotification;
    }
}
