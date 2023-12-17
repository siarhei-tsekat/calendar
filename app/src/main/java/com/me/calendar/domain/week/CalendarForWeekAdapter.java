package com.me.calendar.domain.week;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.me.calendar.OnItemClickListener;
import com.me.calendar.R;
import com.me.calendar.repository.model.Event;
import com.me.calendar.repository.model.HourWeeklyEvents;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

enum ColumnType {
    Time,
    Day
}

public class CalendarForWeekAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<HourWeeklyEvents> eventList;
    private ArrayList<LocalDate> days;

    public CalendarForWeekAdapter(OnItemClickListener onItemClickListener, ArrayList<HourWeeklyEvents> eventList, ArrayList<LocalDate> days) {
        this.onItemClickListener = onItemClickListener;
        this.eventList = eventList;
        this.days = days;
        days.add(0, null);
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ColumnType.Time.ordinal()) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.calendar_week_time_cell, parent, false);

            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = (int) (parent.getHeight() * 0.1);
            return new CalendarWeekTimeViewHolder(view, (i, d) -> {
            }, new ArrayList<>());
        } else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.calendar_week_grid_cell, parent, false);

            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = (int) (parent.getHeight() * 0.1);

            return new CalendarWeekViewHolder(view, onItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {

        if (position % 8 == 0) {
            LocalTime time = getTimeForPosition(position);
            ((CalendarWeekTimeViewHolder) holder).setTimeTextView(time);
        } else {
            LocalTime time = getTimeForPosition(position);

            List<HourWeeklyEvents> eventsForCurrentHour = eventList.stream().filter(ev -> ev.getTime().getHour() == time.getHour()).collect(Collectors.toList());

            int day = position % 8;

            LocalDate currentCalendarDate = days.get(day);

            List<Event> events = eventsForCurrentHour.stream().map(e -> e.getEvents().get(currentCalendarDate)).flatMap(l -> l.stream()).collect(Collectors.toList());
            ((CalendarWeekViewHolder) holder).clear();
            ((CalendarWeekViewHolder) holder).setEventsForTheTimeAndDate(events);
            ((CalendarWeekViewHolder) holder).setLocalDate(currentCalendarDate);
        }
    }

    private LocalTime getTimeForPosition(int position) {
        int hour = position / 8;
        return LocalTime.of(hour, 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 8 == 0) {
            return ColumnType.Time.ordinal();
        } else {
            return ColumnType.Day.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return 8 * 24;
    }

    public void update(List<HourWeeklyEvents> events) {
        this.eventList = events;
    }
}
