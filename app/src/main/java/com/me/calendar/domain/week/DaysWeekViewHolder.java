package com.me.calendar.domain.week;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.me.calendar.OnItemClickListener;
import com.me.calendar.R;
import com.me.calendar.repository.model.Event;

import java.time.LocalDate;
import java.util.List;

public class DaysWeekViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final List<LocalDate> days;
    public final View parentView;
    public final TextView dayOfMonthTextView;

    private final OnItemClickListener onItemClickListener;

    public DaysWeekViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener, List<LocalDate> days) {
        super(itemView);
        this.days = days;
        this.parentView = itemView.findViewById(R.id.parentView);
        this.dayOfMonthTextView = itemView.findViewById(R.id.calendar_week_day);

        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}
