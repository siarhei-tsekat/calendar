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

import com.me.calendar.CalendarUtils;
import com.me.calendar.Event;
import com.me.calendar.EventEditActivity;
import com.me.calendar.HourAdapter;
import com.me.calendar.HourEvent;
import com.me.calendar.R;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class DayFragment extends Fragment {

    private static final String ARG_LOCAL_DATE = "DayFragment.localDate";

    private TextView monthDayTex;
    private TextView dayOfWeekTextView;
    private ListView hourListView;
    private LocalDate localDate;

    private Button addEventBtn;

    public static Fragment newInstance(LocalDate localDate) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCAL_DATE, localDate);
        DayFragment dayFragment = new DayFragment();
        dayFragment.setArguments(args);
        return dayFragment;
    }

    private DayFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localDate = (LocalDate) getArguments().getSerializable(ARG_LOCAL_DATE);
    }

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
        addEventBtn = view.findViewById(R.id.add_event_btn_day_view);

//        prevDayBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
//                setDayView();
//            }
//        });
//
//        nextDayBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
//                setDayView();
//            }
//        });

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EventEditActivity.newInstance(getActivity(), localDate);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setDayView();
    }

    private void setDayView() {
        monthDayTex.setText(CalendarUtils.monthDayFromDate(localDate));
        String dayOfWeek = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
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
            ArrayList<Event> events = Event.eventsForDateAndTime(localDate, time);
            HourEvent hourEvent = new HourEvent(time, events);
            list.add(hourEvent);
        }

        return list;
    }

}