package com.mallapp.View;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.GestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.BannerImagesModel;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.MallDetailModel;
import com.mallapp.Model.MallTimingsModel;
import com.mallapp.listeners.MallDataListener;
import com.mallapp.utils.AppUtils;
import com.mallapp.utils.VolleyNetworkUtil;

public class MallDetailActivity extends SlidingDrawerActivity implements OnClickListener, MallDataListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    //region Data Members
    private GestureDetector detector;
    VolleyNetworkUtil volleyNetworkUtil;
    GoogleMap map;
    private SliderLayout mDemoSlider;
    public static final int DEFAULT_MAP_ZOOM_LEVEL = 12;
    Double lat, lng;
    String locationName;

    private ImageView mall_logo, mall_map, mall_background, expand;
    private TextView tv_about, tv_Detail, mall_name, rel_offer_title, shop_detail;
    private TextView tv_address, tv_Phone, tv_Email, tv_Web, tv_Timing1, tv_Timing2;
    private ImageButton open_drawer, open_navigation, location, timing, social_sharing;
    LinearLayout linear_timing_layout;
    String url;
    String mallStoreId;
    MallDetailModel mallDetailModel;

    ScrollView scrollView;
    LinearLayout error_layout;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_detail_activity);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
        mallStoreId = getIntent().getStringExtra(Offers_News_Constants.MALL_PLACE_ID);
        url = ApiConstants.GET_MALL_DETAIL_URL_KEY + mallStoreId + "&languageId=1";
        volleyNetworkUtil = new VolleyNetworkUtil(this);
        volleyNetworkUtil.GetMallDetail(url, this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
    }


    private void init() {
        scrollView	= (ScrollView) findViewById(R.id.scrollView);
        error_layout	= (LinearLayout) findViewById(R.id.error_layout);
        open_navigation = (ImageButton) findViewById(R.id.back);
        open_drawer = (ImageButton) findViewById(R.id.navigation_drawer);
        mallDetailModel = new MallDetailModel();
        expand = (ImageView) findViewById(R.id.iv_expand);
//        mall_background = (ImageView) findViewById(R.id.iv_background);
        mall_name = (TextView) findViewById(R.id.heading);
        mall_map = (ImageView) findViewById(R.id.ivMap);
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_Detail = (TextView) findViewById(R.id.tv_offer_detail);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_Phone = (TextView) findViewById(R.id.tv_phone);
        tv_Email = (TextView) findViewById(R.id.tv_email);
        rel_offer_title = (TextView) findViewById(R.id.rel_off_title);
        tv_Web = (TextView) findViewById(R.id.tv_web);
        linear_timing_layout = (LinearLayout) findViewById(R.id.layout_timings);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        open_navigation.setOnClickListener(this);
        open_drawer.setOnClickListener(this);
        open_navigation.setVisibility(View.GONE);
        open_drawer.setVisibility(View.VISIBLE);
        tv_Web.setOnClickListener(this);
        tv_address.setOnClickListener(this);
        tv_Phone.setOnClickListener(this);
        expand.setOnClickListener(this);
        tv_Email.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        /*if(navigation_button.getId() == v.getId()){
			finish();
		}*/
        if (v.getId() == tv_Web.getId()) {
            Intent intent = new Intent(MallDetailActivity.this, WebViewActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("url", mallDetailModel.getWebURL());
            startActivity(intent);
        } else if (v.getId() == tv_address.getId()) {
            Intent intent = new Intent(MallDetailActivity.this, MapDirectionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("latlong", mallDetailModel.getLatitude() + "-" + mallDetailModel.getLongitude());
            startActivity(intent);
        } else if (v.getId() == expand.getId()) {
            Intent intent = new Intent(MallDetailActivity.this, MapFullScreenActivity.class);
            intent.putExtra(Offers_News_Constants.SHOP_DETAIL_OBJECT, mallDetailModel);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
        if (open_navigation.getId() == v.getId()) {
            //DashboardTabFragmentActivity.uiHandler.sendEmptyMessage(1);
            finish();
        } else if (open_drawer.getId() == v.getId()) {
            SlidingDrawerActivity.uiHandler.sendEmptyMessage(1);
        }else if (tv_Phone.getId() == v.getId()) {
            AppUtils.displayCallDialog(this, mallDetailModel.getPhone());
        }else if (tv_Email.getId() == v.getId()) {
            AppUtils.sendEmail(this,mallDetailModel.getEmail());
        }
    }

    @Override
    public void onDataReceived(ArrayList<MallActivitiesModel> mallActivitiesModels) {

    }

    @Override
    public void onMallDetailReceived(MallDetailModel mallDetailModel) {

        this.mallDetailModel = mallDetailModel;
        initilizeMap();
//        Picasso.with(this).load(mallDetailModel.getAppBackgroundImageUrl()).placeholder(R.drawable.mallapp_placeholder).fit().into(mall_background);
        mall_name.setText(mallDetailModel.getName());
        tv_about.setText(mallDetailModel.getAboutText());
        tv_Detail.setText(mallDetailModel.getBriefText());

        if (mallDetailModel.getAddress() != null && !mallDetailModel.getAddress().isEmpty())
            tv_address.setText(mallDetailModel.getAddress().trim());
        else
            tv_address.setVisibility(View.GONE);
        if (mallDetailModel.getEmail() != null && !mallDetailModel.getEmail().isEmpty())
            tv_Email.setText(mallDetailModel.getEmail().trim());
        else
            tv_Email.setVisibility(View.GONE);
        if (mallDetailModel.getPhone() != null && !mallDetailModel.getPhone().isEmpty())
            tv_Phone.setText(mallDetailModel.getPhone().trim());
        else
            tv_Phone.setVisibility(View.GONE);
        if (mallDetailModel.getWebURL() == null || mallDetailModel.getWebURL().isEmpty())
            tv_Web.setVisibility(View.GONE);
        MallTimingsModel[] timinigs = mallDetailModel.getMallTimings();

        for (MallTimingsModel st : timinigs
                ) {
            String t1 = "";
            final RelativeLayout newView = (RelativeLayout) getLayoutInflater().inflate(R.layout.timing_layout, null);
            if (! (st.getFromDay().isEmpty()|| st.getToDay().isEmpty()) ){
                t1 = st.getFromDay() + "-" + st.getToDay();
            }
            TextView tv = (TextView) newView.findViewById(R.id.tv_d);
            tv.setText(t1);


            if (!st.getIsException()){
                String t2 = st.getStartTime() + "-" + st.getEndTime();
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
        lat = Double.parseDouble(mallDetailModel.getLatitude());
        lng = Double.parseDouble(mallDetailModel.getLongitude());
        locationName = mallDetailModel.getAddress();
        drawMarkerandZoom(lat, lng, locationName);
        BannerImagesModel bImage[] = mallDetailModel.getBannerImages();
        ImageSlider(bImage);
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
        scrollView.setVisibility(View.INVISIBLE);
        error_layout.setVisibility(View.VISIBLE);
    }

    private void initilizeMap() {

        FragmentManager myFragmentManager = getSupportFragmentManager();
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

    @Override
    public void onSliderClick(BaseSliderView slider) {

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
}