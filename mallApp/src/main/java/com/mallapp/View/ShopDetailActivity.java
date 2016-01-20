package com.mallapp.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.Offers_News;
import com.mallapp.Model.ShopDetailModel;
import com.mallapp.Model.ShopsModel;
import com.mallapp.Model.StoreTimingsModel;
import com.mallapp.SharedPreferences.SharedPreference;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.db.DatabaseHelper;
import com.mallapp.listeners.ShopsDataListener;
import com.mallapp.utils.AppUtils;
import com.mallapp.utils.GlobelOffersNews;
import com.mallapp.utils.VolleyNetworkUtil;
import com.squareup.picasso.Picasso;


@SuppressLint("InflateParams")
public class ShopDetailActivity extends FragmentActivity implements OnClickListener, ShopsDataListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private GestureDetector detector;
    VolleyNetworkUtil volleyNetworkUtil;
    GoogleMap map;
    public static final int DEFAULT_MAP_ZOOM_LEVEL = 12;
    Double lat, lng;
    String locationName;
    private DatabaseHelper databaseHelper = null;
    Dao<ShopsModel, Integer> shopsDao;
    ArrayList<ShopsModel> dbList = new ArrayList<>();


    ShopsModel shop_obj;
    ShopDetailModel shop_detail_obj;
    Fragment fragment_Map;
    private ImageView shop_logo, shop_map, expand;
    private TextView tv_about, tv_Detail, shop_name, shop_detail;
    private TextView tv_address, tv_Phone, tv_Email, tv_Web, tv_Timing1, tv_Timing2;
    private ImageButton back_screen, is_fav, location, timing, social_sharing;
    private LinearLayout related_shops, shop_offers, social_sharing_layout, location_layout, rel_shop_offers_layout;
    HorizontalScrollView shops_offers1;
    RelativeLayout timing_layout;
    LinearLayout linear_timing_layout;
    private ImageButton message, face_book, twitter, email, chat;

    private AnimationListener mAnimationListener;
    String url;
    String mallStoreId;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_detail_activity);
        mallStoreId = getIntent().getStringExtra("MallStoreId");
        url = ApiConstants.GET_SHOP_DETAIL_URL_KEY + mallStoreId + "&languageId=1";
        volleyNetworkUtil = new VolleyNetworkUtil(this);
        volleyNetworkUtil.GetShopDetail(url, this);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//		initActivity();
        displayData();
    }

    private void displayData() {

		/*rest_obj = GlobelShops.shopModel_obj;
		shop_name.setText(rest_obj.getStoreName());
		shop_detail.setText(rest_obj.getBriefText());*/
        shop_map = (ImageView) findViewById(R.id.ivMap);
        expand = (ImageView) findViewById(R.id.iv_expand);
        shop_offers = (LinearLayout) findViewById(R.id.shop_offers);
        rel_shop_offers_layout = (LinearLayout) findViewById(R.id.shop_rel_offers);
        shops_offers1 = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
        shop_obj = new ShopsModel();
        shop_name = (TextView) findViewById(R.id.offer_title);
        is_fav = (ImageButton) findViewById(R.id.fav_offer);
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_Detail = (TextView) findViewById(R.id.tv_offer_detail);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_Phone = (TextView) findViewById(R.id.tv_phone);
        tv_Email = (TextView) findViewById(R.id.tv_email);
        tv_Web = (TextView) findViewById(R.id.tv_web);
        back_screen = (ImageButton) findViewById(R.id.back);
        linear_timing_layout = (LinearLayout) findViewById(R.id.layout_timings);
        is_fav.setOnClickListener(this);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        back_screen.setOnClickListener(this);
        tv_Web.setOnClickListener(this);
        expand.setOnClickListener(this);
        tv_Phone.setOnClickListener(this);
        tv_Email.setOnClickListener(this);
        try {
            // This is how, a reference of DAO object can be done
            shopsDao = getHelper().getShopsDao();

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		/*setShopLogo();
		setShopOffers();
		setRelatedShop();*/
    }

    private void setShopOffers(MallActivitiesModel[] storeOffers) {

        shop_offers.removeAllViews();
        SharedPreference sharedPreference = new SharedPreference();
        ArrayList<Offers_News> offers_list = sharedPreference.getOffersNews(getApplicationContext());
        if (storeOffers.length > 0) {
            for (MallActivitiesModel storeOffersModel : storeOffers
                    ) {
                View view = add_layoutOffers(storeOffersModel);
                shop_offers.addView(view);
            }
        } else {
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
    private View add_layoutOffers(final MallActivitiesModel offer_obj) {
        String image_nam = null, offer_name = null;

        final View view = getLayoutInflater().inflate(R.layout.list_item_shop_offers, null);
        TextView title = (TextView) view.findViewById(R.id.shop_offer_title);
        TextView shop_offer = (TextView) view.findViewById(R.id.shop_offer);
        ImageView shop_logo = (ImageView) view.findViewById(R.id.shop_ofer_image);

        title.setText(offer_obj.getActivityTextTitle());
        shop_offer.setText(offer_obj.getBriefText());
        Picasso.with(this).load(offer_obj.getImageURL()).into(shop_logo);


        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//				GlobelOffersNews.related_offer_obj= offer_obj;
                Intent intent = new Intent(ShopDetailActivity.this, OffersDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Offers_News_Constants.MALL_OBJECT, offer_obj);
                startActivity(intent);
            }
        });
        return view;
    }

    private void setRelatedShop() {
        related_shops.removeAllViews();
        SharedPreference sharedPreference = new SharedPreference();
        ArrayList<Offers_News> endorsement_list_filter = sharedPreference.getOffersNews(getApplicationContext());
        if (endorsement_list_filter != null) {
            for (int i = 0; i < 3; i++) {
                Offers_News object = endorsement_list_filter.get(i);
                View view = add_layout(object);
                related_shops.addView(view);
            }
        }
    }

    private View add_layout(final Offers_News offer_obj) {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.list_item_offers, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView decs = (TextView) view.findViewById(R.id.center_city);
        TextView center_name = (TextView) view.findViewById(R.id.center_name);
        TextView shop_name = (TextView) view.findViewById(R.id.shop_name);
        final ImageButton is_fav = (ImageButton) view.findViewById(R.id.fav_center);
        ImageView back_image = (ImageView) view.findViewById(R.id.center_image);

        title.setText(offer_obj.getTitle());
        decs.setText(offer_obj.getDetail());
        center_name.setText(offer_obj.getCenter_name());
        shop_name.setText(offer_obj.getShop_name());

        final boolean fav = offer_obj.isFav();
        if (fav)
            is_fav.setImageResource(R.drawable.offer_fav_p);
        else
            is_fav.setImageResource(R.drawable.offer_fav);

        String image_nam = offer_obj.getImage();
        int imageResource = getResources().getIdentifier(image_nam, "drawable", getPackageName());
        Drawable d = getResources().getDrawable(imageResource);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        int mDstWidth = getResources().getDimensionPixelSize(R.dimen.createview_destination_width);
        int mDstHeight = getResources().getDimensionPixelSize(R.dimen.createview_destination_height);
        d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, mDstWidth, mDstHeight, true));
        back_image.setBackground(d);

        is_fav.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!offer_obj.isFav()) {
                    is_fav.setImageResource(R.drawable.offer_fav_p);
                    offer_obj.setFav(true);
                } else {
                    is_fav.setImageResource(R.drawable.offer_fav);
                    offer_obj.setFav(false);
                }
            }
        });

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("related offer...", "related offer...");
                GlobelOffersNews.related_offer_obj = offer_obj;
                Intent intent = new Intent(ShopDetailActivity.this, ShopDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return view;
    }


    private void setShopLogo() {

        Picasso.with(this).load(shop_obj.getLogoURL()).into(shop_logo);
		/*String image_		= rest_obj.getLogo_image();
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


        shop_logo = (ImageView) findViewById(R.id.shop_image_logo);

        related_shops = (LinearLayout) findViewById(R.id.related_shops);
        shop_offers = (LinearLayout) findViewById(R.id.shop_offers);
        location_layout = (LinearLayout) findViewById(R.id.location_layout);
        timing_layout = (RelativeLayout) findViewById(R.id.timing_layout);
        social_sharing_layout = (LinearLayout) findViewById(R.id.social_layout);
        shops_offers1 = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);

        location = (ImageButton) findViewById(R.id.location);
        timing = (ImageButton) findViewById(R.id.timing);
        social_sharing = (ImageButton) findViewById(R.id.social_sharing);
		
		
		/*mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
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
		*/


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

        message = (ImageButton) findViewById(R.id.sms);
        face_book = (ImageButton) findViewById(R.id.fb);
        twitter = (ImageButton) findViewById(R.id.twiter);
        email = (ImageButton) findViewById(R.id.email);
        chat = (ImageButton) findViewById(R.id.chat);


        location.setOnClickListener(this);
        timing.setOnClickListener(this);
        social_sharing.setOnClickListener(this);
        message.setOnClickListener(this);
        face_book.setOnClickListener(this);
        twitter.setOnClickListener(this);
        email.setOnClickListener(this);
        chat.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {

        if (v.getId() == back_screen.getId()) {
            finish();
        } else if (v.getId() == is_fav.getId()) {

            boolean fav = shop_detail_obj.isFav();
            if (fav) {
                is_fav.setImageResource(R.drawable.ofer_detail_heart);
                url = ApiConstants.POST_FAV_SHOP_URL_KEY + SharedPreferenceUserProfile.getUserId(this) + "&EntityId=" + mallStoreId + "&IsShop=true" + "&IsDeleted=true";
                volleyNetworkUtil.PostFavShop(url);
                shop_detail_obj.setFav(false);

                shop_obj.setMallStoreId(mallStoreId);
                shop_obj.setFav(false);
                updateShops(shop_obj);
            } else {
                is_fav.setImageResource(R.drawable.ofer_detail_heart_p);
                url = ApiConstants.POST_FAV_SHOP_URL_KEY + SharedPreferenceUserProfile.getUserId(this) + "&EntityId=" + mallStoreId + "&IsShop=true" + "&IsDeleted=false";
                volleyNetworkUtil.PostFavShop(url);
                shop_detail_obj.setFav(true);

                shop_obj.setMallStoreId(mallStoreId);
                shop_obj.setFav(true);
                updateShops(shop_obj);

            }


        }/*else if(v.getId() == location.getId()){
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
		}*/ else if (v.getId() == tv_Web.getId()) {
            Intent intent = new Intent(ShopDetailActivity.this, WebViewActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("url", shop_detail_obj.getWebURL());
            startActivity(intent);
        } else if (v.getId() == expand.getId()) {
            Intent intent = new Intent(ShopDetailActivity.this, MapFullScreenActivity.class);
            intent.putExtra(Offers_News_Constants.SHOP_DETAIL_OBJECT, shop_detail_obj);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }else if (tv_Phone.getId() == v.getId()) {
            AppUtils.displayCallDialog(this, shop_detail_obj.getPhone());
        }else if (tv_Email.getId() == v.getId()) {
            AppUtils.sendEmail(this,shop_detail_obj.getEmail());
        }

    }

    private void setInVisibility_tabs() {

        social_sharing.setImageResource(R.drawable.shop_detail_share);
        location.setImageResource(R.drawable.shop_detail_location);
        timing.setImageResource(R.drawable.shop_detail_clock);

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
        getDBShops();
        shop_detail_obj = shopDetail;
        initilizeMap();
        shop_name.setText(shopDetail.getName());
        tv_about.setText(shopDetail.getAboutText());
        tv_Detail.setText(shopDetail.getBriefText());
        if (shopDetail.getAddress() != null && !shopDetail.getAddress().isEmpty())
            tv_address.setText(shopDetail.getAddress());
        else
            tv_address.setVisibility(View.GONE);
        if (shopDetail.getEmail() != null && !shopDetail.getEmail().isEmpty())
            tv_Email.setText(shopDetail.getEmail());
        else
            tv_Email.setVisibility(View.GONE);
        if (shopDetail.getPhone() != null && !shopDetail.getPhone().isEmpty())
            tv_Phone.setText(shopDetail.getPhone());
        else
            tv_Phone.setVisibility(View.GONE);
        if (shopDetail.getWebURL() == null || shopDetail.getWebURL().isEmpty())
            tv_Web.setVisibility(View.GONE);

        StoreTimingsModel[] timinigs = shopDetail.getStoreTimings();
        MallActivitiesModel[] storeOffers = shopDetail.getStoreOffers();
        setShopOffers(storeOffers);
        for (StoreTimingsModel st : timinigs
                ) {
            final RelativeLayout newView = (RelativeLayout) getLayoutInflater().inflate(R.layout.timing_layout, null);
            String t1 = st.getFromDay() + "-" + st.getToDay();
            TextView tv = (TextView) newView.findViewById(R.id.tv_d);
            tv.setText(t1);


            if (!st.getIsException()){
                String t2 = st.getOpeningTiming() + "-" + st.getClosingTiming();
                TextView tv1 = (TextView) newView.findViewById(R.id.tv_t);
                tv1.setText(t2);
                this.linear_timing_layout.addView(newView);
            }
            else {
                String t2 = st.getDescription();
                TextView tv1 = (TextView) newView.findViewById(R.id.tv_t);
                tv1.setText(t2);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    LinearLayout.LayoutParams relativeParams = null;
                    relativeParams = new LinearLayout.LayoutParams(
                            new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                    relativeParams.setMargins(0, 35, 0, 0);
                    newView.setLayoutParams(relativeParams);
                    newView.requestLayout();
                }
                this.linear_timing_layout.addView(newView);
            }
        }
        for (ShopsModel shopsModel : dbList
                ) {
            if (shopsModel.getMallStoreId().equals(mallStoreId)) {
                if (shopsModel.isFav()) {
                    is_fav.setImageResource(R.drawable.ofer_detail_heart_p);
                    shop_detail_obj.setFav(true);
                } else {
                    is_fav.setImageResource(R.drawable.ofer_detail_heart);
                    shop_detail_obj.setFav(false);
                }
            }
        }
        lat = Double.parseDouble(shopDetail.getLatitude());
        lng = Double.parseDouble(shopDetail.getLongitude());
        locationName = shopDetail.getAddress();
        if (shopDetail.getSiteMapActive()) {
            View frag = findViewById(R.id.mapAddress);
            frag.setVisibility(View.GONE);
            shop_map.setVisibility(View.VISIBLE);
            Picasso.with(this).load(shopDetail.getSiteMapURL()).placeholder(R.drawable.mallapp_placeholder).into(shop_map);
        } else {
            shop_map.setVisibility(View.GONE);
            drawMarkerandZoom(lat, lng, locationName);
        }
        BannerImagesModel bImage[] = shopDetail.getBannerImages();
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
     */
    private void initilizeMap() {

        FragmentManager myFragmentManager = getSupportFragmentManager();
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

    public void ImageSlider(BannerImagesModel bImages[]) {
        for (BannerImagesModel name : bImages) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .error(R.drawable.mallapp_placeholder)
                    .empty(R.drawable.mallapp_placeholder)
                    .errorDisappear(false)
                    .image(name.getBannerImageURL())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name.getBannerImageURL());

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(3500);
        mDemoSlider.addOnPageChangeListener(this);
    }

    public void updateShops(ShopsModel fav) {
        try {
            shopsDao.createOrUpdate(fav);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getDBShops() {
        try {
            // This is how, a reference of DAO object can be done
            Dao<ShopsModel, Integer> studentDao = getHelper().getShopsDao();
            // Get our query builder from the DAO
            final QueryBuilder<ShopsModel, Integer> queryBuilder = studentDao.queryBuilder();
            // We need only Students who are associated with the selected Teacher, so build the query by "Where" clause
            // Prepare our SQL statement
            final PreparedQuery<ShopsModel> preparedQuery = queryBuilder.prepare();
            // Fetch the list from Database by queryingit
            final Iterator<ShopsModel> studentsIt = studentDao.queryForAll().iterator();
            // Iterate through the StudentDetails object iterator and populate the comma separated String
            while (studentsIt.hasNext()) {
                final ShopsModel sDetails = studentsIt.next();
                dbList.add(sDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}