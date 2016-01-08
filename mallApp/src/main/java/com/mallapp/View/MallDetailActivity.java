package com.mallapp.View;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.GestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.MallDetailModel;
import com.mallapp.Model.MallTimingsModel;
import com.mallapp.Model.RestaurantModel;
import com.mallapp.listeners.MallDataListener;
import com.mallapp.utils.VolleyNetworkUtil;
import com.squareup.picasso.Picasso;

public class MallDetailActivity extends SlidingDrawerActivity  implements OnClickListener, MallDataListener{

	//region Data Members
	private GestureDetector detector ;
	VolleyNetworkUtil volleyNetworkUtil;
	GoogleMap map;
	public static final int DEFAULT_MAP_ZOOM_LEVEL = 12;
	Double lat,lng;
	String locationName;

	private ImageView 		mall_logo,mall_map,mall_background,expand;
	private TextView 		tv_about, tv_Detail, mall_name, rel_offer_title,	 shop_detail;
	private TextView 		tv_address, tv_Phone, tv_Email, tv_Web, tv_Timing1, tv_Timing2;
	private ImageButton	 	open_drawer, open_navigation , location, timing, social_sharing ;
	LinearLayout linear_timing_layout;
	String url;
	String mallStoreId;
	MallDetailModel mallDetailModel;
	//endregion



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mall_detail_activity);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		mallStoreId = getIntent().getStringExtra(Offers_News_Constants.MALL_PLACE_ID);
		url = ApiConstants.GET_MALL_DETAIL_URL_KEY+mallStoreId+"&languageId=1";
		volleyNetworkUtil = new VolleyNetworkUtil(this);
		volleyNetworkUtil.GetMallDetail(url, this);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		init();
	}



	
	
	private void init() {
		open_navigation = (ImageButton) findViewById(R.id.navigation);
		open_drawer		= (ImageButton) 		findViewById(R.id.navigation_drawer);
		mallDetailModel = new MallDetailModel();
		expand	= (ImageView)	findViewById(R.id.iv_expand);
		mall_background		= (ImageView) findViewById(R.id.iv_background);
		mall_name = (TextView) findViewById(R.id.offer_title);
		mall_map		= (ImageView) findViewById(R.id.ivMap);
		tv_about 		= (TextView) findViewById(R.id.tv_about);
		tv_Detail 		= (TextView) findViewById(R.id.tv_offer_detail);
		tv_address 		= (TextView) findViewById(R.id.tv_address);
		tv_Phone 		= (TextView) findViewById(R.id.tv_phone);
		tv_Email 		= (TextView) findViewById(R.id.tv_email);
		rel_offer_title 		= (TextView) findViewById(R.id.textView4);
		tv_Web 		= (TextView) findViewById(R.id.tv_web);
		linear_timing_layout	= (LinearLayout) findViewById(R.id.layout_timings);

		open_navigation.setOnClickListener(this);
		open_drawer.setOnClickListener(this);
		open_navigation.setVisibility(View.GONE);
		open_drawer.setVisibility(View.VISIBLE);
		tv_Web.setOnClickListener(this);
		expand.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		/*if(navigation_button.getId() == v.getId()){
			finish();
		}*/
		if (v.getId() == tv_Web.getId()){
			Intent intent= new Intent(MallDetailActivity.this, WebViewActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("url", mallDetailModel.getWebURL());
			startActivity(intent);
		}
		else if (v.getId() == expand.getId()){
			Intent intent= new Intent(MallDetailActivity.this, MapFullScreenActivity.class);
			intent.putExtra(Offers_News_Constants.SHOP_DETAIL_OBJECT,mallDetailModel);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

		}
		if (open_navigation.getId() == v.getId()) {
			//DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
			finish();
		}else if(open_drawer.getId() == v.getId()){
			SlidingDrawerActivity.uiHandler.sendEmptyMessage(1);
		}
	}

	@Override
	public void onDataReceived(ArrayList<MallActivitiesModel> mallActivitiesModels) {

	}

	@Override
	public void onMallDetailReceived(MallDetailModel mallDetailModel) {

		this.mallDetailModel = mallDetailModel;
		initilizeMap();
		Picasso.with(this).load(mallDetailModel.getAppBackgroundImageUrl()).placeholder(R.drawable.mallapp_placeholder).fit().into(mall_background);
		mall_name.setText(mallDetailModel.getName());
		tv_about.setText(mallDetailModel.getAboutText());
		tv_Detail.setText(mallDetailModel.getBriefText());
		tv_address.setText(mallDetailModel.getAddress());
		tv_Phone.setText(mallDetailModel.getPhone());tv_Phone.setVisibility(View.GONE);
		tv_Email.setText(mallDetailModel.getEmail());tv_Email.setVisibility(View.GONE);
		MallTimingsModel[] timinigs = mallDetailModel.getMallTimings();
		for (MallTimingsModel st:timinigs
				) {
			final RelativeLayout newView = (RelativeLayout) getLayoutInflater().inflate(R.layout.timing_layout, null);
			String t1 = st.getFromDay()+"-"+st.getToDay();
			String t2 = st.getStartTime()+"-"+st.getEndTime();

			TextView tv=(TextView) newView.findViewById(R.id.tv_d);
			tv.setText(t1);
			TextView tv1=(TextView) newView.findViewById(R.id.tv_t);
			tv1.setText(t2);
			this.linear_timing_layout.addView(newView);
		}
		lat = Double.parseDouble(mallDetailModel.getLatitude());
		lng = Double.parseDouble(mallDetailModel.getLongitude());
		locationName = mallDetailModel.getAddress();
		drawMarkerandZoom(lat, lng, locationName);

		/*if(mallDetailModel.getSiteMapActive()){
			View frag = findViewById(R.id.mapAddress);
			frag.setVisibility(View.GONE);
			mall_map.setVisibility(View.VISIBLE);
			Picasso.with(this).load(mallDetailModel.getSiteMapURL()).into(mall_map);
		}
		else {
			mall_map.setVisibility(View.GONE);
			drawMarkerandZoom(lat, lng, locationName);
		}*/
	}

	@Override
	public void OnError() {

	}

	private void initilizeMap() {

		FragmentManager myFragmentManager		= getSupportFragmentManager();
		SupportMapFragment mySupportMapFragment = (SupportMapFragment) myFragmentManager.findFragmentById(R.id.mapAddress);
		map = mySupportMapFragment.getMap();

		// check if map is created successfully or not
		if (map == null) {
			Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
		} else {
			android.util.Log.e("Initializing Map", "Initializing Map");

			// Changing map type
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			// Showing / hiding your current location
			map.setMyLocationEnabled(true);

			// Enable / Disable zooming controls
			map.getUiSettings().setZoomControlsEnabled(false);

			// Enable / Disable my location button
			map.getUiSettings().setMyLocationButtonEnabled(false);

			// Enable / Disable Compass icon
			map.getUiSettings().setCompassEnabled(false);

			// Enable / Disable Rotate gesture
			map.getUiSettings().setRotateGesturesEnabled(true);
			// Enable / Disable zooming functionality
			map.getUiSettings().setZoomGesturesEnabled(true);
			map.setIndoorEnabled(true);

			/*
			 * drawMarkerandZoom(Constants.USER_CURRENT_LOCATION.latitude, Constants.USER_CURRENT_LOCATION.longitude, "Your Current Position");
			 */


			map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
				@Override
				public boolean onMarkerClick(Marker arg0) {
					// Toast.makeText(getApplicationContext(), arg0.getTitle(),
					// Toast.LENGTH_LONG).show();
					return false;
				}
			});
		}
	}

	public void drawMarkerandZoom(double latitude, double longitude, String tagName) {

		android.util.Log.e("In zoom", "In Xoom");
		android.util.Log.e("Latt", "lati :" + latitude);
		android.util.Log.e("Longg", "longg :" + longitude);
		android.util.Log.e("TagName", "tagName :" + tagName);
		LatLng thisPosition;
		thisPosition = new LatLng(latitude, longitude);
		map.addMarker(new MarkerOptions().position(thisPosition).title(tagName));
		map.clear();
		map.addMarker(new MarkerOptions().position(thisPosition).title(tagName));
		CameraPosition cameraPosition = new CameraPosition.Builder().target(thisPosition).zoom(DEFAULT_MAP_ZOOM_LEVEL).build();
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

	}
}