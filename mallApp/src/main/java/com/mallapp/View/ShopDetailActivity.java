package com.mallapp.View;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Model.Offers_News;
import com.mallapp.Model.ShopDetailModel;
import com.mallapp.Model.Shops;
import com.mallapp.Model.ShopsModel;
import com.mallapp.SharedPreferences.SharedPreference;
import com.mallapp.globel.GlobelShops;
import com.mallapp.imagecapture.ScalingUtilities;
import com.mallapp.imagecapture.ScalingUtilities.ScalingLogic;
import com.mallapp.listeners.ShopsDataListener;
import com.mallapp.utils.GlobelOffersNews;
import com.mallapp.utils.VolleyNetworkUtil;
import com.squareup.picasso.Picasso;


@SuppressLint("InflateParams")
public class ShopDetailActivity extends FragmentActivity implements OnClickListener, ShopsDataListener {
	
	private  GestureDetector detector ;
	VolleyNetworkUtil volleyNetworkUtil;
	GoogleMap map;
	public static final int DEFAULT_MAP_ZOOM_LEVEL = 12;
	Double lat,lng;
	String locationName;


	ShopsModel shop_obj;
	private ImageView 		shop_logo;
	private TextView 		tv_about, tv_Detail, shop_name, 	 shop_detail;
	private TextView 		tv_address, tv_Phone, tv_Email, tv_Web, tv_Timing;
	private ImageButton	 	back_screen, is_fav , location, timing, social_sharing ;
	private LinearLayout 	related_shops, 	shop_offers,  social_sharing_layout, location_layout;
	//HorizontalScrollView 	shops_offers1;
	RelativeLayout 			timing_layout;
	private ImageButton 	message, face_book, twitter, email, chat;
	
	private ViewFlipper 	mViewFlipper;	
	private AnimationListener mAnimationListener;
	String url;

	@Override
	 protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_detail_activity);
		url = ApiConstants.GET_SHOP_DETAIL_URL_KEY+getIntent().getStringExtra("MallStoreId")+"&languageId=1";
		volleyNetworkUtil = new VolleyNetworkUtil(this);
		volleyNetworkUtil.GetShopDetail(url,this);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		detector = new GestureDetector(getApplicationContext(), new SwipeGestureDetector());
