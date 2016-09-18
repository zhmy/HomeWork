package com.zmy.gradledemo.ala;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zmy.gradledemo.R;
import com.zmy.gradledemo.ala.adapter.PersonViewPagerAdapter;

/**
 * Created by zmy on 16/9/15.
 */
public class PersonWrapperFragment extends Fragment {

    private PersonViewPagerAdapter viewPagerAdapter;
    private ViewPager viewpager;
    private int position = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_person_wrapper, null);
        initView(rootView);
        viewPagerAdapter = new PersonViewPagerAdapter(getChildFragmentManager());
        viewpager.setAdapter(viewPagerAdapter);
        viewpager.setCurrentItem(position);
        return rootView;
    }

    private void initView(View rootView) {
        viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
    }
}
