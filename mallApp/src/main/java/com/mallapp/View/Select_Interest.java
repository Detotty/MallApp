package com.mallapp.View;

import java.io.IOException;
import java.util.ArrayList;

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
import com.mallapp.Fragments.ProfileTabFragment;
import com.mallapp.utils.RegistrationController;
import com.mallapp.Model.InterestSelectionModel;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.cache.InterestCacheManager;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.utils.AlertMessages;

public class Select_Interest extends Activity implements OnClickListener {
	
	boolean isSelectAll=false;
	private Button next;
	private ImageView is_interest_select_all;
	private ImageButton back;
	private ListView list_view;
	InterestAdapter adapter;
	ArrayList<InterestSelectionModel> interst_list;
	ArrayList<InterestSelectionModel> interst_list_this;
	private RegistrationController controller;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_interest);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		interst_list= new ArrayList<InterestSelectionModel>();
		list_view	= (ListView) findViewById(R.id.search_list);

		controller = new RegistrationController(this);
		is_interest_select_all= (ImageView) findViewById(R.id.is_select_all_interst);

		adapter		= new InterestAdapter(getApplicationContext(),R.layout.list_item_interest, interst_list,is_interest_select_all);
		list_view.setAdapter(adapter);
		interst_list_this = controller.GetInterestList(ApiConstants.GET_INTEREST_URL_KEY,adapter,interst_list,is_interest_select_all);

		next 	= (Button) findViewById(R.id.next_screen);
		back	= (ImageButton) findViewById(R.id.back_screen);
		next.setOnClickListener(this);
		back.setOnClickListener(this);
		is_interest_select_all.setOnClickListener(this);
//		getInterestList();
		isSelectAll= false;
		try{
			if (ProfileTabFragment.isUpdate){
				back.setVisibility(View.VISIBLE);
				next.setText(getResources().getString(R.string.update));
			}
		}catch (Exception e){

		}
		
	}

	private void getInterestList() {
		String [] interest= getResources().getStringArray(R.array.interest_list);
		interst_list= new ArrayList<InterestSelectionModel>();

		for(int i=0; i<interest.length; i++){
			InterestSelectionModel i_s= new InterestSelectionModel();
//			i_s.setId(i);
//			i_s.setInterest_title(interest[i]);
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
			is_interest_select_all.setImageResource(R.drawable.offer_fav_r);
		}else{
			isSelectAll= true;
			is_interest_select_all.setImageResource(R.drawable.offer_fav_p);
		}
		resetInterestList(isSelectAll);
	}
	
	private void resetInterestList(boolean isSelected) {
		for(InterestSelectionModel interest: interst_list){
			if(isSelected)
				interest.setInterested(true);
			else 
				interest.setInterested(false);
//			interst_list.set(interest.getId(), interest);
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
			ArrayList<InterestSelectionModel> selectedCenters = new ArrayList<>();
			try {
				selectedCenters = InterestCacheManager.readSelectedObjectList(this);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(count>0){

				StringBuilder sb = new StringBuilder();
				for (InterestSelectionModel fv:selectedCenters) {
					if (selectedCenters.size()>0){
						sb.append(fv.getCategoryId() + ",");
					}
					else{
						sb = new StringBuilder(fv.getCategoryId()) ;
					}
				}
				String UserID = SharedPreferenceUserProfile.getUserId(this);
				String CatID = sb.toString().substring(0, sb.length() - 1);
				controller.PostMallInterestSelection(ApiConstants.POST_SELECTED_INTEREST_URL_KEY + "?UserId=" + UserID + "&CategoryId="+CatID,false);

			}else
				AlertMessages.show_alert(Select_Interest.this, getResources().getString(R.string.app_name1), getResources().getString(R.string.select_interest), getResources().getString(R.string.ok));
			
		}else if(v.getId()== back.getId()){
			try{
				if (!ProfileTabFragment.isUpdate){
					Intent select_interest= new Intent(Select_Interest.this, Select_Favourite_Center.class);
					finish();
					startActivity(select_interest);
				}
				else
					finish();
			}catch (Exception e){
				finish();
			}
			
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