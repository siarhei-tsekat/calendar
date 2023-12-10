package com.me.calendar.navigation;

import static com.me.calendar.CalendarUtils.daysInMonthArray;
import static com.me.calendar.CalendarUtils.monthYearFromDate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.me.calendar.CalendarMonthAdapter;
import com.me.calendar.Event;
import com.me.calendar.OnItemClickListener;
import com.me.calendar.R;
import com.me.calendar.screen.DayPagerFragment;

import java.time.LocalDate;
import java.util.ArrayList;

public class MonthFragment extends Fragment implements OnItemClickListener {

    private static final String ARG_LOCAL_DATE = "MonthFragment.localDate";

    private TextView monthYearTex;
    private RecyclerView calendarRecycleView;
    private LocalDate localDate;

    public static Fragment newInstance(LocalDate localDate) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCAL_DATE, localDate);
        MonthFragment monthFragment = new MonthFragment();
        monthFragment.setArguments(args);
        return monthFragment;
    }

    private MonthFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localDate = (LocalDate) getArguments().getSerializable(ARG_LOCAL_DATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);

        initWidgets(view);
        setMonthView();

        return view;
    }

    private void setMonthView() {
        monthYearTex.setText(monthYearFromDate(localDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(localDate);
        ArrayList<Event> eventsForMonth = Event.eventsForMonth(localDate);
        CalendarMonthAdapter calendarMonthAdapter = new CalendarMonthAdapter(daysInMonth, this, localDate, eventsForMonth);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecycleView.setLayoutManager(layoutManager);
        calendarRecycleView.setAdapter(calendarMonthAdapter);
    }

    private void initWidgets(View view) {
        calendarRecycleView = view.findViewById(R.id.calendarRecyclerView);
        monthYearTex = view.findViewById(R.id.monthYearTV);
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DayPagerFragment(date)).commit();
        }
    }
}