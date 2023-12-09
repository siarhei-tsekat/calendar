package com.me.calendar.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;

public class DayDetails implements Parcelable {

    private final int day;
    private final int month;
    private final int year;

    public DayDetails(LocalDate date) {
        day = date.getDayOfMonth();
        month = date.getMonthValue();
        year = date.getYear();
    }

    protected DayDetails(Parcel in) {
        day = in.readInt();
        month = in.readInt();
        year = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(day);
        dest.writeInt(month);
        dest.writeInt(year);
    }

    public static final Creator<DayDetails> CREATOR = new Creator<DayDetails>() {
        @Override
        public DayDetails createFromParcel(Parcel in) {
            return new DayDetails(in);
        }

        @Override
        public DayDetails[] newArray(int size) {
            return new DayDetails[size];
        }
    };
}
