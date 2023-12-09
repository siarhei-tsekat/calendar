package com.me.calendar.navigation;

import static com.me.calendar.CalendarUtils.daysInMonthArray;
import static com.me.calendar.CalendarUtils.monthYearFromDate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.me.calendar.CalendarAdapter;
import com.me.calendar.CalendarUtils;
import com.me.calendar.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class MonthFragment extends Fragment implements CalendarAdapter.OnItemClickListener {
    private ViewPager viewPager;

    private TextView monthYearTex;
    private RecyclerView calendarRecycleView;
    private Button prevMonthBtn;
    private Button nextMonthBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);

        initWidgets(view);
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        return view;
    }

    private void setMonthView() {
        monthYearTex.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray();
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecycleView.setLayoutManager(layoutManager);
        calendarRecycleView.setAdapter(calendarAdapter);
    }

    private void initWidgets(View view) {
        calendarRecycleView = view.findViewById(R.id.calendarRecyclerView);
        monthYearTex = view.findViewById(R.id.monthYearTV);
        prevMonthBtn = view.findViewById(R.id.prev_month_btn);
        nextMonthBtn = view.findViewById(R.id.next_month_btn);

        prevMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
                setMonthView();
            }
        });
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }
}