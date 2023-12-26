package com.me.calendar.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

public class EventRepeat implements Parcelable {

    public static final Creator<EventRepeat> CREATOR = new Creator<EventRepeat>() {
        @Override
        public EventRepeat createFromParcel(Parcel in) {
            return new EventRepeat(in);
        }

        @Override
        public EventRepeat[] newArray(int size) {
            return new EventRepeat[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(repeat.getId());
        dest.writeSerializable(from);
        dest.writeSerializable(till);
    }

    protected EventRepeat(Parcel in) {
        repeat = Repeat.fromId(in.readInt());
        from = (LocalDateTime) (in.readSerializable());
        till = (LocalDateTime) (in.readSerializable());
    }

    public enum Repeat {
        No("No repeat", 0),
        Every_day("Every day", 1),
        Every_week("Every week", 2),
        Every_month("Every month", 3),
        Every_year("Every year", 4);

        private final String valueName;
        private final int id;

        Repeat(String valueName, int id) {
            this.valueName = valueName;
            this.id = id;
        }

        public static Repeat fromString(String v) {
            for (Repeat repeat : Repeat.values()) {
                if (repeat.valueName.equals(v)) {
                    return repeat;
                }
            }

            return null;
        }

        public static Repeat fromId(int id) {
            for (Repeat repeat : Repeat.values()) {
                if (repeat.id == id) {
                    return repeat;
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

    private Repeat repeat;
    private LocalDateTime from;
    private LocalDateTime till;

    public EventRepeat(Repeat repeat, LocalDateTime from, LocalDateTime till) {
        this.repeat = repeat;
        this.from = from;
        this.till = till;
    }

    public EventRepeat(Repeat repeat) {
        this.repeat = repeat;
        this.from = LocalDateTime.now();
        this.till = LocalDateTime.now();
    }

    public Repeat getRepeat() {
        return repeat;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTill() {
        return till;
    }
}
