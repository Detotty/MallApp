package com.mallapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost.OnTabChangeListener;

import com.mallapp.View.R;
import com.mallapp.utils.Log;


public class DashBoardFragmentTabs extends Fragment implements OnTabChangeListener {

	String TAG = getClass().getCanonicalName();
	private FragmentTabHost mTabHost;
	//private FragmentActivity myContext;
	public static Handler uihandler;
	
	
	public DashBoardFragmentTabs(Handler handler) {
		super();
		uihandler= handler;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		//myContext=(FragmentActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.e(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.dashboard_fragmentparent_tab, container, false);
		
//		mTabHost = new FragmentTabHost(getActivity());
//        mTabHost.setup(getActivity(), getChildFragmentManager());//, R.id.fragment1);
		
        mTabHost = new FragmentTabHost(getActivity());
		mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.dashboard_fragmentparent_tab);
		
		
		//Log.e("MainActivity", "home pressed");
		//mTabHost = new FragmentTabHost(getActivity());
		//mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.my_parent_fragment);

		
		
		
		
		//FragmentManager fragManager = myContext.getSupportFragmentManager();
//		mTabHost = (FragmentTabHost)  view.findViewById(android.R.id.tabhost);
//		mTabHost.setup(getActivity(), getActivity().getSupportFragmentManager());
//        mTabHost.setup(getActivity(), getChildFragmentManager() , android.R.id.tabcontent);

//		mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
//		mTabHost.setup(getActivity().getApplicationContext(), fragManager, android.R.id.tabcontent);
//		mTabHost.setup(getActivity().getApplicationContext(), fragManager, R.id.tabcontent2);
		
		// Home Tab
		View view1 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_indicator_offers, 		mTabHost, false);
		View view2 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_indicator_messages, 	mTabHost, false);
		View view3 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_indicator_cards, 		mTabHost, false);
		View view4 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_indicator_rewards, 	mTabHost, false);
		View view5 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_indicator_profile, 	mTabHost, false);

		mTabHost.addTab(mTabHost.newTabSpec("Offer")	.setIndicator(view1),	OffersTabFragment.class, 	null);
		mTabHost.addTab(mTabHost.newTabSpec("Messages")	.setIndicator(view2),	MessagesTabFragments.class,null);
		mTabHost.addTab(mTabHost.newTabSpec("Cards")	.setIndicator(view3),	CardTabFragments.class, 	null);
		mTabHost.addTab(mTabHost.newTabSpec("Rewards")	.setIndicator(view4),	RewardsTabFragments.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("Profile")	.setIndicator(view5),	ProfileTabFragment.class, 	null);

		mTabHost.setCurrentTab(0);
		return view;
	
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.e(TAG, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
		
	}
	
	
//	private void initTabs() {
//		// Locate android.R.id.tabhost in main_fragment.xml
//		Log.e("MainActivity", "home pressed");
//		FragmentManager fragManager = myContext.getSupportFragmentManager();
//		// Create the tabs in main_fragment.xml
//		
//		mTabHost = (FragmentTabHost) getView().findViewById(android.R.id.tabhost);
//		mTabHost.setup(getActivity().getApplicationContext(), fragManager, android.R.id.tabcontent);
//		mTabHost.setup(getActivity().getApplicationContext(), fragManager, R.id.tabcontent2);
//		
//		// Home Tab
//		View view1 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_indicator_offers, 		mTabHost, false);
//		View view2 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_indicator_messages, 	mTabHost, false);
//		View view3 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_indicator_cards, 		mTabHost, false);
//		View view4 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_indicator_rewards, 	mTabHost, false);
//		View view5 = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_indicator_profile, 	mTabHost, false);
//
//		mTabHost.addTab(mTabHost.newTabSpec("Offer")	.setIndicator(view1),OffersTabFragment.class, 	null);
//		mTabHost.addTab(mTabHost.newTabSpec("Messages")	.setIndicator(view2),MessagesTabFragments.class,null);
//		mTabHost.addTab(mTabHost.newTabSpec("Cards")	.setIndicator(view3),CardTabFragments.class, 	null);
//		mTabHost.addTab(mTabHost.newTabSpec("Rewards")	.setIndicator(view4),RewardsTabFragments.class, null);
//		mTabHost.addTab(mTabHost.newTabSpec("Profile")	.setIndicator(view5),ProfileTabFragment.class, 	null);
//	}
	

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mTabHost = null;
	}



}
