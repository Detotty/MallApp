package com.ViewPager.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mallapp.Fragments.OpeningHourPagerFragment;

public class OpeningHourPagerAdapter extends FragmentStatePagerAdapter {


	Context context;
	private ArrayList<String> TITLES;
	List<OpeningHourPagerFragment> fragments;
	
	
	public OpeningHourPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public OpeningHourPagerAdapter(FragmentManager fm , Context context,
									List<OpeningHourPagerFragment> fragments, ArrayList<String> TITLES) {
		super(fm);
		this.context= context;
		this.TITLES = TITLES;
		this.fragments	=	fragments;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return TITLES.get(position);
	}

	@Override
	public int getCount() {
		return TITLES.size();
	}
	
	
	@Override
	public Fragment getItem(int position) {
		OpeningHourPagerFragment  f= this.fragments.get(position);
		return f;
	}
}