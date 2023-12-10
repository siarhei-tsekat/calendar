package com.me.calendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarWeekAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

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
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.calendar_cell, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = parent.getHeight();
        return new CalendarViewHolder(view, onItemClickListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        LocalDate date = days.get(position);

        holder.dayOfMonthTextView.setText(String.valueOf(date.getDayOfMonth()));

        if (date.getMonth().equals(currentDate.getMonth())) {
            holder.dayOfMonthTextView.setTextColor(Color.BLACK);
            if (date.equals(currentDate) && LocalDate.now().equals(currentDate)) {
//            holder.parentView.setBackgroundDrawable(new CircleDrawable(Color.rgb(179, 224, 255), Color.rgb(179, 224, 255), 25));
                holder.dayOfMonthTextView.setTextColor(Color.rgb(219, 29, 158));
            }
        } else {
            holder.dayOfMonthTextView.setTextColor(Color.LTGRAY);
        }

        if (date.getDayOfMonth() == 4) {
            holder.event_shape_1.setVisibility(View.VISIBLE);
            holder.event_shape_2.setVisibility(View.VISIBLE);
            holder.event_shape_2.setBackgroundColor(Color.rgb(7, 176, 137));
        }

        if (date.getDayOfMonth() == 5) {
            holder.event_shape_1.setVisibility(View.VISIBLE);
            holder.event_shape_2.setVisibility(View.VISIBLE);
            holder.event_shape_2.setBackgroundColor(Color.rgb(7, 176, 137));
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }
}
