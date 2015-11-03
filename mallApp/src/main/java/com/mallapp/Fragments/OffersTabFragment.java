package com.mallapp.Fragments;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ViewPager.Adapter.OffersNewsPagerAdapter;
import com.astuetz.PagerSlidingTabStrip;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Controllers.FavouriteCentersFiltration;
import com.mallapp.Controllers.OffersNewsFiltration;
import com.mallapp.Model.FavouriteCenters;
import com.mallapp.View.DashboardTabFragmentActivity;
import com.mallapp.View.R;
import com.mallapp.cache.CentersCacheManager;
import com.mallapp.layouts.SegmentedRadioGroup;
import com.mallapp.utils.GlobelOffersNews;
import com.mallapp.utils.Log;

public class OffersTabFragment 	extends Fragment 
								implements 	OnCheckedChangeListener {
	
	String TAG = getClass().getCanonicalName();

	static Context context;
	private ViewPager pager;
	SegmentedRadioGroup segmentText;
	private PagerSlidingTabStrip tabs;
	public static ImageView center_logo;
	private ImageButton navigation_button;
	private OffersNewsPagerAdapter adapter;
	public static String selected_center_name;
	public static String selected_center_logo;
	
	private ArrayList<String> TITLES= new ArrayList<String>();
	String audienceFilter=Offers_News_Constants.AUDIENCE_FILTER_ALL;
	
	public static Handler uihandler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.e(TAG, " on create OffersTabFragment ");
		context= getActivity().getApplicationContext();
		TITLES= FavouriteCentersFiltration.getFavCenterTITLES(context);
		uihandler= MainMenuConstants.uiHandler;
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.e(TAG, "onCreateView");
		View  view	= inflater.inflate(R.layout.fragment_parent_tab_offer, container, false);
		
		navigation_button= (ImageButton) view.findViewById(R.id.navigation_drawer);
		center_logo = (ImageView) view.findViewById(R.id.center_logo);
		setCenter_logo("rest_logo8");
		segmentText = (SegmentedRadioGroup)  view.findViewById(R.id.segment_text);
		tabs 		= (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
		pager 		= (ViewPager)  view.findViewById(R.id.pager);
		
		segmentText.setOnCheckedChangeListener(this);
		
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
				setCenterLogo(position);
			}});
		
		navigation_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
				
			}
		});
		
	//	return inflater.inflate(R.layout.fragment_parent_tab_offer, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.e(TAG, "onActivityCreated");
		
