package com.me.calendar.screen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.me.calendar.CalendarUtils;
import com.me.calendar.R;
import com.me.calendar.repository.model.EventRepeat;
import com.me.calendar.repository.model.PaletteColors;

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
    protected TextView colorPickerTextView;
    protected ImageView colorPickerImageView;

    protected TextView textViewBlue;
    protected TextView textViewLightBlue;
    protected TextView textViewGreen;
    protected TextView textViewOrange;
    protected TextView textViewYellow;
    protected TextView textViewRed;
    protected TextView textViewIndigo;
    protected TextView textViewViolet;
    protected TextView textViewPink;
    protected AlertDialog alertDialog;

    protected int chosenColor = PaletteColors.Blue;

    protected void initColorPicker(int colorRgb) {

        chosenColor = colorRgb;

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_colors, null);
        alertBuilder.setView(view);

        alertBuilder.create();

        colorPickerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = alertBuilder.show();
            }
        });
        textViewBlue = view.findViewById(R.id.event_color_textView_blue);
        textViewLightBlue = view.findViewById(R.id.event_color_textView_light_blue);
        textViewGreen = view.findViewById(R.id.event_color_textView_green);
        textViewOrange = view.findViewById(R.id.event_color_textView_orange);
        textViewYellow = view.findViewById(R.id.event_color_textView_yellow);
        textViewRed = view.findViewById(R.id.event_color_textView_red);
        textViewIndigo = view.findViewById(R.id.event_color_textView_indigo);
        textViewViolet = view.findViewById(R.id.event_color_textView_violet);
        textViewPink = view.findViewById(R.id.event_color_textView_pink);

        textViewBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawableCompat.setTint(DrawableCompat.wrap(colorPickerImageView.getDrawable()), ContextCompat.getColor(EventAbstract.this, R.color.eventCircleColorBlue));
                chosenColor = PaletteColors.Blue;
                alertDialog.dismiss();
            }
        });
        textViewLightBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawableCompat.setTint(DrawableCompat.wrap(colorPickerImageView.getDrawable()), ContextCompat.getColor(EventAbstract.this, R.color.eventCircleColorLightBlue));
                chosenColor = PaletteColors.LightBlue;
                alertDialog.dismiss();
            }
        });
        textViewGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawableCompat.setTint(DrawableCompat.wrap(colorPickerImageView.getDrawable()), ContextCompat.getColor(EventAbstract.this, R.color.eventCircleColorGreen));
                chosenColor = PaletteColors.Green;
                alertDialog.dismiss();
            }
        });
        textViewOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawableCompat.setTint(DrawableCompat.wrap(colorPickerImageView.getDrawable()), ContextCompat.getColor(EventAbstract.this, R.color.eventCircleColorOrange));
                chosenColor = PaletteColors.Orange;
                alertDialog.dismiss();
            }
        });
        textViewYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawableCompat.setTint(DrawableCompat.wrap(colorPickerImageView.getDrawable()), ContextCompat.getColor(EventAbstract.this, R.color.eventCircleColorYellow));
                chosenColor = PaletteColors.Yellow;
                alertDialog.dismiss();
            }
        });
        textViewRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawableCompat.setTint(DrawableCompat.wrap(colorPickerImageView.getDrawable()), ContextCompat.getColor(EventAbstract.this, R.color.eventCircleColorRed));
                chosenColor = PaletteColors.Red;
                alertDialog.dismiss();
            }
        });
        textViewIndigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawableCompat.setTint(DrawableCompat.wrap(colorPickerImageView.getDrawable()), ContextCompat.getColor(EventAbstract.this, R.color.eventCircleColorIndigo));
                chosenColor = PaletteColors.Indigo;
                alertDialog.dismiss();
            }
        });
        textViewViolet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawableCompat.setTint(DrawableCompat.wrap(colorPickerImageView.getDrawable()), ContextCompat.getColor(EventAbstract.this, R.color.eventCircleColorViolet));
                chosenColor = PaletteColors.Violet;
                alertDialog.dismiss();
            }
        });
        textViewPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawableCompat.setTint(DrawableCompat.wrap(colorPickerImageView.getDrawable()), ContextCompat.getColor(EventAbstract.this, R.color.eventCircleColorPink));
                chosenColor = PaletteColors.Pink;
                alertDialog.dismiss();
            }
        });

    }

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
