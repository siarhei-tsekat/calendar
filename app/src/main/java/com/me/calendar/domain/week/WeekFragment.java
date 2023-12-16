package com.me.calendar.domain.week;

import static com.me.calendar.CalendarUtils.monthYearFromDate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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

import com.me.calendar.CalendarUtils;
import com.me.calendar.OnItemClickListener;
import com.me.calendar.R;
import com.me.calendar.domain.day.EventAdapter;
import com.me.calendar.repository.model.Event;
import com.me.calendar.repository.model.HourWeeklyEvents;
import com.me.calendar.screen.MainActivity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeekFragment extends Fragment implements OnItemClickListener {

    private static final String ARG_LOCAL_DATE = "WeekFragment.localDate";

    //    private TextView monthYearTex;
//    private RecyclerView weekDaysRecycleView;
    private LocalDate localDate;
    private TextView text_day_1;
    private TextView text_day_2;
    private TextView text_day_3;
    private TextView text_day_4;
    private TextView text_day_5;
    private TextView text_day_6;
    private TextView text_day_7;

    private RecyclerView calendarForWeekRecycleView;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private CalendarForWeekAdapter calendarForWeekAdapter;

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

        IntentFilter filter = new IntentFilter("NewEventActivity.newEventAdded");

        BroadcastReceiver smsBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LocalDate localDate = (LocalDate) intent.getSerializableExtra("localDate");
                calendarForWeekAdapter.update(hourEventList());

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        calendarForWeekAdapter.notifyDataSetChanged();
                    });
                }
//                calendarMonthAdapter.notifyItemChanged(5);

//                RecyclerView.ViewHolder viewHolder = calendarRecycleView.findViewHolderForAdapterPosition(5);
            }
        };

        requireActivity().registerReceiver(smsBroadcastReceiver, filter);
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
    }

    private void setWeekView() {

        ArrayList<LocalDate> days = CalendarUtils.daysInWeekArray(localDate);

        text_day_1.setText(String.valueOf(days.get(0).getDayOfMonth()));
        text_day_2.setText(String.valueOf(days.get(1).getDayOfMonth()));
        text_day_3.setText(String.valueOf(days.get(2).getDayOfMonth()));
        text_day_4.setText(String.valueOf(days.get(3).getDayOfMonth()));
        text_day_5.setText(String.valueOf(days.get(4).getDayOfMonth()));
        text_day_6.setText(String.valueOf(days.get(5).getDayOfMonth()));
        text_day_7.setText(String.valueOf(days.get(6).getDayOfMonth()));


//        if (currentCalendarDate.getMonth().equals(localDate.getMonth())) {
//            holder.dayOfMonthTextView.setTextColor(Color.BLACK);
//            if (currentCalendarDate.equals(currentDate) && LocalDate.now().equals(currentDate)) {
//                holder.dayOfMonthTextView.setTextColor(Color.rgb(66, 215, 245));
//            }
//        } else {
//            holder.dayOfMonthTextView.setTextColor(Color.LTGRAY);
//        }

//        ArrayList<Event> eventsForWeek = Event.eventsForWeek(localDate);
//        WeekDaysAdapter weekDaysAdapter = new WeekDaysAdapter(days, this, localDate, eventsForWeek);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
//        weekDaysRecycleView.setLayoutManager(layoutManager);
//        weekDaysRecycleView.setAdapter(weekDaysAdapter);


        calendarForWeekAdapter = new CalendarForWeekAdapter(this, localDate, hourEventList(), days);
        RecyclerView.LayoutManager layoutManagerForCalendar = new GridLayoutManager(getActivity().getApplicationContext(), 8);
        calendarForWeekRecycleView.setLayoutManager(layoutManagerForCalendar);
        calendarForWeekRecycleView.setAdapter(calendarForWeekAdapter);

        calendarForWeekRecycleView.scrollToPosition(8 * 8);
    }

    private void initWidgets(View view) {
//        weekDaysRecycleView = view.findViewById(R.id.weekDaysRecyclerView);
//        monthYearTex = view.findViewById(R.id.monthYearTV);
        text_day_1 = view.findViewById(R.id.week_day_1);
        text_day_2 = view.findViewById(R.id.week_day_2);
        text_day_3 = view.findViewById(R.id.week_day_3);
        text_day_4 = view.findViewById(R.id.week_day_4);
        text_day_5 = view.findViewById(R.id.week_day_5);
        text_day_6 = view.findViewById(R.id.week_day_6);
        text_day_7 = view.findViewById(R.id.week_day_7);

        calendarForWeekRecycleView = view.findViewById(R.id.calendarWeekRecyclerView);
        calendarForWeekRecycleView.setHasFixedSize(true);
        calendarForWeekRecycleView.setItemViewCacheSize(3);
        calendarForWeekRecycleView.setRecycledViewPool(viewPool);
    }

    private ArrayList<HourWeeklyEvents> hourEventList() {

        ArrayList<HourWeeklyEvents> list = new ArrayList<>();

        for (int hour = 0; hour < 24; hour++) {

            LocalTime time = LocalTime.of(hour, 0);
            ArrayList<LocalDate> weekDays = CalendarUtils.daysInWeekArray(localDate);

            ArrayList<Event> events = Event.eventsForWeekAndTime(localDate, time);
            Map<LocalDate, List<Event>> eventsDaily = new HashMap<>();

            for (LocalDate weekDay : weekDays) {
                eventsDaily.put(weekDay, new ArrayList<>());
            }

            for (Event event : events) {
                eventsDaily.get(event.getDate()).add(event);
            }

            HourWeeklyEvents hourEvent = new HourWeeklyEvents(time, eventsDaily);
            list.add(hourEvent);
        }

        return list;
    }
}