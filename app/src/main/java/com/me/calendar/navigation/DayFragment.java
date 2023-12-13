package com.me.calendar.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.me.calendar.CalendarUtils;
import com.me.calendar.Event;
import com.me.calendar.HourAdapter;
import com.me.calendar.HourEvent;
import com.me.calendar.NewEventActivity;
import com.me.calendar.OnItemClickListener;
import com.me.calendar.R;
import com.me.calendar.screen.MainActivity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class DayFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String ARG_LOCAL_DATE = "DayFragment.localDate";

    private TextView monthDayTex;
    private TextView dayOfWeekTextView;
    private ListView hourListView;
    private LocalDate localDate;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        setDayView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && localDate != null) {
            MainActivity.selectedDate = localDate;
        }
    }

    private void setDayView() {
        monthDayTex.setText(CalendarUtils.monthDayFromDate(localDate));
        String dayOfWeek = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTextView.setText(dayOfWeek);
        setHourAdapter();
    }

    private void setHourAdapter() {
        ArrayList<HourEvent> hourEvents = hourEventList();
        HourAdapter hourAdapter = new HourAdapter(getActivity(), hourEvents);
        hourListView.setAdapter(hourAdapter);
        hourListView.setOnItemClickListener(this);
        hourListView.setSelection(8);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HourEvent itemAtPosition = (HourEvent) hourListView.getItemAtPosition(position);
        Intent intent = NewEventActivity.newInstance(getContext(), localDate, itemAtPosition.getTime());
        startActivity(intent);
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