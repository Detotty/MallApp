package com.ViewPager.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mallapp.Fragments.OfferPagerTabFragment;
import com.mallapp.SharedPreferences.SharedPreferenceFavourites;

public class OffersNewsPagerAdapter extends FragmentStatePagerAdapter{

	Handler ui;
	String headerFilter;
	List<OfferPagerTabFragment> fragments;
	Context context;
	private ArrayList<String> TITLES;
	
	public OffersNewsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	
	public OffersNewsPagerAdapter(FragmentManager fm , Context context, Handler uiHandler, 
			String audienceFilter, List<OfferPagerTabFragment> fragments) {
		super(fm);
		ui= uiHandler;
		this.context= context;
		this.TITLES = SharedPreferenceFavourites.getFavouritesList(context);
		this.headerFilter= audienceFilter;
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
		OfferPagerTabFragment  f= this.fragments.get(position);
		return f;
	}

	
}