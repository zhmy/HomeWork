package com.zmy.gradledemo.rn;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zmy on 2016/11/8.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> list = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

        list.add(new HelloFragment());
        list.add(new Hello2Fragment());
        list.add(new Hello3Fragment());
        list.add(new NativeFragment());

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
