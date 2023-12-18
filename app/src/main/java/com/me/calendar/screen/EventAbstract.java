package com.me.calendar.screen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.me.calendar.CalendarUtils;
import com.me.calendar.repository.model.EventRepeat;

import java.time.LocalDate;
import java.util.Calendar;

public class EventAbstract extends AppCompatActivity {

    protected TextView repeatEventFromTextView;
    protected TextView repeatEventTillTextView;
    protected LocalDate localDateEventRepeatFrom;
    protected LocalDate localDateEventRepeatTill;
    protected TextView repeatEventTextView;
    protected EventRepeat eventRepeat;
    protected LinearLayout eventPeriodRepeat;


    protected void initRepeatWidgets() {
        repeatEventFromTextView.setText(CalendarUtils.formattedDate(localDateEventRepeatFrom));
        repeatEventTillTextView.setText(CalendarUtils.formattedDate(localDateEventRepeatTill));
        eventPeriodRepeat.setVisibility(eventRepeat == EventRepeat.No ? View.INVISIBLE : View.VISIBLE);
    }

    protected void showRadioButtonDialog() {

        String[] eventRepeatNames = new String[5];

        eventRepeatNames[0] = EventRepeat.No.getValueName();
        eventRepeatNames[1] = EventRepeat.Every_day.getValueName();
        eventRepeatNames[2] = EventRepeat.Every_week.getValueName();
        eventRepeatNames[3] = EventRepeat.Every_month.getValueName();
        eventRepeatNames[4] = EventRepeat.Every_year.getValueName();

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setSingleChoiceItems(eventRepeatNames, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                repeatEventTextView.setText(eventRepeatNames[item]);
                eventRepeat = EventRepeat.fromString(eventRepeatNames[item]);

                eventPeriodRepeat.setVisibility(eventRepeat == EventRepeat.No ? View.INVISIBLE : View.VISIBLE);
                dialog.dismiss();
            }
        });

        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    protected void initDatePickerForRepeatFrom() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                localDateEventRepeatFrom = LocalDate.of(year, month, day);
                repeatEventFromTextView.setText(CalendarUtils.formattedDate(localDateEventRepeatFrom));
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day);

        repeatEventFromTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    protected void initDatePickerForRepeatTill() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                localDateEventRepeatTill = LocalDate.of(year, month, day);
                repeatEventTillTextView.setText(CalendarUtils.formattedDate(localDateEventRepeatTill));
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day);

        repeatEventTillTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }
}
