package com.me.calendar.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.me.calendar.R;
import com.me.calendar.domain.day.DayPagerFragment;
import com.me.calendar.domain.month.MonthPagerFragment;
import com.me.calendar.domain.settings.SettingsFragment;
import com.me.calendar.domain.week.WeekPagerFragment;

import java.time.LocalDate;
import java.time.LocalTime;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static LocalDate selectedDay = LocalDate.now();

    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;
    private WeekPagerFragment weekPagerFragment;
    private MonthPagerFragment monthPagerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("");

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MonthPagerFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_month);
        }

        fab = findViewById(R.id.add_event_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewEventActivity.newInstance(MainActivity.this, selectedDay, LocalTime.now());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_month) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MonthPagerFragment()).commit();
        } else if (item.getItemId() == R.id.nav_week) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WeekPagerFragment()).commit();
        } else if (item.getItemId() == R.id.nav_day) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DayPagerFragment(LocalDate.now())).commit();
        } else if (item.getItemId() == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
        } else if (item.getItemId() == R.id.nav_exit) {
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}