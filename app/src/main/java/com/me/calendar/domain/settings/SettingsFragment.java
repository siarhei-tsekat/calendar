package com.me.calendar.domain.settings;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.me.calendar.App;
import com.me.calendar.R;
import com.me.calendar.repository.model.PaletteColors;
import com.me.calendar.screen.MainActivity;

public class SettingsFragment extends Fragment {

    private Button deleteAllEventsBtn;
    private Button deleteAllTablesBtn;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        deleteAllEventsBtn = view.findViewById(R.id.delete_all_events_btn);
        deleteAllTablesBtn = view.findViewById(R.id.delete_all_tables_btn);

        buttonEffect(deleteAllEventsBtn);
        buttonEffect(deleteAllTablesBtn);

        deleteAllEventsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getInstance().getEventService().deleteAllEvents();
                Toast.makeText(SettingsFragment.this.getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        deleteAllTablesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getInstance().getEventService().deleteAllTables();
                Toast.makeText(SettingsFragment.this.getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Settings");

        return view;
    }

    public static void buttonEffect(View button) {
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(PaletteColors.Blue, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }
}