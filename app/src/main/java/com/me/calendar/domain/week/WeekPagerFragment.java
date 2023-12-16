package com.me.calendar.domain.week;

import static com.me.calendar.CalendarUtils.monthYearFromDate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.me.calendar.R;
import com.me.calendar.screen.MainActivity;

import java.time.LocalDate;

public class WeekPagerFragment extends Fragment {
    private ViewPager viewPager;
    private LocalDate now = LocalDate.now();
    private int currentItem = 5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        viewPager = view.findViewById(R.id.fragment_view_pager);

        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();

        viewPager.setAdapter(new FragmentStatePagerAdapter(supportFragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {

                if (position > currentItem) {
                    int diff = Math.abs(position - currentItem);
                    return WeekFragment.newInstance(now.plusWeeks(diff));

                } else if (position < currentItem) {
                    int diff = Math.abs(currentItem - position);
                    return WeekFragment.newInstance(now.minusWeeks(diff));
                } else {
                    return WeekFragment.newInstance(now);
                }
            }

            @Override
            public int getCount() {
                return 10;
            }

        });

        viewPager.setCurrentItem(currentItem);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(monthYearFromDate(now));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LocalDate date;

                if (position > currentItem) {
                    int diff = Math.abs(position - currentItem);
                    date = now.plusWeeks(diff);

                } else if (position < currentItem) {
                    int diff = Math.abs(currentItem - position);
                    date = now.minusWeeks(diff);
                } else {
                    date = now;
                }
                ((MainActivity) getActivity()).getSupportActionBar().setTitle(monthYearFromDate(date));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }
}
