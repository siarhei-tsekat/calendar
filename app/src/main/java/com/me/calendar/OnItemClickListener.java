package com.me.calendar;

import java.time.LocalDate;

public interface OnItemClickListener {
    void onItemClick(int position, LocalDate date);
}
