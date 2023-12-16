package com.me.calendar.domain.week;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public CalendarViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}