package com.me.calendar.screen;

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
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

import com.me.calendar.App;
import com.me.calendar.CalendarUtils;
import com.me.calendar.R;
import com.me.calendar.repository.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

public class EditEventActivity extends EventAbstract {

    private Event editEvent;
    private MenuItem saveMenuBtn;
    private boolean saveEventMenuItemEnabled = false;
    public static final String EXTRA_EVENT = "EditEventActivity.event";

    private EditText eventNameEditText;
    private TextView evenDateTextView;
    private TextView evenTimeTextView;
    private LocalTime time;
    private LocalDateTime localDate;

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
        localDate = editEvent.getLocalDateTime();
        eventRepeat = editEvent.getEventRepeat();
        repeatEventTextView.setText(eventRepeat.getRepeat().getValueName());
        eventNameEditText.setText(editEvent.getName());
        evenDateTextView.setText(CalendarUtils.formattedDate(editEvent.getLocalDateTime().toLocalDate()));
        evenTimeTextView.setText(CalendarUtils.formattedTime(editEvent.getLocalDateTime().toLocalTime()));
        time = editEvent.getLocalDateTime().toLocalTime();

        localDateEventRepeatFrom = editEvent.getLocalDateEventRepeatFrom() != null ?
                editEvent.getLocalDateEventRepeatFrom() :
                LocalDate.now();
        localDateEventRepeatTill = editEvent.getLocalDateEventRepeatTill() != null ?
                editEvent.getLocalDateEventRepeatTill() :
                LocalDate.now();

        eventNotification = editEvent.getEventNotification();

        initRepeatWidgets();

        initDatePicker();
        initTimePicker();
        initDatePickerForRepeatFrom();
        initDatePickerForRepeatTill();

        DrawableCompat.setTint(DrawableCompat.wrap(colorPickerImageView.getDrawable()), editEvent.getColor());

        initColorPicker(editEvent.getColor());

        initAlarm();
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
                localDate = LocalDateTime.of(year, month, day, 0, 0, 0);
                evenDateTextView.setText(CalendarUtils.formattedDate(localDate.toLocalDate()));
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day);

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
        eventPeriodRepeat = findViewById(R.id.event_repeat_period_layout);
        repeatEventFromTextView = findViewById(R.id.repeat_event_from);
        repeatEventTillTextView = findViewById(R.id.repeat_event_till);
        colorPickerTextView = findViewById(R.id.event_color_textView);
        colorPickerImageView = findViewById(R.id.event_color_image_view);
        alarmTextView = findViewById(R.id.event_alarm_text_view);

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
        App.getInstance().getEventService().deleteEventById(editEvent.getEventId());
    }

    public void saveEvent() {

        String eventName = eventNameEditText.getText().toString();
        editEvent.setLocalDateTime(localDate);
        editEvent.setName(eventName);
        editEvent.setEventRepeat(eventRepeat);
        editEvent.setEventRepeatFrom(localDateEventRepeatFrom);
        editEvent.setEventRepeatTill(localDateEventRepeatTill);
        editEvent.setEventColor(chosenColor);
        editEvent.setEventNotification(eventNotification);
        App.getInstance().getEventService().updateEvent(editEvent);
    }
}