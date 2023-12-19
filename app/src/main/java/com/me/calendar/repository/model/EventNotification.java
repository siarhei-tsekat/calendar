package com.me.calendar.repository.model;

public enum EventNotification {
    alarm_no("none", 0),
    alarm_10_min_before("10 min before", 1),
    alarm_30_min_before("30 min before", 2),
    alarm_1_hour_before("1 hr before", 3),
    alarm_1_day_before("1 day before", 4);

    private String name;
    private int id;

    EventNotification(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static EventNotification fromString(String item) {
        EventNotification[] values = EventNotification.values();
        for (EventNotification value : values) {
            if (value.name.equals(item)) {
                return value;
            }
        }

        return null;
    }

    public static EventNotification fromId(int id) {
        EventNotification[] values = EventNotification.values();
        for (EventNotification value : values) {
            if (value.id == id) {
                return value;
            }
        }

        return null;
    }

    public String value() {
        return name;
    }

    public int getId() {
        return id;
    }
}
