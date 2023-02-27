package com.example.android.newstodaytimes.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.android.newstodaytimes.fragment.entertainmentFragment;
import com.example.android.newstodaytimes.fragment.healthFragment;
import com.example.android.newstodaytimes.fragment.homeFragment;
import com.example.android.newstodaytimes.fragment.scienceFragment;
import com.example.android.newstodaytimes.fragment.sportsFragment;
import com.example.android.newstodaytimes.fragment.technologyFragment;

public class PagerAdapter extends FragmentPagerAdapter {
int tabCount ;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount = behavior ;
    }
    @NonNull
    @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new homeFragment();
                case 1:
                    return new sportsFragment();
                case 2:
                    return new healthFragment();
                case 3:
                    return new entertainmentFragment();
                case 4:
                    return new scienceFragment();

                case 5:
                    return new technologyFragment();

                default:
                    return null;
            }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
