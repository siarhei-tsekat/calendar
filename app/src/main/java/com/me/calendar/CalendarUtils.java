package com.me.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtils {
//    public static LocalDate selectedDate = LocalDate.now();

    public static ArrayList<LocalDate> daysInMonthArray(LocalDate localDate) {

        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(localDate);
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate prevMonth = localDate.minusMonths(1);
        LocalDate nextMonth = localDate.plusMonths(1);

        YearMonth prevYearMonth = YearMonth.from(prevMonth);
        int prevDaysInMonth = prevYearMonth.lengthOfMonth();


        LocalDate firstOfMonth = localDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {

            if (i <= dayOfWeek) {
                daysInMonthArray.add(LocalDate.of(prevMonth.getYear(), prevMonth.getMonth(), prevDaysInMonth + i - dayOfWeek));
            } else if (i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), i - dayOfWeek - daysInMonth));
            } else {
                daysInMonthArray.add(LocalDate.of(localDate.getYear(), localDate.getMonth(), i - dayOfWeek));
            }

        }
        return daysInMonthArray;
    }

    public static String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static String monthDayFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
        return date.format(formatter);
    }

    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate) {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayForDate(selectedDate);

        LocalDate endDate = current.plusWeeks(1);

        while (current.isBefore(endDate)) {
            days.add(current);
            current = current.plusDays(1);
        }

        return days;
    }

    private static LocalDate sundayForDate(LocalDate current) {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while (current.isAfter(oneWeekAgo)) {

            if (current.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return current;
            }

            current = current.minusDays(1);
        }

        return null;
    }

    public static String formattedDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    public static String formattedTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return time.format(formatter);
    }

    public static String formattedShortTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }
}
