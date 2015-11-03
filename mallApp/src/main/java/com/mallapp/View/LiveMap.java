package com.mallapp.View;

import android.app.ActionBar;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class LiveMap extends FragmentActivity {

	//public static final LatLng TEST_LOCATION_LHR = new LatLng(31.5275826, 74.3458857);
	public static final LatLng TEST_LOCATION_LHR = new LatLng(31.5626468, 74.3328965);
	
	Dialog mDialog = null;
	GoogleMap map;
	LatLng selectedPosition;
	String prevActivityLocationName;
	Double prevActivityLat, prevActivityLng;
	
	static String locationName;
	static Double lat = TEST_LOCATION_LHR.latitude;
	static Double lng = TEST_LOCATION_LHR.longitude;
	
	String addressAfterChange;
	public static final int DEFAULT_MAP_ZOOM_LEVEL = 12;
	//PlacesAutoCompleteAdapter myPlacesAdapter;
	boolean isJobDone = false;
	boolean isFromLongClick = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_livemap);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		// initializing variables
		if (getIntent().getExtras().get("locationName").toString() != null) {
			locationName = getIntent().getExtras().get("locationName").toString();
			prevActivityLocationName = getIntent().getExtras().get("locationName").toString();
		}if (getIntent().getExtras().get("lat").toString() != null) {
			lat = Double.parseDouble(getIntent().getExtras().get("lat").toString());
			prevActivityLat = Double.parseDouble(getIntent().getExtras().get("lat").toString());
		}if (getIntent().getExtras().get("lng").toString() != null) {
			lng = Double.parseDouble(getIntent().getExtras().get("lng").toString());
			prevActivityLng = Double.parseDouble(getIntent().getExtras().get("lng").toString());
		}
		
		initilizeMap();
	}

	/**
	 * function to load map If map is not created it will create it for you
	 * */
	private void initilizeMap() {
	
		FragmentManager myFragmentManager		= getSupportFragmentManager();
		SupportMapFragment mySupportMapFragment = (SupportMapFragment) myFragmentManager.findFragmentById(R.id.mapAddress);
		map = mySupportMapFragment.getMap();
		
		// check if map is created successfully or not
		if (map == null) {
			Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
		} else {
			Log.e("Initializing Map", "Initializing Map");

			// Changing map type
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			// Showing / hiding your current location
			map.setMyLocationEnabled(true);

			// Enable / Disable zooming controls
			map.getUiSettings().setZoomControlsEnabled(true);

			// Enable / Disable my location button
			map.getUiSettings().setMyLocationButtonEnabled(true);

			// Enable / Disable Compass icon
			map.getUiSettings().setCompassEnabled(true);

			// Enable / Disable Rotate gesture
			map.getUiSettings().setRotateGesturesEnabled(true);
			// Enable / Disable zooming functionality
			map.getUiSettings().setZoomGesturesEnabled(true);
			map.setIndoorEnabled(true);

			/*
			 * drawMarkerandZoom(Constants.USER_CURRENT_LOCATION.latitude, Constants.USER_CURRENT_LOCATION.longitude, "Your Current Position");
			 */

			drawMarkerandZoom(lat, lng, locationName);

			map.setOnMarkerClickListener(new OnMarkerClickListener() {
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
	
		Log.e("In zoom", "In Xoom");
		Log.e("Latt", "lati :" + latitude);
		Log.e("Longg", "longg :" + longitude);
		Log.e("TagName", "tagName :" + tagName);
		LatLng thisPosition;
		thisPosition = new LatLng(latitude, longitude);
		map.addMarker(new MarkerOptions().position(thisPosition).title(tagName));
		map.clear();
		map.addMarker(new MarkerOptions().position(thisPosition).title(tagName));
		CameraPosition cameraPosition = new CameraPosition.Builder().target(thisPosition).zoom(DEFAULT_MAP_ZOOM_LEVEL).build();
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

	}


	public void drawMarkerOnly(double latitude, double longitude, String tagName) {
		LatLng thisPosition;
		thisPosition = new LatLng(latitude, longitude);
		map.addMarker(new MarkerOptions().position(thisPosition).title(tagName));
		map.clear();
		map.addMarker(new MarkerOptions().position(thisPosition).title(tagName));
	}
}
