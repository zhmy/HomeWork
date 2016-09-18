package com.zmy.gradledemo.ala.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.zmy.gradledemo.ala.PersonListFragment;

public class PersonViewPagerAdapter extends FragmentStatePagerAdapter {

	private SparseArray<Fragment> fragments = new SparseArray<Fragment>();
	private int count = 3;

	public PersonViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = getFragmentByIndex(position);
		if (fragment == null) {
			fragment = new PersonListFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("position", position);
			fragment.setArguments(bundle);
			fragments.put(position, fragment);
		}
		return fragment;
	}

	@Override
	public int getItemPosition(Object object) {
		return PagerAdapter.POSITION_NONE;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "";
	}

	@Override
	public int getCount() {
		return count;
	}

	public Fragment getFragmentByIndex(int index) {
		if (index < fragments.size()) {
			return fragments.get(index);
		}
		return null;
	}
}
