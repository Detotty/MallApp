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
import android.widget.ListView;

import com.List.Adapter.FavouriteCenterAdapter;
import com.mallapp.Model.FavouriteCenters;
import com.mallapp.cache.CentersCacheManager;
import com.mallapp.utils.AlertMessages;
import com.mallapp.utils.Log;

public class Select_Favourite_Center extends Activity implements OnClickListener {
	Button next, all, nearby ;
	ListView list_view;
	FavouriteCenterAdapter adapter;
	ArrayList<FavouriteCenters> centers_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_favourite_center);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		
		getCenterList();
		
		next 	= (Button) findViewById(R.id.next_screen);
		all		= (Button) findViewById(R.id.all_centers);
		nearby	= (Button) findViewById(R.id.nearby_centers);
		
		all.setOnClickListener(this);
		next.setOnClickListener(this);
		nearby.setOnClickListener(this);
		
		list_view= (ListView) findViewById(R.id.search_list);
		adapter= new FavouriteCenterAdapter(getApplicationContext(), R.layout.list_item_favourite, centers_list);
		list_view.setAdapter(adapter);
		
		all.setBackgroundResource(R.drawable.all_fav_p);
		nearby.setBackgroundResource(R.drawable.nearby_fav);
		all.setTextColor(getResources().getColor(R.color.white));
		nearby.setTextColor(getResources().getColor(R.color.purple));
	
	}

	private void getCenterList() {
		String [] interest_images= {"img_01", "img_02", "img_03", 
									"img_04", "img_05", "img_06",
									"img_07", "img_08"};
		
		String [] interest_logos= {"rest_logo1", "rest_logo2", "rest_logo3", 
				"rest_logo4", "rest_logo5", "rest_logo6",
				"rest_logo7", "rest_logo8"};
		
		String [] interest= getResources().getStringArray(R.array.centers_list);
		centers_list= new ArrayList<FavouriteCenters>();

		for(int i=0; i<interest.length; i++){
			FavouriteCenters i_s= new FavouriteCenters();
			i_s.setId(i);
			i_s.setCenter_title(interest[i]);
			i_s.setCenter_city("Copenhagen");
			i_s.setCenter_image(interest_images[i]);
			i_s.setCenter_logo(interest_logos[i]);
			i_s.setIsfav(false);
			centers_list.add(i_s);
		}
		saveCenters();
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
			
			int count= CentersCacheManager.countCenters(getApplicationContext());
			if(count>0){
				Intent select_interest= new Intent(Select_Favourite_Center.this, Select_Interest.class);
				finish();
				startActivity(select_interest);
			}else
				AlertMessages.show_alert(Select_Favourite_Center.this, "The Mall App", "Please select at least one center.", "OK");
			
			
		}else if(v.getId()== all.getId()){
			
			all.setBackgroundResource(R.drawable.all_fav_p);
			nearby.setBackgroundResource(R.drawable.nearby_fav);
			all.setTextColor(getResources().getColor(R.color.white));
			nearby.setTextColor(getResources().getColor(R.color.purple));
			
		}else if(v.getId()== nearby.getId()){
			
			
			
			
			all.setBackgroundResource(R.drawable.all_fav);
			nearby.setBackgroundResource(R.drawable.nearby_fav_p);
			all.setTextColor(getResources().getColor(R.color.purple));
			nearby.setTextColor(getResources().getColor(R.color.white));
		
		}
	}

	private void saveCenters() {
		Log.e("save center list ", "size = "+centers_list.size());
		CentersCacheManager.saveFavorites(getApplicationContext(), centers_list);
	}
}