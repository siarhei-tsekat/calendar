package com.me.calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.me.calendar.repository.dao.EventsDao;

import java.time.LocalTime;
import java.util.Optional;

public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameEditText;
    private TextView evenDateTextView;
    private TextView evenTimeTextView;
    private LocalTime time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

        time = LocalTime.now();
        evenDateTextView.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        evenTimeTextView.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets() {
        eventNameEditText = findViewById(R.id.eventNameEditText);
        evenDateTextView = findViewById(R.id.eventDateTextView);
        evenTimeTextView = findViewById(R.id.eventTimeTextView);
    }

    public void saveEventAction(View view) {

        String eventName = eventNameEditText.getText().toString();
        Event event = new Event(eventName, CalendarUtils.selectedDate, time);
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