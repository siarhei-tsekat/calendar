package com.me.calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.me.calendar.repository.dao.EventsDao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class EventEditActivity extends AppCompatActivity {

    public static final String EXTRA_LOCAL_DATE = "EventEditActivity.local_date";

    private EditText eventNameEditText;
    private TextView evenDateTextView;
    private TextView evenTimeTextView;
    private LocalTime time;
    private LocalDate localDate;

    public static Intent newInstance(Context context, LocalDate localDate) {
        Intent intent = new Intent(context, EventEditActivity.class);
        intent.putExtra(EXTRA_LOCAL_DATE, localDate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

        localDate = ((LocalDate) getIntent().getSerializableExtra(EXTRA_LOCAL_DATE));
        time = LocalTime.now();
        evenDateTextView.setText("Date: " + CalendarUtils.formattedDate(localDate));
        evenTimeTextView.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets() {
        eventNameEditText = findViewById(R.id.eventNameEditText);
        evenDateTextView = findViewById(R.id.eventDateTextView);
        evenTimeTextView = findViewById(R.id.eventTimeTextView);
    }

    public void saveEventAction(View view) {

        String eventName = eventNameEditText.getText().toString();
        Event event = new Event(eventName, localDate, time);
        Event.events.add(event);

        finish();
    }

//    public void saveNewEvent(View view) {
//
//        EventsDao eventsDao = App.getInstance().getEventsDao();
//
//        com.me.calendar.model.Event event = new com.me.calendar.model.Event(
//                dayDetails.getDay(),
//                dayDetails.getMonth(),
//                dayDetails.getYear(),
//                eventNameText.getText().toString(),
//                Optional.of(eventNoteText.getText().toString()));
//
//        eventsDao.addNewEvent(event);
//
//        finish();
//    }
}