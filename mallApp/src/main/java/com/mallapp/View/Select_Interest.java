package com.mallapp.View;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.List.Adapter.InterestAdapter;
import com.mallapp.Model.Interst_Selection;
import com.mallapp.cache.InterestCacheManager;
import com.mallapp.utils.AlertMessages;

public class Select_Interest extends Activity implements OnClickListener {
	
	boolean isSelectAll=false;
	private Button next;
	private ImageView is_interest_select_all;
	private ImageButton back;
	private ListView list_view;
	InterestAdapter adapter;
	ArrayList<Interst_Selection> interst_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_interest);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		is_interest_select_all= (ImageView) findViewById(R.id.is_select_all_interst);
		next 	= (Button) findViewById(R.id.next_screen);
		back	= (ImageButton) findViewById(R.id.back_screen);
		next.setOnClickListener(this);
		back.setOnClickListener(this);
		is_interest_select_all.setOnClickListener(this);
		getInterestList();
		isSelectAll= false;
		list_view	= (ListView) findViewById(R.id.search_list);
		adapter		= new InterestAdapter(getApplicationContext(),R.layout.list_item_interest, interst_list);
		list_view.setAdapter(adapter);
		
	}

	private void getInterestList() {
		String [] interest= getResources().getStringArray(R.array.interest_list);
		interst_list= new ArrayList<Interst_Selection>();

		for(int i=0; i<interest.length; i++){
			Interst_Selection i_s= new Interst_Selection();
			i_s.setId(i);
			i_s.setInterest_title(interest[i]);
			i_s.setInterested(false);
			interst_list.add(i_s);
		}
		saveInterestList();
	}
	
	private void saveInterestList() {
		InterestCacheManager.saveFavorites(getApplicationContext(), interst_list);
	}

	private void selectAllInterests() {
		if(isSelectAll){
			isSelectAll= false;
			is_interest_select_all.setImageResource(R.drawable.interest);
		}else{
			isSelectAll= true;
			is_interest_select_all.setImageResource(R.drawable.interest_p);
		}
		resetInterestList(isSelectAll);
	}
	
	private void resetInterestList(boolean isSelected) {
		for(Interst_Selection interest: interst_list){
			if(isSelected)
				interest.setInterested(true);
			else 
				interest.setInterested(false);
			interst_list.set(interest.getId(), interest);
		}
		saveInterestList();
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		if(v.getId()== next.getId()){
			
			int count= InterestCacheManager.countInterests(getApplicationContext());
			if(count>0){
				Intent select_interest= new Intent(Select_Interest.this, DashboardTabFragmentActivity.class);
				finish();
				startActivity(select_interest);
			}else
				AlertMessages.show_alert(Select_Interest.this, "The Mall App", "Please select at least one interest.", "OK");
			
			
			
			
			
		}else if(v.getId()== back.getId()){
			Intent select_interest= new Intent(Select_Interest.this, Select_Favourite_Center.class);
			finish();
			startActivity(select_interest);
			
		}else if(v.getId()== is_interest_select_all.getId()){
			selectAllInterests();
		}
	}

	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent select_interest= new Intent(Select_Interest.this, Select_Favourite_Center.class);
		finish();
		startActivity(select_interest);
	}
}