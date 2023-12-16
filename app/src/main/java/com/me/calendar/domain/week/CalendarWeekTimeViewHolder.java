package com.me.calendar.domain.week;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.me.calendar.CalendarUtils;
import com.me.calendar.OnItemClickListener;
import com.me.calendar.R;
import com.me.calendar.repository.model.Event;

import java.time.LocalTime;
import java.util.List;

public class CalendarWeekTimeViewHolder extends CalendarViewHolder {

    private final List<Event> days;
    public final View parentView;
    public final TextView timeTextView;

    private final OnItemClickListener onItemClickListener;

    public CalendarWeekTimeViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener, List<Event> days) {
        super(itemView);
        this.days = days;
        this.parentView = itemView.findViewById(R.id.parentView);
        this.timeTextView = itemView.findViewById(R.id.cell_calendar_week_time);
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);

    }

    public void setTimeTextView(LocalTime time) {
        timeTextView.setText(CalendarUtils.formattedShortTime(time));
    }

    @Override
    public void onClick(View v) {
//        onItemClickListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}
