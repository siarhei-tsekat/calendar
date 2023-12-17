package com.me.calendar.domain.week;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.me.calendar.OnItemClickListener;
import com.me.calendar.R;
import com.me.calendar.repository.model.Event;

import java.time.LocalDate;
import java.util.List;


public class CalendarWeekViewHolder extends CalendarViewHolder implements View.OnClickListener {

    private List<Event> events;
    public final View parentView;
    private final OnItemClickListener onItemClickListener;
    private TextView calendar_week_event_1;
    private TextView calendar_week_event_2;
    private TextView calendar_week_event_3;
    private LocalDate localDate;

    public CalendarWeekViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);
        this.parentView = itemView.findViewById(R.id.parentView);

        this.calendar_week_event_1 = itemView.findViewById(R.id.calendar_week_event_1);
        this.calendar_week_event_2 = itemView.findViewById(R.id.calendar_week_event_2);
        this.calendar_week_event_3 = itemView.findViewById(R.id.calendar_week_event_3);

        calendar_week_event_1.setVisibility(View.INVISIBLE);
        calendar_week_event_2.setVisibility(View.INVISIBLE);
        calendar_week_event_3.setVisibility(View.INVISIBLE);

        clear();

        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public void setEventsForTheTimeAndDate(List<Event> events) {
        this.events = events;

        if (events.size() >= 1) {
            calendar_week_event_1.setVisibility(View.VISIBLE);
            calendar_week_event_1.setText(events.get(0).getName());
        }

        if (events.size() >= 2) {
            calendar_week_event_2.setVisibility(View.VISIBLE);
            calendar_week_event_2.setText(events.get(1).getName());
        }

        if (events.size() >= 3) {
            calendar_week_event_3.setVisibility(View.VISIBLE);
            calendar_week_event_3.setText(events.get(2).getName());
        }
//        calendar_week_event_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainActivity.selectedDate = events.get(0).getDate();
//                NavigationView navigationView = parentView.findViewById(R.id.nav_view);
//                navigationView.setCheckedItem(R.id.nav_day);
//                parentView.getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, new DayPagerFragment(events.get(0).getDate()))
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
//        calendar_week_event_2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        calendar_week_event_3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(getAdapterPosition(), localDate);
    }

    public void clear() {
        calendar_week_event_1.setVisibility(View.INVISIBLE);
        calendar_week_event_2.setVisibility(View.INVISIBLE);
        calendar_week_event_3.setVisibility(View.INVISIBLE);
    }
}
