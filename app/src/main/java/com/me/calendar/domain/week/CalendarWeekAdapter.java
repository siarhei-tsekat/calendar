package com.me.calendar.domain.week;

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

public class CalendarWeekAdapter extends RecyclerView.Adapter<CalendarWeekViewHolder> {

    private final ArrayList<LocalDate> days;
    private final OnItemClickListener onItemClickListener;
    private LocalDate currentDate;
    private ArrayList<Event> currentEvents;

    public CalendarWeekAdapter(ArrayList<LocalDate> days, OnItemClickListener onItemClickListener, LocalDate currentDate, ArrayList<Event> currentEvents) {
        this.days = days;
        this.onItemClickListener = onItemClickListener;
        this.currentDate = currentDate;
        this.currentEvents = currentEvents;
    }

    @NonNull
    @Override
    public CalendarWeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.calendar_week_cell, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = parent.getHeight();
        return new CalendarWeekViewHolder(view, onItemClickListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarWeekViewHolder holder, int position) {
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
    }

    private List<Event> getAmountEventsForDay(ArrayList<Event> currentEvents, LocalDate currentCalendarDate) {
        return currentEvents.stream().filter(event -> event.getDate().isEqual(currentCalendarDate)).collect(Collectors.toList());
    }


    @Override
    public int getItemCount() {
        return days.size();
    }
}
