package com.me.calendar.domain.month;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.me.calendar.OnItemClickListener;
import com.me.calendar.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarMonthViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView dayOfMonthTextView;

    public TextView event_shape_1;
    public TextView event_shape_2;
    public TextView event_shape_3;

    private final OnItemClickListener onItemClickListener;

    public CalendarMonthViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener, ArrayList<LocalDate> days) {
        super(itemView);
        this.days = days;
        this.parentView = itemView.findViewById(R.id.parentView);
        this.dayOfMonthTextView = itemView.findViewById(R.id.cellDayText);

        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);

        this.event_shape_1 = itemView.findViewById(R.id.event_shape_1);
        this.event_shape_2 = itemView.findViewById(R.id.event_shape_2);
        this.event_shape_3 = itemView.findViewById(R.id.event_shape_3);
        event_shape_1.setVisibility(View.INVISIBLE);
        event_shape_2.setVisibility(View.INVISIBLE);
        event_shape_3.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}