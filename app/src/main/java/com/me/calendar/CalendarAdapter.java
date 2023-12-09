package com.me.calendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<LocalDate> days;
    private final OnItemClickListener onItemClickListener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemClickListener onItemClickListener) {
        this.days = days;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (days.size() > 15) { // month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        } else { // week view
            layoutParams.height = parent.getHeight();
        }
        return new CalendarViewHolder(view, onItemClickListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        LocalDate date = days.get(position);

        holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

        if (date.equals(CalendarUtils.selectedDate)) {
//            holder.parentView.setBackgroundColor(Color.rgb(238,238,238));
//            holder.parentView.setBackground(new CircleDrawable(Color.rgb(238,238,238), Color.rgb(238,238,238), 0));
            holder.parentView.setBackgroundDrawable(new CircleDrawable(Color.rgb(238, 238, 238), Color.rgb(238, 238, 238), 50));
        }

        if (date.getMonth().equals(CalendarUtils.selectedDate.getMonth())) {
            holder.dayOfMonth.setTextColor(Color.BLACK);
        } else {
            holder.dayOfMonth.setTextColor(Color.LTGRAY);
        }
//        if (date.getDayOfWeek().getValue() == 4) {
//            holder.imageView.setImageDrawable(new CircleDrawable(Color.RED, Color.BLACK, 8));
//        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, LocalDate date);
    }
}
