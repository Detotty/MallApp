package com.mallapp.View;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ViewPager.Adapter.RestaurantPagerAdapter;
import com.astuetz.PagerSlidingTabStrip;
import com.mallapp.Fragments.RestaurantPagerFragment;
import com.mallapp.globel.GlobelRestaurants;
import com.mallapp.utils.Log;



public class RestaurantMenuActivity extends FragmentActivity implements OnClickListener{

	String TAG = getClass().getCanonicalName();
	static Context context;
	private ViewPager pager;
	private PagerSlidingTabStrip tabs;
	
	private TextView heading;
	private ImageButton navigation_button;
	private RestaurantPagerAdapter adapter;
	private ArrayList<String> TITLES= new ArrayList<String>();

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_menu_activity);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		addMenuTITLES();
		init();
		callInOnResume();
	}

	private void init() {
	
		heading				= (TextView) findViewById(R.id.heading);
		heading.setText("Menu");
		navigation_button	= (ImageButton) findViewById(R.id.back);
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



	private void addMenuTITLES() {
		if(GlobelRestaurants.TITLES == null || GlobelRestaurants.TITLES.size()== 0){
			TITLES.add("Breakfast");
			TITLES.add("Lunch");
			TITLES.add("Dinner");
			TITLES.add("Italina");
			GlobelRestaurants.TITLES= TITLES;
		}else{
			TITLES= GlobelRestaurants.TITLES;
		}
	}

	private void callInOnResume() {
		Log.e(TAG, "On Resme call");
		if(GlobelRestaurants.fragment_instences == null){
			createPagerIntence();
		}else{
			changeInstanceType();
		}
	}

	private void createPagerIntence() {
		List<RestaurantPagerFragment> fragments= getFavouriteFragments();
		adapter = new RestaurantPagerAdapter(getSupportFragmentManager(),context, fragments, TITLES);
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, 
										getResources().getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		pager.setOffscreenPageLimit(fragments.size());
		tabs.setViewPager(pager);
		
	}
	
	private List<RestaurantPagerFragment> changeInstanceType() {
		
		List<RestaurantPagerFragment> fragments	=	GlobelRestaurants.fragment_instences;
		adapter = new RestaurantPagerAdapter(getSupportFragmentManager(),context, fragments, TITLES);
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, 
										getResources().getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		pager.setOffscreenPageLimit(fragments.size());
		tabs.setViewPager(pager);
		return fragments;
		
//		for(int i=0 ; i< fragments.size(); i++) {
//			RestaurantPagerFragment f= fragments.get(i);
//			//f.setHeaderFilter(audienceFilter);
//			//f.changeType_Notification(audienceFilter);
//			fragments.set(i, f);
//		}return fragments;
	}

	private List<RestaurantPagerFragment> getFavouriteFragments() {
		if(TITLES.size()>0){
			List<RestaurantPagerFragment> instances = new ArrayList<RestaurantPagerFragment>();
			for(int i=0; i< TITLES.size() ; i++)
				instances.add(RestaurantPagerFragment.newInstance(i, context));
			
			GlobelRestaurants.fragment_instences= instances;
			return instances;
		}
		return null;
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}



	@Override
	public void onClick(View v) {
		if(navigation_button.getId() == v.getId()){
			finish();
		}
	}
}
