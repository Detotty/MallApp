package com.mallapp.View;

import com.Location.Gps.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class DirectionsNParking  extends Activity implements OnClickListener {

	private ImageButton open_navigation;
	private TextView	heading;
	private ImageButton display_map, parking;
	GPSTracker gps;
	double latitude; // latitude
	double longitude; // longitude
	// Google Map
    private GoogleMap googleMap;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directions_parking_main_menu);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		init();
		getCurrentLocation();
		try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	private void getCurrentLocation() {
		gps = new GPSTracker(DirectionsNParking.this);
		
		// check if GPS enabled
		if(gps.canGetLocation()){
			latitude 	= gps.getLatitude();
			longitude	= gps.getLongitude();
			// \n is for new line
			//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();   
		}else{
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}
	}

	private void init() {
		open_navigation	= (ImageButton) findViewById(R.id.back);
		heading			= (TextView) 	findViewById(R.id.heading);
		display_map		= (ImageButton) findViewById(R.id.display_map);
		parking			= (ImageButton) findViewById(R.id.parking);
		
		heading.setText(getResources().getString(R.string.heading_directions));
		open_navigation.setOnClickListener(this);
		display_map.	setOnClickListener(this);
		parking.		setOnClickListener(this);
	}
	
	/**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
    	if (googleMap == null) {
    		googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

    		// check if map is created successfully or not
    		if (googleMap == null) {
    			Toast.makeText(getApplicationContext(),
    					"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }
    		
    		// create marker
    		MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));//.title("Latitude: "+latitude+"\nLongitude: "+ longitude);
    		// ROSE color icon
    		//marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
    		 
    		// adding marker
    		googleMap.addMarker(marker);
    		CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(11).build();
    		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    		
        }
    }
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initilizeMap();
	}
	
	

	@Override
	public void onClick(View v) {
		if(open_navigation.getId()== v.getId()){
			//DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
			finish();
		
		}else if(display_map.getId()== v.getId()){
		
			Intent getAddressIntent = new Intent(DirectionsNParking.this, LiveMap.class);
			getAddressIntent.putExtra("locationName", "");
			getAddressIntent.putExtra("lat", String.valueOf(latitude));
			getAddressIntent.putExtra("lng", String.valueOf(longitude));
			startActivity(getAddressIntent);
		
		}else if(parking.getId()== v.getId()){
			Intent intent	=	new Intent(DirectionsNParking.this, DirectionsNParkingCreateObject.class);
			startActivity(intent);
		}
	}
}
