package com.me.calendar;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity {

    private Event editEvent;

    enum Mode {
        NEW, EDIT;
    }

    private Mode mode;

    public static final String EXTRA_LOCAL_DATE = "EventEditActivity.local_date";
    public static final String EXTRA_EVENT = "EventEditActivity.event";

    private EditText eventNameEditText;
    private TextView evenDateTextView;
    private TextView evenTimeTextView;
    private LocalTime time;
    private LocalDate localDate;
    private Button deleteEventBtn;

    public static Intent newInstance(Context context, LocalDate localDate) {
        Intent intent = new Intent(context, EventEditActivity.class);
        intent.putExtra(EXTRA_LOCAL_DATE, localDate);
        return intent;
    }

    public static Intent newInstance(Context context, Event event) {
        Intent intent = new Intent(context, EventEditActivity.class);
        intent.putExtra(EXTRA_EVENT, event);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

        if (getIntent().hasExtra(EXTRA_LOCAL_DATE)) {
            mode = Mode.NEW;
            localDate = ((LocalDate) getIntent().getSerializableExtra(EXTRA_LOCAL_DATE));
            time = LocalTime.now();
            evenDateTextView.setText(CalendarUtils.formattedDate(localDate));
            evenTimeTextView.setText(CalendarUtils.formattedTime(time));
            deleteEventBtn.setEnabled(false);
            deleteEventBtn.setBackgroundColor(Color.LTGRAY);

        } else if (getIntent().hasExtra(EXTRA_EVENT)) {

            editEvent = getIntent().getParcelableExtra(EXTRA_EVENT);
            localDate = editEvent.getDate();
            eventNameEditText.setText(editEvent.getName());
            evenDateTextView.setText(CalendarUtils.formattedDate(editEvent.getDate()));
            evenTimeTextView.setText(CalendarUtils.formattedTime(editEvent.getTime()));
            time = editEvent.getTime();
            mode = Mode.EDIT;
        }

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time = LocalTime.of(selectedHour, selectedMinute);
                evenTimeTextView.setText(CalendarUtils.formattedTime(time));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(EventEditActivity.this, AlertDialog.THEME_HOLO_LIGHT, onTimeSetListener, time.getHour(), time.getMinute(), true);
        timePickerDialog.setTitle("Select time");

        evenTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });
    }

    private void initWidgets() {
        eventNameEditText = findViewById(R.id.eventNameEditText);
        evenDateTextView = findViewById(R.id.eventDateTextView);
        evenTimeTextView = findViewById(R.id.eventTimeTextView);
        deleteEventBtn = findViewById(R.id.deleteEventBtn);
        deleteEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event.events.removeIf(ev -> ev.getEventId() == editEvent.getEventId());
                finish();
            }
        });
    }

    public void saveEventAction(View view) {

        String eventName = eventNameEditText.getText().toString();

        if (mode == Mode.NEW) {
            Event event = new Event(System.nanoTime(), eventName, localDate, time);
            Event.events.add(event);
        } else if (mode == Mode.EDIT) {
            editEvent.setDate(localDate);
            editEvent.setTime(time);
            editEvent.setName(eventName);
            Event.events.removeIf(ev -> ev.getEventId() == editEvent.getEventId());
            Event.events.add(editEvent);
        }
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