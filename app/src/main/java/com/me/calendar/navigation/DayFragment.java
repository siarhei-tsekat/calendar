package com.me.calendar.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.me.calendar.App;
import com.me.calendar.CalendarUtils;
import com.me.calendar.Event;
import com.me.calendar.EventEditActivity;
import com.me.calendar.HourAdapter;
import com.me.calendar.HourEvent;
import com.me.calendar.R;
import com.me.calendar.repository.dao.EventsDao;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

public class DayFragment extends Fragment {

    private TextView monthDayTex;
    private TextView dayOfWeekTextView;
    private ListView hourListView;

    private Button prevDayBtn;
    private Button nextDayBtn;
    private Button addEventBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        initWidgets(view);
        return view;
    }


    private void initWidgets(View view) {
        monthDayTex = view.findViewById(R.id.monthDayText);
        dayOfWeekTextView = view.findViewById(R.id.dayOfWeeTextView);
        hourListView = view.findViewById(R.id.hourListView);
        prevDayBtn = view.findViewById(R.id.prev_day_btn);
        nextDayBtn = view.findViewById(R.id.next_day_btn);
        addEventBtn = view.findViewById(R.id.add_event_btn_day_view);

        prevDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
                setDayView();
            }
        });

        nextDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
                setDayView();
            }
        });

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EventEditActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setDayView();
    }

    private void setDayView() {
        monthDayTex.setText(CalendarUtils.monthDayFromDate(CalendarUtils.selectedDate));
        String dayOfWeek = CalendarUtils.selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTextView.setText(dayOfWeek);
        setHourAdapter();
    }

    private void setHourAdapter() {
        HourAdapter hourAdapter = new HourAdapter(getActivity().getApplicationContext(), hourEventList());
        hourListView.setAdapter(hourAdapter);
    }

    private ArrayList<HourEvent> hourEventList() {

        ArrayList<HourEvent> list = new ArrayList<>();

        for (int hour = 0; hour < 24; hour++) {

            LocalTime time = LocalTime.of(hour, 0);
            ArrayList<Event> events = Event.eventsForDateAndTime(CalendarUtils.selectedDate, time);
            HourEvent hourEvent = new HourEvent(time, events);
            list.add(hourEvent);
        }

        return list;
    }

}