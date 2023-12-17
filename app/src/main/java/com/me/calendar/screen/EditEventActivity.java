package com.me.calendar.screen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.me.calendar.CalendarUtils;
import com.me.calendar.R;
import com.me.calendar.repository.model.Event;
import com.me.calendar.repository.model.EventRepeat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class EditEventActivity extends AppCompatActivity {

    private Event editEvent;

    private DatePickerDialog datePickerDialog;
    private MenuItem saveMenuBtn;
    private boolean saveEventMenuItemEnabled = false;
    public static final String EXTRA_EVENT = "EditEventActivity.event";

    private EditText eventNameEditText;
    private TextView evenDateTextView;
    private TextView evenTimeTextView;
    private LocalTime time;
    private LocalDate localDate;
    private TextView repeatEventTextView;
    private EventRepeat eventRepeat;

    public static Intent newInstance(Context context, Event event) {
        Intent intent = new Intent(context, EditEventActivity.class);
        intent.putExtra(EXTRA_EVENT, event);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        Toolbar myToolbar = findViewById(R.id.edit_event_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_menu_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initWidgets();

        editEvent = getIntent().getParcelableExtra(EXTRA_EVENT);
        localDate = editEvent.getDate();
        eventRepeat = editEvent.getEventRepeat();
        repeatEventTextView.setText(eventRepeat.getValueName());
        eventNameEditText.setText(editEvent.getName());
        evenDateTextView.setText(CalendarUtils.formattedDate(editEvent.getDate()));
        evenTimeTextView.setText(CalendarUtils.formattedTime(editEvent.getTime()));
        time = editEvent.getTime();

        initDatePicker();
        initTimePicker();
    }

    private void showRadioButtonDialog() {

        String[] grpname = new String[5];

        grpname[0] = EventRepeat.No.getValueName();
        grpname[1] = EventRepeat.Every_day.getValueName();
        grpname[2] = EventRepeat.Every_week.getValueName();
        grpname[3] = EventRepeat.Every_month.getValueName();
        grpname[4] = EventRepeat.Every_year.getValueName();

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setSingleChoiceItems(grpname, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                repeatEventTextView.setText(grpname[item]);
                eventRepeat = EventRepeat.fromString(grpname[item]);
                dialog.dismiss();
            }
        });

        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_event, menu);
        saveMenuBtn = menu.findItem(R.id.save_event_btn);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        saveMenuBtn.setEnabled(saveEventMenuItemEnabled);
        saveMenuBtn.getIcon().setAlpha(saveEventMenuItemEnabled ? 255 : 50);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.save_event_btn) {
            saveEvent();

            InputMethodManager imm = (InputMethodManager) EditEventActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = EditEventActivity.this.getCurrentFocus();
//            if (view == null) {
//                view = new View(activity);
//            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            finish();
            return true;
        }
        if (item.getItemId() == R.id.delete_event_btn) {
            deleteEvent();
            finish();
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
                saveEventMenuItemEnabled = true;
                invalidateOptionsMenu();
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

        TimePickerDialog timePickerDialog = new TimePickerDialog(EditEventActivity.this, AlertDialog.THEME_HOLO_LIGHT, onTimeSetListener, time.getHour(), time.getMinute(), true);
        timePickerDialog.setTitle("Select time");

        evenTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEventMenuItemEnabled = true;
                invalidateOptionsMenu();
                timePickerDialog.show();
            }
        });
    }

    private void initWidgets() {
        eventNameEditText = findViewById(R.id.eventNameEditText);
        evenDateTextView = findViewById(R.id.eventDateTextView);
        evenTimeTextView = findViewById(R.id.eventTimeTextView);
        repeatEventTextView = findViewById(R.id.repeat_event_textView);

        repeatEventTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRadioButtonDialog();
            }
        });
        eventNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveEventMenuItemEnabled = true;
                invalidateOptionsMenu();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void deleteEvent() {
        Event.events.removeIf(ev -> ev.getEventId() == editEvent.getEventId());
    }

    public void saveEvent() {

        String eventName = eventNameEditText.getText().toString();
        editEvent.setDate(localDate);
        editEvent.setTime(time);
        editEvent.setName(eventName);
        editEvent.setEventRepeat(eventRepeat);
        Event.events.removeIf(ev -> ev.getEventId() == editEvent.getEventId());
        Event.events.add(editEvent);
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