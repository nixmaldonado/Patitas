package com.example.patitas.pets;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.patitas.R;

public class FragmentPager extends FragmentPagerAdapter {

    private static final int NUMBER_OF_PAGES = 3;

    private String tabTitles[];

    public FragmentPager(FragmentManager fm, Context context) {
        super(fm);
        this.tabTitles = context.getResources().getStringArray(R.array.tabs_title_array);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PetsFragment();
        } else if (position == 1) {
            return new PetsFragment();
        } else {
            return new UserPetsFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.tabTitles[position];
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }
}
