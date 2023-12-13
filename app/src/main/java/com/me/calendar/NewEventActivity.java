package com.me.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class NewEventActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private MenuItem saveEventMenuItem;
    private boolean saveEventMenuItemEnabled = false;

    public static final String EXTRA_LOCAL_DATE = "NewEventActivity.local_date";
    public static final String EXTRA_LOCAL_TIME = "NewEventActivity.local_time";

    private EditText eventNameEditText;
    private TextView evenDateTextView;
    private TextView evenTimeTextView;
    private LocalTime time;
    private LocalDate localDate;

    public static Intent newInstance(Context context, LocalDate localDate, LocalTime time) {
        Intent intent = new Intent(context, NewEventActivity.class);
        intent.putExtra(EXTRA_LOCAL_DATE, localDate);
        intent.putExtra(EXTRA_LOCAL_TIME, time);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        Toolbar myToolbar = findViewById(R.id.edit_event_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("New Event");
        initWidgets();

        localDate = ((LocalDate) getIntent().getSerializableExtra(EXTRA_LOCAL_DATE));
        time = ((LocalTime) getIntent().getSerializableExtra(EXTRA_LOCAL_TIME));
        evenDateTextView.setText(CalendarUtils.formattedDate(localDate));
        evenTimeTextView.setText(CalendarUtils.formattedTime(time));

        initDatePicker();
        initTimePicker();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        saveEventMenuItem = menu.findItem(R.id.done_dark_btn);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        saveEventMenuItem.setEnabled(saveEventMenuItemEnabled);
        saveEventMenuItem.getIcon().setAlpha(saveEventMenuItemEnabled ? 255 : 50);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.done_dark_btn) {
            saveEvent();
            Intent intent = new Intent("NewEventActivity.newEventAdded");
            intent.putExtra("localDate", localDate);
            sendBroadcast(intent);

            InputMethodManager imm = (InputMethodManager) NewEventActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = NewEventActivity.this.getCurrentFocus();
//            if (view == null) {
//                view = new View(activity);
//            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventNameEditText.requestFocus();
    }

    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                localDate = LocalDate.of(year, month, day);
                evenDateTextView.setText(CalendarUtils.formattedDate(localDate));
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day);

        evenDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time = LocalTime.of(selectedHour, selectedMinute);
                evenTimeTextView.setText(CalendarUtils.formattedTime(time));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(NewEventActivity.this, AlertDialog.THEME_HOLO_LIGHT, onTimeSetListener, time.getHour(), time.getMinute(), true);
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

        eventNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                saveEventMenuItemEnabled = s != null && !s.toString().trim().equals("");
                invalidateOptionsMenu();
            }
        });
    }

    public void saveEvent() {
        String eventName = eventNameEditText.getText().toString();
        Event event = new Event(System.nanoTime(), eventName, localDate, time);
        Event.events.add(event);
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