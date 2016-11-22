package com.zmy.gradledemo.tab;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by zmy on 2016/11/11.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int PAGE_COUNT;
    private String tabTitles[];
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context, String[] tabTitles) {
        super(fm);
        this.context = context;
        this.tabTitles = tabTitles;
        PAGE_COUNT = tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
