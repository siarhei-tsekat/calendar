package com.me.calendar.domain.month;

import static com.me.calendar.CalendarUtils.daysInMonthArray;
import static com.me.calendar.CalendarUtils.monthYearFromDate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.me.calendar.OnItemClickListener;
import com.me.calendar.R;
import com.me.calendar.domain.day.DayPagerFragment;
import com.me.calendar.repository.model.Event;
import com.me.calendar.screen.MainActivity;

import java.time.LocalDate;
import java.util.ArrayList;

public class MonthFragment extends Fragment implements OnItemClickListener {

    private static final String ARG_LOCAL_DATE = "MonthFragment.localDate";

    private RecyclerView calendarRecycleView;
    private LocalDate localDate;
    private CalendarMonthAdapter calendarMonthAdapter;

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


        IntentFilter filter = new IntentFilter("NewEventActivity.newEventAdded");

        BroadcastReceiver smsBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LocalDate localDate = (LocalDate) intent.getSerializableExtra("localDate");
                calendarMonthAdapter.update(Event.eventsForMonth(localDate));

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        calendarMonthAdapter.notifyDataSetChanged();
                    });
                }
//                calendarMonthAdapter.notifyItemChanged(5);

//                RecyclerView.ViewHolder viewHolder = calendarRecycleView.findViewHolderForAdapterPosition(5);
            }
        };

        requireActivity().registerReceiver(smsBroadcastReceiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.selectedDay = LocalDate.now();

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
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(localDate);
        ArrayList<Event> eventsForMonth = Event.eventsForMonth(localDate);
        calendarMonthAdapter = new CalendarMonthAdapter(daysInMonth, this, localDate, eventsForMonth);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);

        calendarRecycleView.setLayoutManager(layoutManager);
        calendarRecycleView.setAdapter(calendarMonthAdapter);
    }

    private void initWidgets(View view) {
        calendarRecycleView = view.findViewById(R.id.calendarRecyclerView);
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            MainActivity.selectedDay = date;
            NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
            navigationView.setCheckedItem(R.id.nav_day);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new DayPagerFragment(date))
                    .addToBackStack(null)
                    .commit();
        }
    }
}