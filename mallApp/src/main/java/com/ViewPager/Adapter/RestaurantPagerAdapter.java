package com.ViewPager.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.mallapp.Fragments.RestaurantPagerFragment;


public class RestaurantPagerAdapter extends FragmentStatePagerAdapter {


	Context context;
	private ArrayList<String> TITLES;
	List<RestaurantPagerFragment> fragments;
	
	
	public RestaurantPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public RestaurantPagerAdapter(FragmentManager fm , Context context,
									List<RestaurantPagerFragment> fragments, ArrayList<String> TITLES) {
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
		RestaurantPagerFragment  f= this.fragments.get(position);
		return f;
	}
}