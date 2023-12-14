package com.me.calendar.domain.month;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.me.calendar.repository.model.Event;
import com.me.calendar.OnItemClickListener;
import com.me.calendar.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarMonthAdapter extends RecyclerView.Adapter<CalendarMonthViewHolder> {

    private final ArrayList<LocalDate> days;
    private final OnItemClickListener onItemClickListener;
    private LocalDate currentDate;
    private ArrayList<Event> currentEvents;

    public CalendarMonthAdapter(ArrayList<LocalDate> days, OnItemClickListener onItemClickListener, LocalDate currentDate, ArrayList<Event> currentEvents) {
        this.days = days;
        this.onItemClickListener = onItemClickListener;
        this.currentDate = currentDate;
        this.currentEvents = currentEvents;
    }

    @NonNull
    @Override
    public CalendarMonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.calendar_cell, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarMonthViewHolder(view, onItemClickListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarMonthViewHolder holder, int position) {
        LocalDate currentCalendarDate = days.get(position);

        holder.dayOfMonthTextView.setText(String.valueOf(currentCalendarDate.getDayOfMonth()));

        if (currentCalendarDate.getMonth().equals(currentDate.getMonth())) {
            holder.dayOfMonthTextView.setTextColor(Color.BLACK);
            if (currentCalendarDate.equals(currentDate) && LocalDate.now().equals(currentDate)) {
                holder.dayOfMonthTextView.setTextColor(Color.rgb(66, 215, 245));
            }
        } else {
            holder.dayOfMonthTextView.setTextColor(Color.LTGRAY);
        }

        List<Event> eventsForDay = getAmountEventsForDay(currentEvents, currentCalendarDate);

        if (eventsForDay.size() > 0) {
            holder.event_shape_1.setVisibility(View.VISIBLE);
            holder.event_shape_1.setText(eventsForDay.get(0).getName());
        }

        if (eventsForDay.size() > 1) {
            holder.event_shape_2.setVisibility(View.VISIBLE);
            holder.event_shape_2.setBackgroundColor(Color.rgb(7, 176, 137));
            holder.event_shape_2.setText(eventsForDay.get(1).getName());
        }

        if (eventsForDay.size() > 2) {
            holder.event_shape_3.setVisibility(View.VISIBLE);
            holder.event_shape_3.setBackgroundColor(Color.rgb(133, 50, 168));
            holder.event_shape_3.setText(eventsForDay.get(2).getName());
        }

    }

    private List<Event> getAmountEventsForDay(ArrayList<Event> currentEvents, LocalDate currentCalendarDate) {
        return currentEvents.stream().filter(event -> event.getDate().isEqual(currentCalendarDate)).collect(Collectors.toList());
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public void update(ArrayList<Event> events) {
        currentEvents = events;
    }
}
