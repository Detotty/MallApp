package com.mallapp.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.BannerImagesModel;
import com.mallapp.Model.Offers_News;
import com.mallapp.Model.RestaurantDetailModel;
import com.mallapp.Model.RestaurantModel;
import com.mallapp.Model.RestaurantOffersModel;
import com.mallapp.Model.RestaurantTimingsModel;
import com.mallapp.SharedPreferences.SharedPreference;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.db.DatabaseHelper;
import com.mallapp.listeners.RestaurantDataListener;
import com.mallapp.utils.VolleyNetworkUtil;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.GestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation.AnimationListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantDetailActivity extends FragmentActivity implements OnClickListener, RestaurantDataListener,BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

	//region Data Members
	private  GestureDetector detector ;
	VolleyNetworkUtil volleyNetworkUtil;
	GoogleMap map;
	public static final int DEFAULT_MAP_ZOOM_LEVEL = 12;
	Double lat,lng;
	String locationName;
	private DatabaseHelper databaseHelper = null;
	Dao<RestaurantModel, Integer> restDao;
	ArrayList<RestaurantModel> dbList = new ArrayList<>();

	RestaurantModel rest_obj;
	RestaurantDetailModel rest_detail_obj;
	private ImageView 		shop_logo,rest_map,expand;
	private TextView 		tv_about, tv_Detail, shop_name, rel_offer_title,	 shop_detail;
	private TextView 		tv_address, tv_Phone, tv_Email, tv_Web, tv_Timing1, tv_Timing2;
	private ImageButton	 	back_screen, is_fav , location, timing, social_sharing ;
	private LinearLayout 	related_shops, 	shop_offers,  social_sharing_layout, location_layout, rel_shop_offers_layout;
	HorizontalScrollView shops_offers1;
	RelativeLayout 			timing_layout;
	LinearLayout 			linear_timing_layout;
	private ImageButton 	message, face_book, twitter, email, chat;

	private AnimationListener mAnimationListener;
	String url;
	String mallStoreId;
	private SliderLayout mDemoSlider;
	//endregion

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_detail_activity);
		mallStoreId = getIntent().getStringExtra("MallStoreId");
		url = ApiConstants.GET_RESTAURANT_DETAIL_URL_KEY+mallStoreId+"&languageId=1";
		volleyNetworkUtil = new VolleyNetworkUtil(this);
		volleyNetworkUtil.GetRestaurantDetail(url, this);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		init();
	}

	//region Data Initialization
	private void init() {

		expand	= (ImageView)	findViewById(R.id.iv_expand);
		shop_offers		= (LinearLayout) findViewById(R.id.shop_offers);
		rel_shop_offers_layout		= (LinearLayout) findViewById(R.id.shop_rel_offers);
		shops_offers1	= (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		rest_obj = new RestaurantModel();
		shop_name 	= (TextView) findViewById(R.id.offer_title);
		is_fav		= (ImageButton) findViewById(R.id.fav_offer);
		rest_map		= (ImageView) findViewById(R.id.ivMap);
		tv_about 		= (TextView) findViewById(R.id.tv_about);
		tv_Detail 		= (TextView) findViewById(R.id.tv_offer_detail);
		tv_address 		= (TextView) findViewById(R.id.tv_address);
		tv_Phone 		= (TextView) findViewById(R.id.tv_phone);
		tv_Email 		= (TextView) findViewById(R.id.tv_email);
		rel_offer_title 		= (TextView) findViewById(R.id.textView4);
		tv_Web 		= (TextView) findViewById(R.id.tv_web);
		back_screen = (ImageButton) findViewById(R.id.back);
		linear_timing_layout	= (LinearLayout) findViewById(R.id.layout_timings);
		is_fav.setOnClickListener(this);
		mDemoSlider = (SliderLayout)findViewById(R.id.slider);
		back_screen	.setOnClickListener(this);
		tv_Web.setOnClickListener(this);
		expand.setOnClickListener(this);
		try {
			// This is how, a reference of DAO object can be done
			restDao = getHelper().getRestaurantsDao();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	//endregion

	//region Restaurant Offers
	private void getRestaurantOffers(RestaurantOffersModel[] storeOffers) {

		rel_offer_title.setText(R.string.rest_offer_title);
		shop_offers.removeAllViews();
		SharedPreference sharedPreference 	= new SharedPreference();
		ArrayList <Offers_News> offers_list = sharedPreference.getOffersNews(getApplicationContext());
		if (storeOffers.length>0){
			for (RestaurantOffersModel restaurantOffersModel:storeOffers
					) {
				View view =add_layoutOffers(restaurantOffersModel);
				shop_offers.addView(view);
			}
		}
		else {
			rel_shop_offers_layout.setVisibility(View.GONE);
		}

		/*if(offers_list!=null){
			for(int i=0; i<4; i++){
				Offers_News object= offers_list.get(i);
				View view =add_layoutOffers(object, i);
				shop_offers.addView(view);
			}
		}*/
	}

	@SuppressLint("InflateParams")
	private View add_layoutOffers(final RestaurantOffersModel offer_obj) {
		String image_nam = null, offer_name = null;

		final View view 	= getLayoutInflater().inflate(R.layout.list_item_shop_offers, null);
		TextView title 		= (TextView) view.findViewById(R.id.shop_offer_title);
		TextView shop_offer = (TextView) view.findViewById(R.id.shop_offer);
		ImageView shop_logo	= (ImageView) view.findViewById(R.id.shop_ofer_image);

		title.setText(offer_obj.getActivityTextTitle());
		shop_offer.setText(offer_obj.getBriefText());
		Picasso.with(this).load(offer_obj.getImageURL()).into(shop_logo);



		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				GlobelOffersNews.related_offer_obj= offer_obj;
				/*Intent intent= new Intent(ShopDetailActivity.this, OffersRelatedDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(Offers_News_Constants.MALL_OBJECT, offer_obj);
				startActivity(intent);*/
			}
		});
		return view;
	}
	//endregion

	//region LifeCycle Methods and DB Helper
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
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	private DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		}
		return databaseHelper;
	}
	//endregion

	//region Click Events
	@Override
	public void onClick(View v) {

		if(v.getId() == back_screen.getId()){
			finish();
		}else if(v.getId() == is_fav.getId()){

			boolean fav	= rest_detail_obj.isFav();
			if(fav){
				is_fav.setImageResource(R.drawable.ofer_detail_heart);
				url = ApiConstants.POST_FAV_SHOP_URL_KEY+ SharedPreferenceUserProfile.getUserId(this)+"&EntityId="+mallStoreId+"&IsShop=false"+"&IsDeleted=true";
				volleyNetworkUtil.PostFavShop(url);
				rest_detail_obj.setFav(false);

				rest_obj.setMallResturantId(mallStoreId);
				rest_obj.setFav(false);
				updateShops(rest_obj);
			}else{
				is_fav.setImageResource(R.drawable.ofer_detail_heart_p);
				url = ApiConstants.POST_FAV_SHOP_URL_KEY+SharedPreferenceUserProfile.getUserId(this)+"&EntityId="+mallStoreId+"&IsShop=false"+"&IsDeleted=false";
				volleyNetworkUtil.PostFavShop(url);
				rest_detail_obj.setFav(true);

				rest_obj.setMallResturantId(mallStoreId);
				rest_obj.setFav(true);
				updateShops(rest_obj);

			}


		}
		else if (v.getId() == tv_Web.getId()){
			Intent intent= new Intent(RestaurantDetailActivity.this, WebViewActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("url", rest_detail_obj.getWebURL());
			startActivity(intent);
		}
		else if (v.getId() == expand.getId()){
			Intent intent= new Intent(RestaurantDetailActivity.this, MapFullScreenActivity.class);
			intent.putExtra(Offers_News_Constants.SHOP_DETAIL_OBJECT,rest_detail_obj);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

		}

	}
	//endregion

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	@Override
	public void onDataReceived(ArrayList<RestaurantModel> shopsModelArrayList) {

	}

	@Override
	public void onRestaurantDetailReceived(RestaurantDetailModel restaurantDetailModel) {
		getDBRestaurants();
		rest_detail_obj = restaurantDetailModel;
		initilizeMap();
		shop_name.setText(restaurantDetailModel.getName());
		tv_about.setText(restaurantDetailModel.getAboutText());
		tv_Detail.setText(restaurantDetailModel.getBriefText());
		tv_address.setText(restaurantDetailModel.getAddress());
		tv_Phone.setText(restaurantDetailModel.getPhone());
		tv_Email.setText(restaurantDetailModel.getEmail());
		RestaurantTimingsModel[] timinigs = restaurantDetailModel.getResturantTimings();
		RestaurantOffersModel[] storeOffers = restaurantDetailModel.getRestaurantOffers();
		getRestaurantOffers(storeOffers);
		for (RestaurantTimingsModel st:timinigs
				) {
			final RelativeLayout newView = (RelativeLayout) getLayoutInflater().inflate(R.layout.timing_layout, null);
			String t1 = st.getFromDay()+"-"+st.getToDay();
			String t2 = st.getOpeningTimings()+"-"+st.getClosingTimings();

			TextView tv=(TextView) newView.findViewById(R.id.tv_d);
			tv.setText(t1);
			TextView tv1=(TextView) newView.findViewById(R.id.tv_t);
			tv1.setText(t2);
			this.linear_timing_layout.addView(newView);
		}
		for (RestaurantModel restaurantModel:dbList
				) {
			if (restaurantModel.getMallResturantId().equals(mallStoreId)){
				if (restaurantModel.isFav()){
					is_fav.setImageResource(R.drawable.ofer_detail_heart_p);
					rest_detail_obj.setFav(true);
				}
				else {
					is_fav.setImageResource(R.drawable.ofer_detail_heart);
					rest_detail_obj.setFav(false);
				}
			}
		}
		lat = Double.parseDouble(restaurantDetailModel.getLatitude());
		lng = Double.parseDouble(restaurantDetailModel.getLongitude());
		locationName = restaurantDetailModel.getAddress();
		if(restaurantDetailModel.getSiteMapActive()){
			View frag = findViewById(R.id.mapAddress);
			frag.setVisibility(View.GONE);
			rest_map.setVisibility(View.VISIBLE);
			Picasso.with(this).load(restaurantDetailModel.getSiteMapURL()).into(rest_map);
		}
		else {
			rest_map.setVisibility(View.GONE);
			drawMarkerandZoom(lat, lng, locationName);
		}
		BannerImagesModel bImage[] = restaurantDetailModel.getBannerImages();
		ImageSlider(bImage);
	}

	@Override
	public void OnError() {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onSliderClick(BaseSliderView slider) {

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
			android.util.Log.e("Initializing Map", "Initializing Map");

			// Changing map type
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			// Showing / hiding your current location
//			map.setMyLocationEnabled(true);

			// Enable / Disable zooming controls
//			map.getUiSettings().setZoomControlsEnabled(true);

			// Enable / Disable my location button
//			map.getUiSettings().setMyLocationButtonEnabled(true);

			// Enable / Disable Compass icon
//			map.getUiSettings().setCompassEnabled(true);

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

	public void ImageSlider(BannerImagesModel bImages[]){
		for(BannerImagesModel name : bImages){
			TextSliderView textSliderView = new TextSliderView(this);
			// initialize a SliderLayout
			textSliderView
					.error(R.drawable.profile_image_placeholder)
					.empty(R.drawable.profile_image_placeholder)
					.errorDisappear(false)
					.image(name.getBannerImageURL())
					.setScaleType(BaseSliderView.ScaleType.Fit)
					.setOnSliderClickListener(this);

			//add your extra information
			textSliderView.bundle(new Bundle());
			textSliderView.getBundle()
					.putString("extra",name.getBannerImageURL());

			mDemoSlider.addSlider(textSliderView);
		}
		mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
		mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
		mDemoSlider.setCustomAnimation(new DescriptionAnimation());
		mDemoSlider.setDuration(3500);
		mDemoSlider.addOnPageChangeListener(this);
	}

	public void updateShops(RestaurantModel fav){
		try {
			restDao.createOrUpdate(fav);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getDBRestaurants() {
		try {
			// This is how, a reference of DAO object can be done
			Dao<RestaurantModel, Integer> studentDao = getHelper().getRestaurantsDao();
			// Get our query builder from the DAO
			final QueryBuilder<RestaurantModel, Integer> queryBuilder = studentDao.queryBuilder();
			// We need only Students who are associated with the selected Teacher, so build the query by "Where" clause
			// Prepare our SQL statement
			final PreparedQuery<RestaurantModel> preparedQuery = queryBuilder.prepare();
			// Fetch the list from Database by queryingit
			final Iterator<RestaurantModel> studentsIt = studentDao.queryForAll().iterator();
			// Iterate through the StudentDetails object iterator and populate the comma separated String
			while (studentsIt.hasNext()) {
				final RestaurantModel sDetails = studentsIt.next();
				dbList.add(sDetails);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
