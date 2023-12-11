package com.me.calendar.navigation;

import static com.me.calendar.CalendarUtils.monthYearFromDate;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.calendar.CalendarUtils;
import com.me.calendar.CalendarWeekAdapter;
import com.me.calendar.Event;
import com.me.calendar.EventAdapter;
import com.me.calendar.EventEditActivity;
import com.me.calendar.OnItemClickListener;
import com.me.calendar.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekFragment extends Fragment implements OnItemClickListener {

    private static final String ARG_LOCAL_DATE = "WeekFragment.localDate";

    private TextView monthYearTex;
    private RecyclerView calendarRecycleView;
    private ListView eventListView;

    private Button addEventBtn;
    private LocalDate localDate;

    public static Fragment newInstance(LocalDate localDate) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCAL_DATE, localDate);
        WeekFragment weekFragment = new WeekFragment();
        weekFragment.setArguments(args);
        return weekFragment;
    }

    private WeekFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localDate = (LocalDate) getArguments().getSerializable(ARG_LOCAL_DATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        initWidgets(view);
        setWeekView();

        return view;
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        localDate = date;
        setWeekView();
    }

    @Override
    public void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        ArrayList<Event> dailyEvents = Event.eventsForDate(localDate);
        EventAdapter eventAdapter = new EventAdapter(getActivity().getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    private void setWeekView() {
        monthYearTex.setText(monthYearFromDate(localDate));
        ArrayList<LocalDate> days = CalendarUtils.daysInWeekArray(localDate);
        ArrayList<Event> eventsForWeek = Event.eventsForWeek(localDate);
        CalendarWeekAdapter calendarMonthAdapter = new CalendarWeekAdapter(days, this, localDate, eventsForWeek);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecycleView.setLayoutManager(layoutManager);
        calendarRecycleView.setAdapter(calendarMonthAdapter);
        setEventAdapter();
    }

    private void initWidgets(View view) {
        calendarRecycleView = view.findViewById(R.id.calendarRecyclerView);
        monthYearTex = view.findViewById(R.id.monthYearTV);
        eventListView = view.findViewById(R.id.eventListView);
        addEventBtn = view.findViewById(R.id.add_event_btn);

//        prevWeekBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
//                setWeekView();
//            }
//        });
//
//        nextWeekBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
//                setWeekView();
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
}