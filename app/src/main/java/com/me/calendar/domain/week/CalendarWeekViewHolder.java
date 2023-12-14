package com.me.calendar.domain.week;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.me.calendar.OnItemClickListener;
import com.me.calendar.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarWeekViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView dayOfMonthTextView;

    private final OnItemClickListener onItemClickListener;

    public CalendarWeekViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener, ArrayList<LocalDate> days) {
        super(itemView);
        this.days = days;
        this.parentView = itemView.findViewById(R.id.parentView);
        this.dayOfMonthTextView = itemView.findViewById(R.id.cellDayText);

        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}
