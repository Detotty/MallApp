package com.mallapp.View;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ViewPager.Adapter.OpeningHourPagerAdapter;
import com.astuetz.PagerSlidingTabStrip;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Fragments.OpeningHourPagerFragment;
import com.mallapp.globel.GlobelMainMenu;
import com.mallapp.utils.Log;

public class OpeningHourInformation extends FragmentActivity  implements OnClickListener{
	
	String TAG = getClass().getCanonicalName();
	static Context context;
	private ViewPager pager;
	private PagerSlidingTabStrip tabs;
	
	private TextView heading;
	private ImageButton navigation_button;
	private OpeningHourPagerAdapter adapter;
	private ArrayList<String> TITLES= new ArrayList<String>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_menu_activity);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		addShopsTITLES();
		init();
		callInOnResume();
	}

	private void callInOnResume() {
		Log.e(TAG, "On Resme call");
		if(GlobelMainMenu.fragment_instences == null){
			createPagerIntence();
		}else{
			changeInstanceType();
		}
	}

	private void createPagerIntence() {
		List<OpeningHourPagerFragment> fragments = getFavouriteFragments();
		adapter = new OpeningHourPagerAdapter(getSupportFragmentManager(),context, fragments, TITLES);
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, 
										getResources().getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		pager.setOffscreenPageLimit(fragments.size());
		tabs.setViewPager(pager);
		
	}
	
	private List<OpeningHourPagerFragment> changeInstanceType() {
		List<OpeningHourPagerFragment> fragments	=	GlobelMainMenu.fragment_instences;
		adapter = new OpeningHourPagerAdapter(getSupportFragmentManager(),context, fragments, TITLES);
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, 
										getResources().getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		pager.setOffscreenPageLimit(fragments.size());
		tabs.setViewPager(pager);
		return fragments;
	}
	
	private List<OpeningHourPagerFragment> getFavouriteFragments() {
		if(TITLES.size()>0){
			List<OpeningHourPagerFragment> instances = new ArrayList<OpeningHourPagerFragment>();
			for(int i=0; i< TITLES.size() ; i++)
				instances.add(OpeningHourPagerFragment.newInstance(i, context));
			GlobelMainMenu.fragment_instences= instances;
			return instances;
		}
		return null;
	}
	
	
	private void init() {
		heading				= (TextView) findViewById(R.id.heading);
		heading.setText("Opening Hours & Information");
		
		navigation_button	= (ImageButton) findViewById(R.id.navigation);
		navigation_button.setOnClickListener(this);
		
		tabs 		= (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager 		= (ViewPager)  findViewById(R.id.pager);
		
		tabs.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				//Log.e(" offers tab fragment ", "onPageScrollStateChanged");
			}
			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
				//Log.e(" offers tab fragment ", "onPageScrolled" + position);
			}
			@Override
			public void onPageSelected(int position) {
				//Log.e(" offers tab fragment ", "onPageSelected" + position+" centers fav size = "+ TITLES.size());
				//setCenterLogo(position);
			}});
	}

	private void addShopsTITLES() {
		TITLES.add(MainMenuConstants.SELECTED_CENTER_NAME);
		TITLES.add("Shop X");
		TITLES.add("Shop Y");
		TITLES.add("Shop Z");
		GlobelMainMenu.TITLES= TITLES;
	}

	@Override
	public void onClick(View v) {
		if(navigation_button.getId() == v.getId()){
			finish();
		}
	}
}