//		center_logo = (ImageView) getView().findViewById(R.id.center_logo);
//		setCenter_logo("rest_logo8");
//		
//		segmentText = (SegmentedRadioGroup)  getView().findViewById(R.id.segment_text);
//		segmentText.setOnCheckedChangeListener(this);
//		tabs 	= (PagerSlidingTabStrip) getView().findViewById(R.id.tabs);
//		pager 	= (ViewPager)  getView().findViewById(R.id.pager);
//		
//		tabs.setOnPageChangeListener(new OnPageChangeListener() {
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//				//Log.e(" offers tab fragment ", "onPageScrollStateChanged");
//			}
//			@Override
//			public void onPageScrolled(int position, float arg1, int arg2) {
//				//Log.e(" offers tab fragment ", "onPageScrolled" + position);
//			}
//			@Override
//			public void onPageSelected(int position) {
//				//Log.e(" offers tab fragment ", "onPageSelected" + position+" centers fav size = "+ TITLES.size());
//				setCenterLogo(position);
//			}});
		super.onActivityCreated(savedInstanceState);
	}
	
	protected void setCenterLogo(int position){
		
		String selectedCenter= TITLES.get(position).trim();
		setSelected_center_name(selectedCenter);
		
		if(position == 0){
			setCenter_logo("rest_logo8");
			MainMenuConstants.SELECTED_CENTER_LOGO = null;
			
		}else {
			ArrayList<FavouriteCenters> TITLES_Centers= GlobelOffersNews.TITLES_centers;
			
			if(TITLES_Centers == null || TITLES_Centers.size()==0){
				TITLES_Centers = CentersCacheManager.getAllCenters(context);
			}
			for(FavouriteCenters center : TITLES_Centers){
				if(center.isIsfav() && center.getCenter_title().trim().equals(selectedCenter)){
					String center_logo_name= center.getCenter_logo();
					setCenter_logo(center_logo_name);
					setSelected_center_logo(center_logo_name);
				}
			}
		}
	}
	
	@Override
	public void onResume() {
		callInOnResume();
		super.onResume();
	}
	
	@Override
	public void onDestroyView() {
		Log.e(TAG, "destory view");
		destoryAllInstences();
		super.onDestroyView();
	}
	
	private void destoryAllInstences() {
		GlobelOffersNews.fragment_instences = null;
//		GlobelOffersNews.all_audience= null;
//		GlobelOffersNews.all_audience_images= null;
//		GlobelOffersNews.endorsement_array= null;
//		GlobelOffersNews.offers_audience= null;
		
		
//		Globel_Endorsement.endorsement_array				= null;
//		Globel_Endorsement.endorsement_all_audience			= null;
//		Globel_Endorsement.endorsement_all_audience_images	= null;
//		Globel_Endorsement.endorsement_contacts_audience	= null;
//		Globel_Endorsement.endorsement_contacts_audience_images	= null;
//		Globel_Endorsement.endorsement_trusted_audience_images	= null;
//		Globel_Endorsement.endorsement_trusted_audience			= null;

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (group == segmentText) {
			if (checkedId == R.id.button_one) {
				audienceFilter= Offers_News_Constants.AUDIENCE_FILTER_ALL;
			} else if (checkedId == R.id.button_two) {
				audienceFilter= Offers_News_Constants.AUDIENCE_FILTER_OFFERS;
			} else if (checkedId == R.id.button_three) {
				audienceFilter= Offers_News_Constants.AUDIENCE_FILTER_NEWS;
			}
			callInOnResume();
		}
	}

	private void callInOnResume() {
		Log.e(TAG, "On Resme call");
		if(GlobelOffersNews.fragment_instences == null){
			createPagerIntence();
		}else{
			changeInstanceType();
		}
	}

	private void createPagerIntence() {
		
		OffersNewsFiltration.readOffersNews(context);
		List<OfferPagerTabFragment> fragments= getFavouriteFragments();
		adapter = new OffersNewsPagerAdapter(getChildFragmentManager(),context, uiHandler, audienceFilter, fragments);
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, 
										getResources().getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		pager.setOffscreenPageLimit(fragments.size());
		tabs.setViewPager(pager);
		adapter.notifyDataSetChanged();
	}
	
	private List<OfferPagerTabFragment> changeInstanceType() {
		List<OfferPagerTabFragment> fragments	=	GlobelOffersNews.fragment_instences;
		for(int i=0 ; i< fragments.size(); i++) {
			OfferPagerTabFragment f= fragments.get(i);
			f.setHeaderFilter(audienceFilter);
			f.changeType_Notification(audienceFilter);
			fragments.set(i, f);
		}return fragments;
	}

	private List<OfferPagerTabFragment> getFavouriteFragments() {
		List<OfferPagerTabFragment> instances = new ArrayList<OfferPagerTabFragment>();
//		if(TITLES == null || TITLES.size()==0)
//			TITLES = SharedPreferenceFavourites.getFavouritesList(context);
//		
		for(int i=0; i< TITLES.size() ; i++)
			instances.add(OfferPagerTabFragment.newInstance(i, uiHandler,context, audienceFilter));
		
//		GlobelOffersNews.TITLES= TITLES;
		GlobelOffersNews.fragment_instences= instances;
		return instances;
	}

	public static ImageView getCenter_logo() {
		return center_logo;
	}

	public static void setCenter_logo(String center_logo) {
		
		int mDstWidth 	= context.getResources().getDimensionPixelSize(R.dimen.center_logo_width);
        int mDstHeight 	= context.getResources().getDimensionPixelSize(R.dimen.center_logo_height);
		
		int imageResource 	= context.getResources().getIdentifier(center_logo, "drawable", context.getPackageName());
		Drawable d 			= context.getResources().getDrawable(imageResource);
		Bitmap bitmap 		= ((BitmapDrawable) d).getBitmap();
		
		d = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, mDstWidth,mDstHeight, true));
		OffersTabFragment.center_logo.setImageDrawable(d);
		
	}

	public static String getSelected_center_name() {
		return MainMenuConstants.SELECTED_CENTER_NAME;
	}

	public static void setSelected_center_name(String selected_center_name) {
		OffersTabFragment.selected_center_name = selected_center_name;
		MainMenuConstants.SELECTED_CENTER_NAME = selected_center_name;
		Log.e("", "selected center name = "+selected_center_name);
	}
	
	
	
  
	public static String getSelected_center_logo() {
		return selected_center_logo;
	}
	

	public static void setSelected_center_logo(String selected_center_logo) {
		OffersTabFragment.selected_center_logo = selected_center_logo;
		MainMenuConstants.SELECTED_CENTER_LOGO = selected_center_logo;
	}
	




	@SuppressLint("HandlerLeak") 
	public Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Log.e(TAG, "Handler works");
				destoryAllInstences();//Globel_Endorsement.fragment_instences = null;
				callInOnResume();
				break;
			}
		};
	};

   
}