package com.me.calendar.repository.model;

import java.time.LocalDateTime;

public class Notification {

    private long eventId;
    private LocalDateTime date;

    public Notification(long eventId, LocalDateTime date) {
        this.eventId = eventId;
        this.date = date;
    }

    public long getEventId() {
        return eventId;
    }

    public LocalDateTime getLocalDateTime() {
        return date;
    }
}