//		initActivity();
		displayData();
	}

	private void displayData() {
		
		/*shop_obj = GlobelShops.shopModel_obj;
		shop_name.setText(shop_obj.getStoreName());
		shop_detail.setText(shop_obj.getBriefText());*/
		tv_about 		= (TextView) findViewById(R.id.tv_about);
		tv_Detail 		= (TextView) findViewById(R.id.tv_offer_detail);
		tv_address 		= (TextView) findViewById(R.id.tv_address);
		tv_Phone 		= (TextView) findViewById(R.id.tv_phone);
		tv_Email 		= (TextView) findViewById(R.id.tv_email);
		tv_Web 		= (TextView) findViewById(R.id.tv_web);
		tv_Timing 		= (TextView) findViewById(R.id.tv_timings);
		back_screen = (ImageButton) findViewById(R.id.back);

		back_screen	.setOnClickListener(this);

		/*boolean fav	= shop_obj.isFav();
		if(fav)
			is_fav.setImageResource(R.drawable.ofer_detail_heart_p);
		else
			is_fav.setImageResource(R.drawable.ofer_detail_heart);*/
		
		/*setShopLogo();
		setShopOffers();
		setRelatedShop();*/
	}

	private void setShopOffers() {
	
		shop_offers.removeAllViews();
		SharedPreference sharedPreference 	= new SharedPreference();
		ArrayList <Offers_News> offers_list = sharedPreference.getOffersNews(getApplicationContext());
		
		if(offers_list!=null){
			for(int i=0; i<4; i++){
				Offers_News object= offers_list.get(i);
				View view =add_layoutOffers(object, i);
				shop_offers.addView(view);
			}
		}
	}
	
	
	
	@SuppressLint("InflateParams")
	private View add_layoutOffers(final Offers_News offer_obj , int index) {
		String image_nam = null, offer_name = null;
		
		final View view 	= getLayoutInflater().inflate(R.layout.list_item_shop_offers, null);
        TextView title 		= (TextView) view.findViewById(R.id.shop_offer_title);
        TextView shop_offer = (TextView) view.findViewById(R.id.shop_offer);
        ImageView shop_logo	= (ImageView) view.findViewById(R.id.shop_ofer_image);
        //Log.e("related offer...", "related offer..."+ index);
        if(index==0){
        	image_nam	= "offer_logo1";
        	offer_name= "Rough n Tough";
        	title.setText(offer_name);
        	shop_offer.setText("10% off");
        }else if(index==1){
        	
        	image_nam	= "offer_logo2";
        	offer_name= "soldier boots";
        	title.setText(offer_name);
        	shop_offer.setText("80% off");
        	
        }else if(index==2){
        	
        	image_nam	= "offer_logo3";
        	offer_name= "jeans & jeans";
        	title.setText(offer_name);
        	shop_offer.setText("40% off");
        	
        }else if(index==3){
        	
        	image_nam	= "offer_logo4";
        	offer_name= "Amazing offer";
        	title.setText(offer_name);
        	shop_offer.setText("10% off");
        }
		
		int imageResource 	= getResources().getIdentifier(image_nam, "drawable", getPackageName());
		Drawable d 			= getResources().getDrawable(imageResource);
		Bitmap bitmap 	= ((BitmapDrawable) d).getBitmap();
		int mDstWidth 	= getResources().getDimensionPixelSize(R.dimen.createview_destination_width1);
        int mDstHeight 	= getResources().getDimensionPixelSize(R.dimen.createview_destination_width);
        bitmap= ScalingUtilities.createScaledBitmap(bitmap, mDstWidth, mDstHeight, ScalingLogic.FIT);
		d = new BitmapDrawable(getResources(), bitmap);
		//back_image.setBackground(d);
		shop_logo.setImageDrawable(d);
		//title.setCompoundDrawablesWithIntrinsicBounds(null, d, null, null);
		
        view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobelOffersNews.related_offer_obj= offer_obj;
				Intent intent= new Intent(ShopDetailActivity.this, OffersRelatedDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		return view;
	}

	private void setRelatedShop() {
		related_shops.removeAllViews();
		SharedPreference sharedPreference = new SharedPreference();
		ArrayList <Offers_News> endorsement_list_filter = sharedPreference.getOffersNews(getApplicationContext());
		if(endorsement_list_filter!=null){
			for(int i=0; i<3; i++){
				Offers_News object= endorsement_list_filter.get(i);
				View view =add_layout(object);
				related_shops.addView(view);
			}
		}
	}
	
	private View add_layout(final Offers_News offer_obj) {
		
		LayoutInflater layoutInflater 	= (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view 				= layoutInflater.inflate(R.layout.list_item_offers, null);
        
        TextView title 		= (TextView) view.findViewById(R.id.title);
        TextView decs 		= (TextView) view.findViewById(R.id.center_city);
        TextView center_name= (TextView) view.findViewById(R.id.center_name);
        TextView shop_name 	= (TextView) view.findViewById(R.id.shop_name);
        final ImageButton is_fav	= (ImageButton) view.findViewById(R.id.fav_center);
        ImageView back_image= (ImageView) view.findViewById(R.id.center_image);
        
        title.setText(offer_obj.getTitle());
		decs.setText(offer_obj.getDetail());
		center_name.setText(offer_obj.getCenter_name());
		shop_name.setText(offer_obj.getShop_name());
		
		final boolean fav	= offer_obj.isFav();
		if(fav)
			is_fav.setImageResource(R.drawable.offer_fav_p);
		else
			is_fav.setImageResource(R.drawable.offer_fav);
		
		String image_nam	= offer_obj.getImage();
		int imageResource 	= getResources().getIdentifier(image_nam, "drawable", getPackageName());
		Drawable d 			= getResources().getDrawable(imageResource);
		Bitmap bitmap 	= ((BitmapDrawable) d).getBitmap();
		int mDstWidth 	= getResources().getDimensionPixelSize(R.dimen.createview_destination_width);
        int mDstHeight 	= getResources().getDimensionPixelSize(R.dimen.createview_destination_height);
		d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, mDstWidth,mDstHeight, true));
		back_image.setBackground(d);
		
		is_fav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!offer_obj.isFav()){
					is_fav.setImageResource(R.drawable.offer_fav_p);
					offer_obj.setFav(true);
				}else{
					is_fav.setImageResource(R.drawable.offer_fav);
					offer_obj.setFav(false);
				}
			}
		});
		
        view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("related offer...", "related offer...");
				GlobelOffersNews.related_offer_obj= offer_obj;
				Intent intent= new Intent(ShopDetailActivity.this, ShopDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		return view;
	}
	

	private void setShopLogo() {

		Picasso.with(this).load(shop_obj.getLogoURL()).into(shop_logo);
		/*String image_		= shop_obj.getLogo_image();
		int imageResource 	= getResources().getIdentifier(image_, "drawable", getPackageName());
		Drawable d 			= getResources().getDrawable(imageResource);
		Bitmap bitmap 		= ((BitmapDrawable) d).getBitmap();
		
//		Display display = getWindowManager().getDefaultDisplay();
//		Point size 		= new Point();
//		display.getSize(size);
		
		//int mDstWidth 	= size.x;
		int mDstWidth = getResources().getDimensionPixelSize(R.dimen.createview_destination_width1);
        int mDstHeight 	= getResources().getDimensionPixelSize(R.dimen.createview_destination_height1);
        bitmap = ScalingUtilities.createScaledBitmap(bitmap, mDstWidth, mDstHeight, ScalingLogic.FIT);
		d = new BitmapDrawable(getResources(), bitmap);
		shop_logo.setBackground(d);*/
	}

	@SuppressLint("ClickableViewAccessibility")
	private void initActivity() {

		shop_name 	= (TextView) findViewById(R.id.offer_title);
		shop_detail	= (TextView) findViewById(R.id.offer_detail);
		
		is_fav		= (ImageButton) findViewById(R.id.fav_offer);
		shop_logo	= (ImageView)	findViewById(R.id.shop_image_logo);
		
		related_shops	= (LinearLayout) findViewById(R.id.related_shops);
		shop_offers		= (LinearLayout) findViewById(R.id.shop_offers);
		location_layout = (LinearLayout) findViewById(R.id.location_layout);
		timing_layout	= (RelativeLayout) findViewById(R.id.timing_layout);
		social_sharing_layout= (LinearLayout) findViewById(R.id.social_layout);
		//shops_offers1	= (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		
		location	= (ImageButton) findViewById(R.id.location);
		timing		= (ImageButton) findViewById(R.id.timing);
		social_sharing = (ImageButton) findViewById(R.id.social_sharing);
		
		
		mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
		mViewFlipper.setAutoStart(true);
		mViewFlipper.setFlipInterval(4000);
		mViewFlipper.startFlipping();
		mViewFlipper.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(final View view, final MotionEvent event) {
				detector.onTouchEvent(event);
				return true;
			}
		});
		
		
		
		//animation listener
		mAnimationListener = new Animation.AnimationListener() {
			public void onAnimationStart(Animation animation) {
				//animation started event
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				//TODO animation stopped event
			}
		};
		
		message		= (ImageButton) findViewById(R.id.sms);
		face_book	= (ImageButton) findViewById(R.id.fb); 
		twitter 	= (ImageButton) findViewById(R.id.twiter);
		email		= (ImageButton) findViewById(R.id.email);
		chat		= (ImageButton) findViewById(R.id.chat);


		location	.setOnClickListener(this);
		timing		.setOnClickListener(this);
		social_sharing	.setOnClickListener(this);
		is_fav		.setOnClickListener(this);
		message		.setOnClickListener(this);
		face_book	.setOnClickListener(this); 
		twitter 	.setOnClickListener(this);
		email		.setOnClickListener(this);
		chat		.setOnClickListener(this);
	
		
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId() == back_screen.getId()){
			finish();
		}else if(v.getId() == is_fav.getId()){
			
			/*boolean fav	= shop_obj.isFav();
			if(fav){
				is_fav.setImageResource(R.drawable.ofer_detail_heart);
				shop_obj.setFav(false);
			}else{
				is_fav.setImageResource(R.drawable.ofer_detail_heart_p);
				shop_obj.setFav(true);
			}*/
				
			
		}else if(v.getId() == location.getId()){
			if(location_layout.getVisibility()== View.GONE){
				setInVisibility_tabs();
				location.setImageResource(R.drawable.shop_detail_location_p);
				location_layout.setVisibility(View.VISIBLE);
			}else{
				setInVisibility_tabs();
			}
		}else if(v.getId() == timing.getId()){
			if(timing_layout.getVisibility()== View.GONE){
				setInVisibility_tabs();
				timing.setImageResource(R.drawable.shop_detail_clock_p);
				timing_layout.setVisibility(View.VISIBLE);
				
			}else{
				setInVisibility_tabs();
			}
			
		}else if(v.getId() == social_sharing.getId()){

			if(social_sharing_layout.getVisibility()== View.GONE){
				setInVisibility_tabs();
				social_sharing.setImageResource(R.drawable.shop_detail_share_p);
				social_sharing_layout.setVisibility(View.VISIBLE);
			}else{
				setInVisibility_tabs();
			}
		}
		
	}
	
	private void setInVisibility_tabs() {

		social_sharing.	setImageResource(R.drawable.shop_detail_share);
		location.		setImageResource(R.drawable.shop_detail_location);
		timing.			setImageResource(R.drawable.shop_detail_clock);
		
		timing_layout.setVisibility(View.GONE);
		social_sharing_layout.setVisibility(View.GONE);
		location_layout.setVisibility(View.GONE);
	}
	
	
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	@Override
	public void onDataReceived(ArrayList<ShopsModel> shopsModelArrayList) {

	}

	@Override
	public void onShopDetailReceived(ShopDetailModel shopDetail) {

		initilizeMap();
		tv_about.setText(shopDetail.getAboutText());
		tv_Detail.setText(shopDetail.getBriefText());
		tv_address.setText(shopDetail.getAddress());
		tv_Phone.setText(shopDetail.getPhone());
		tv_Email.setText(shopDetail.getEmail());
		tv_Web.setText(shopDetail.getWebURL());
		lat = Double.parseDouble(shopDetail.getLatitude());
		lng = Double.parseDouble(shopDetail.getLongitude());
		locationName = shopDetail.getAddress();
		drawMarkerandZoom(lat, lng, locationName);

	}

	@Override
	public void OnError() {

	}

	class SwipeGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			try {
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_in));
					mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_out));
					// controlling animation
					mViewFlipper.getInAnimation().setAnimationListener(mAnimationListener);
					mViewFlipper.showNext();
					return true;
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_in));
					mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.right_out));
					// controlling animation
					mViewFlipper.getInAnimation().setAnimationListener(mAnimationListener);
					mViewFlipper.showPrevious();
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
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

}