package com.me.calendar.repository.model;

public enum EventRepeat {
    No("No repeat", 0),
    Every_day("Every day", 1),
    Every_week("Every week", 2),
    Every_month("Every month", 3),
    Every_year("Every year", 4);

    private final String valueName;
    private final int id;

    EventRepeat(String valueName, int id) {
        this.valueName = valueName;
        this.id = id;
    }

    public static EventRepeat fromString(String v) {
        for (EventRepeat eventRepeat : EventRepeat.values()) {
            if (eventRepeat.valueName.equals(v)) {
                return eventRepeat;
            }
        }

        return null;
    }

    public static EventRepeat fromId(int id) {
        for (EventRepeat eventRepeat : EventRepeat.values()) {
            if (eventRepeat.id == id) {
                return eventRepeat;
            }
        }

        return null;
    }

    public String getValueName() {
        return valueName;
    }

    public int getId() {
        return id;
    }
}
