package com.mallapp.View;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Location.Gps.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mallapp.Model.Parking;
import com.mallapp.globel.GlobelMainMenu;
import com.mallapp.imagecapture.ImageImportHelper;
import com.mallapp.imagecapture.ScalingUtilities;
import com.mallapp.imagecapture.ScalingUtilities.ScalingLogic;
import com.mallapp.layouts.ParentDialog;

public class DirectionsNParkingCreateObject extends Activity implements OnClickListener {

	//static final int REQUEST_IMAGE_CAPTURE = 11;
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
    
    
	private ImageImportHelper mImageImportHelper;
	private ImageButton open_navigation;
	private TextView	heading;
	private Button park_here;
	private ImageButton parking_location, parking_decs, new_parking;
	GPSTracker gps;

	double latitude; 	// latitude
	double longitude; 	// longitude
	// Google Map

	private GoogleMap googleMap;
    Parking parking_obj= null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directions_parking_car_object);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		init();
		
		try {
			getCurrentLocation();
            // Loading map
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	
	private void init() {

		mImageImportHelper 	= ImageImportHelper.getInstance(DirectionsNParkingCreateObject.this);
		open_navigation	= (ImageButton) findViewById(R.id.navigation);
		heading			= (TextView) 	findViewById(R.id.heading);
		parking_location= (ImageButton) findViewById(R.id.parking_picture);
		parking_decs	= (ImageButton) findViewById(R.id.parking_text);
		new_parking		= (ImageButton) findViewById(R.id.new_parking_object);
		park_here		= (Button) findViewById(R.id.park_here);
		
		heading.setText(getResources().getString(R.string.parking_heading));
		open_navigation.setOnClickListener(this);
		parking_location.setOnClickListener(this);
		parking_decs.setOnClickListener(this);
		new_parking.setOnClickListener(this);
		park_here.setOnClickListener(this);
		
		if(park_here.getVisibility()== View.VISIBLE)
			parking_obj= new Parking();
		else 
			parking_obj= GlobelMainMenu.parking_object;
		
	}

	private void getCurrentLocation() {
	
		gps = new GPSTracker(DirectionsNParkingCreateObject.this);
		// check if GPS enabled
		if(gps.canGetLocation()){
			latitude 	= gps.getLatitude();
			longitude	= gps.getLongitude();
			
			parking_obj.setmLatitude(latitude);
			parking_obj.setmLongitude(longitude);
			GlobelMainMenu.parking_object= parking_obj;
			// \n is for new line
			//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();   
		}else{
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}
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
			//MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Latitude: "+latitude+"\nLongitude: "+ longitude);
			// adding marker
			//googleMap.addMarker(marker);
			CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(14).build();
			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		}
	}
	
    
    protected void showDialogToAddTags() {

    	final Dialog dialog = new ParentDialog(DirectionsNParkingCreateObject.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.add_tags);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		
		Button cancel 	= (Button) dialog.findViewById(R.id.cancel);
		Button done 	= (Button) dialog.findViewById(R.id.done);
		final EditText decs	= (EditText) dialog.findViewById(R.id.decs);
		
		if(GlobelMainMenu.parking_object != null){
			parking_obj= GlobelMainMenu.parking_object;
			String decs_s= parking_obj.getmLabel();
			decs.setText(decs_s);
		}
		
		
		cancel.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		dialog.dismiss();
	    	}
		});
	    
	    done.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				
				String decs_s= decs.getText().toString().trim();
				if(GlobelMainMenu.parking_object != null){
					parking_obj= GlobelMainMenu.parking_object;
				}
				parking_obj.setmLabel(decs_s);
				GlobelMainMenu.parking_object= parking_obj;
				
				if(park_here.getVisibility()== View.GONE){
					googleMap.clear();
					park_here.setVisibility(View.GONE);
					if(GlobelMainMenu.parking_object !=null){
						parking_obj= GlobelMainMenu.parking_object;
					}
					MarkerOptions markerOption = new MarkerOptions().position(new LatLng(parking_obj.getmLatitude(), parking_obj.getmLongitude()));
					Marker marker = googleMap.addMarker(markerOption);
					
					googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
					marker.showInfoWindow();
				}
			}
		});
	    dialog.show();
	    
	    
	}
    
    
    
	@Override
	public void onClick(View v) {
	
		  if(open_navigation.getId()== v.getId()){
			finish();
		} else if(parking_location.getId()== v.getId()){
			dispatchTakePictureIntent();
		
		} else if(parking_decs.getId()== v.getId()){
			showDialogToAddTags();
		
		} else if(new_parking.getId()== v.getId()){
		
			googleMap.clear();
			parking_obj= GlobelMainMenu.parking_object;
			parking_obj.setmLabel("");
			parking_obj.setBitmap(null);
			GlobelMainMenu.parking_object= parking_obj;
			park_here.setVisibility(View.VISIBLE);
		
		} else if(park_here.getId()== v.getId()){
		
			park_here.setVisibility(View.GONE);
			if(GlobelMainMenu.parking_object !=null){
				parking_obj= GlobelMainMenu.parking_object;
			}
			MarkerOptions markerOption = new MarkerOptions().position(new LatLng(parking_obj.getmLatitude(), parking_obj.getmLongitude()));
			Marker marker = googleMap.addMarker(markerOption);
			
			googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
			marker.showInfoWindow();
			
			
			
//			MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Latitude: "+latitude+"\nLongitude: "+ longitude);
////			adding marker
//			googleMap.addMarker(marker);
//			marker.showInfoWindow();
//			
		}
	}
	
	// New function for captureImageIntent
	private void dispatchTakePictureIntent() {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, ImageImportHelper.ACTION_TAKE_PHOTO_IMAGEVIEW);
		}
	}
	
	public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        public MarkerInfoWindowAdapter() {
        	
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
        
            View v  = getLayoutInflater().inflate(R.layout.marker_layout, null);
            ImageView markerIcon = (ImageView) v.findViewById(R.id.marker_icon);
            TextView anotherLabel = (TextView)v.findViewById(R.id.another_label);

            Parking myMarker = GlobelMainMenu.parking_object;
            if(myMarker!=null){
            	if(myMarker.getmLabel() !=null && myMarker.getmLabel().length()>0)
                	anotherLabel.setText(myMarker.getmLabel());
                
                if(myMarker.getBitmap() !=null){
                	int mDstWidth = getResources().getDimensionPixelSize(R.dimen.margin_left_60);
            		int mDstHeight = getResources().getDimensionPixelSize(R.dimen.margin_left_60);
            		Bitmap bitmap = ScalingUtilities.createScaledBitmap(myMarker.getBitmap(), mDstWidth , mDstHeight, ScalingLogic.CROP);
                	markerIcon.setImageBitmap(bitmap);
                }
            }
            return v;
        }
    }
	
	


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ImageImportHelper.ACTION_TAKE_PHOTO_IMAGEVIEW
					&& resultCode == RESULT_OK) {
			
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        parking_obj= GlobelMainMenu.parking_object;
	        parking_obj.setBitmap(imageBitmap);
	        GlobelMainMenu.parking_object= parking_obj;
	        
	        if(park_here.getVisibility()== View.GONE){
	        	googleMap.clear();
				park_here.setVisibility(View.GONE);
				if(GlobelMainMenu.parking_object !=null){
					parking_obj= GlobelMainMenu.parking_object;
				}
				MarkerOptions markerOption = new MarkerOptions().position(new LatLng(parking_obj.getmLatitude(), parking_obj.getmLongitude()));
				Marker marker = googleMap.addMarker(markerOption);
				
				googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
				marker.showInfoWindow();
	        }
	        
	        //mImageView.setImageBitmap(imageBitmap);
	    }
		
//		if (requestCode == ImageImportHelper.ACTION_TAKE_PHOTO_IMAGEVIEW) {
//			if (resultCode == RESULT_OK) {
//				//	mImageImportHelper.handleCameraPhoto(profileImageView, false);
//				//profile_image_arr = SharedPreferenceManager.getString(getApplicationContext(), GlobelProfile.profile_image);
//			}
//		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

}