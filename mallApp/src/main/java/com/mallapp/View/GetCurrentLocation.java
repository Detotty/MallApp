package com.mallapp.View;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;




public class GetCurrentLocation implements 	GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
							LocationListener {

	// locations objects
	
	Context context;
	Activity activity;
	
	
	
	
	public GetCurrentLocation(Context context, Activity activity) {
		super();
		this.context = context;
		this.activity = activity;
	}


	GoogleApiClient mLocationClient;
	Location mCurrentLocation;
	LocationRequest mLocationRequest;
	
	public GetCurrentLocation() {
		super();
	}
	
	
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionSuspended(int i) {

	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
 

    
//    TextView txtLong,txtLat;
//    
//    
//    
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // 1. setContnetView
//        setContentView(R.layout.activity_main);
//         
//        // 2. get reference to TextView
//        txtLong = (TextView) findViewById(R.id.txtLong);
//        txtLat = (TextView) findViewById(R.id.txtLat);
// 
//        // 3. create LocationClient
//        mLocationClient = new LocationClient(this, this, this);
// 
//        // 4. create & set LocationRequest for Location update
//        mLocationRequest = LocationRequest.create();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        // Set the update interval to 5 seconds
//        mLocationRequest.setInterval(1000 * 5);
//        // Set the fastest update interval to 1 second
//        mLocationRequest.setFastestInterval(1000 * 1);
// 
//         
//    }
//     @Override
//        protected void onStart() {
//            super.onStart();
//            // 1. connect the client.
//            mLocationClient.connect();
//        }
//     @Override
//        protected void onStop() {
//            super.onStop();
//            // 1. disconnecting the client invalidates it.
//            mLocationClient.disconnect();
//        }
//      
// 
//    // GooglePlayServicesClient.OnConnectionFailedListener
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
//    }
// 
//    // GooglePlayServicesClient.ConnectionCallbacks 
//    @Override
//    public void onConnected(Bundle arg0) {
//         
//        if(mLocationClient != null)
//            mLocationClient.requestLocationUpdates(mLocationRequest,  this);
// 
//        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
//         
//        if(mLocationClient != null){
//            // get location
//            mCurrentLocation = mLocationClient.getLastLocation();
//                try{
//                     
//                    // set TextView(s) 
//                    txtLat.setText(mCurrentLocation.getLatitude()+"");
//                    txtLong.setText(mCurrentLocation.getLongitude()+"");
//                     
//                }catch(NullPointerException npe){
//                     
//                    Toast.makeText(this, "Failed to Connect", Toast.LENGTH_SHORT).show();
// 
//                    // switch on location service intent
//                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                    startActivity(intent);
//                }
//        }
// 
//    }
//    @Override
//    public void onDisconnected() {
//        Toast.makeText(this, "Disconnected.", Toast.LENGTH_SHORT).show();
//     
//    }
// 
//    // LocationListener
//    @Override
//    public void onLocationChanged(Location location) {
//        Toast.makeText(this, "Location changed.", Toast.LENGTH_SHORT).show();
//        mCurrentLocation = mLocationClient.getLastLocation();
//        txtLat.setText(mCurrentLocation.getLatitude()+"");
// 
//        txtLong.setText(mCurrentLocation.getLongitude()+"");
//    }
     
}


