package com.me.calendar.screen;

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
import com.me.calendar.navigation.DayFragment;

import java.time.LocalDate;

public class DayPagerFragment extends Fragment {
    private ViewPager viewPager;
    private LocalDate now;
    private int currentItem = 50;

    public DayPagerFragment(LocalDate date) {
        this.now = date;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        viewPager = view.findViewById(R.id.fragment_view_pager);

        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();

        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {

                if (position > currentItem) {
                    int diff = Math.abs(position - currentItem);
                    return DayFragment.newInstance(now.plusDays(diff));

                } else if (position < currentItem) {
                    int diff = Math.abs(currentItem - position);
                    return DayFragment.newInstance(now.minusDays(diff));
                } else {
                    return DayFragment.newInstance(now);
                }
            }

            @Override
            public int getCount() {
                return 100;
            }

        });

        viewPager.setCurrentItem(currentItem);

        return view;
    }
}
