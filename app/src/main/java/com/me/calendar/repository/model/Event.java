package com.me.calendar.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event implements Parcelable {

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

        dest.writeParcelable(eventRepeat, flags);
        dest.writeLong(eventId);
        dest.writeString(name);
        dest.writeSerializable(localDateTime);
        dest.writeInt(eventNotification.getId());
        dest.writeInt(eventColor);
        dest.writeSerializable(localDateEventRepeatFrom);
        dest.writeSerializable(localDateEventRepeatTill);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Event(Parcel in) {
        eventRepeat = in.readParcelable(EventRepeat.class.getClassLoader());
        eventId = in.readLong();
        name = in.readString();
        localDateTime = (LocalDateTime) in.readSerializable();
        eventNotification = EventNotification.fromId(in.readInt());
        eventColor = in.readInt();
        localDateEventRepeatFrom = (LocalDate) in.readSerializable();
        localDateEventRepeatTill = (LocalDate) in.readSerializable();
    }

    private long eventId;
    private String name;
    private LocalDateTime localDateTime;
    private EventRepeat eventRepeat;
    private EventNotification eventNotification;
    protected LocalDate localDateEventRepeatFrom = LocalDate.now();
    protected LocalDate localDateEventRepeatTill = LocalDate.now();
    protected int eventColor;

    public Event(long eventId, String name, LocalDateTime localDateTime, EventRepeat eventRepeat, int eventColor) {
        this.eventId = eventId;
        this.name = name;
        this.localDateTime = localDateTime;
        this.eventRepeat = eventRepeat;
        this.eventColor = eventColor;
        this.eventNotification = EventNotification.alarm_no;
    }

    public void setEventRepeat(EventRepeat repeat) {
        this.eventRepeat = repeat;
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setEventColor(int eventColor) {
        this.eventColor = eventColor;
    }

    public int getEventColor() {
        return eventColor;
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
