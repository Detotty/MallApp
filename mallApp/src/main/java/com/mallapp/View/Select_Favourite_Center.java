package com.mallapp.View;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.List.Adapter.FavouriteCenterAdapter;
import com.mallapp.Constants.AppConstants;
import com.mallapp.utils.RegistrationController;
import com.mallapp.Model.FavouriteCenters;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.Model.UserLocationModel;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.cache.CentersCacheManager;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.listeners.NearbyListener;
import com.mallapp.utils.AlertMessages;
import com.mallapp.utils.Log;
import com.mallapp.utils.SharedInstance;

public class Select_Favourite_Center extends Activity implements OnClickListener, NearbyListener {
	Button next, all, nearby ;
	ListView list_view;
	public FavouriteCenterAdapter adapter;
	ArrayList<FavouriteCenters> centers_list;
	ArrayList<FavouriteCentersModel> centers_listM;
	ArrayList<FavouriteCentersModel> nearByCenters;
	private RegistrationController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_favourite_center);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		centers_listM= new ArrayList<FavouriteCentersModel>();
		list_view= (ListView) findViewById(R.id.search_list);
		adapter= new FavouriteCenterAdapter(getApplicationContext(), R.layout.list_item_favourite, centers_listM);
		list_view.setAdapter(adapter);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		controller = new RegistrationController(this);
		controller.GetMallList(ApiConstants.GET_MALL_URL_KEY,adapter,centers_listM,this,list_view);
//		SendVerificationCode.GetMallList("http://52.28.59.218:5001/api/MallService/GetMalls?countryCode=PK&languageId=1");
//		getCenterList();
		
		next 	= (Button) findViewById(R.id.next_screen);
		all		= (Button) findViewById(R.id.all_centers);
		nearby	= (Button) findViewById(R.id.nearby_centers);
		
		all.setOnClickListener(this);
		next.setOnClickListener(this);
		nearby.setOnClickListener(this);
		

		
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
		centers_listM= new ArrayList<FavouriteCentersModel>();

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
			ArrayList<FavouriteCentersModel> selectedCenters = new ArrayList<>();
			try {
				selectedCenters = CentersCacheManager.readSelectedObjectList(this);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(count>0){

				StringBuilder sb = new StringBuilder();
				for (FavouriteCentersModel fv:selectedCenters) {
						sb.append(fv.getMallPlaceId() + ",");
				}
				String UserID = SharedPreferenceUserProfile.getUserId(this);
				String MallPlaceID = sb.toString().substring(0, sb.length() - 1);
				controller.PostMallInterestSelection(ApiConstants.POST_FAV_MALL_URL_KEY+"?UserId="+UserID+"&MallPlaceId="+MallPlaceID,true);

			}else
				AlertMessages.show_alert(Select_Favourite_Center.this, "The Mall App", "Please select at least one center.", "OK");

		}else if(v.getId()== all.getId()){

			all.setBackgroundResource(R.drawable.all_fav_p);
			nearby.setBackgroundResource(R.drawable.nearby_fav);
			all.setTextColor(getResources().getColor(R.color.white));
			nearby.setTextColor(getResources().getColor(R.color.purple));

			adapter= new FavouriteCenterAdapter(getApplicationContext(), R.layout.list_item_favourite, nearByCenters);
			list_view.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			
		}else if(v.getId()== nearby.getId()){

			all.setBackgroundResource(R.drawable.all_fav);
			nearby.setBackgroundResource(R.drawable.nearby_fav_p);
			all.setTextColor(getResources().getColor(R.color.purple));
			nearby.setTextColor(getResources().getColor(R.color.white));
			CalculateNearByCenters();

		}
	}

	private void saveCenters() {
		Log.e("save center list ", "size = " + centers_list.size());
//		CentersCacheManager.saveFavorites(getApplicationContext(), centers_list);
//		CentersCacheManager.getAllCenters(this);
	}


	@Override
	public void onMallDataReceived(ArrayList<FavouriteCentersModel> mallList) {

		nearByCenters = mallList;
	}

	void CalculateNearByCenters(){
		UserLocationModel model = null;
		Location locationA = new Location("point A");
		Location locationB = new Location("point B");
		ArrayList<FavouriteCentersModel> centers = new ArrayList<>();

		if (SharedInstance.getInstance().getSharedHashMap().containsKey(AppConstants.USER_LOCATION)) {
			model = (UserLocationModel) SharedInstance.getInstance().getSharedHashMap().get(AppConstants.USER_LOCATION);
			if (model!=null){
				locationA.setLatitude(model.getLatitude());
				locationA.setLongitude(model.getLongitude());
			}
		}
		for (FavouriteCentersModel fv:nearByCenters) {
			locationB.setLatitude(Double.parseDouble(fv.getLatitude()));
			locationB.setLongitude(Double.parseDouble(fv.getLongitude()));
			float distance = locationA.distanceTo(locationB);;
			if (distance<10000){
				centers.add(fv);
			}
		}
		adapter= new FavouriteCenterAdapter(getApplicationContext(), R.layout.list_item_favourite, centers);
		list_view.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}
}