package com.mallapp.View;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.MallDetailModel;
import com.mallapp.Model.RestaurantDetailModel;
import com.mallapp.Model.ShopDetailModel;
import com.mallapp.utils.AppUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by Sharjeel on 12/28/2015.
 */
public class MapFullScreenActivity extends FragmentActivity implements View.OnClickListener {

    ImageView btn_close;
    GoogleMap map;
    ShopDetailModel shopDetail;
    RestaurantDetailModel restaurantDetail;
    MallDetailModel mallDetail;
    private ImageView 	shop_map;
    boolean mapActive;
    String url,add;
    Double lat,lon;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.map_dialog_activity);

        shop_map	= (ImageView)	findViewById(R.id.ivMap);
        btn_close = (ImageView) findViewById(R.id.iv_close);
        btn_close.setOnClickListener(this);

        //region Data Object
        try {
            shopDetail = (ShopDetailModel) getIntent().getSerializableExtra(Offers_News_Constants.SHOP_DETAIL_OBJECT);
            mapActive = shopDetail.getSiteMapActive();
            url = shopDetail.getSiteMapURL();
            lat = Double.parseDouble(shopDetail.getLatitude());
            lon = Double.parseDouble(shopDetail.getLongitude());
            add = shopDetail.getAddress();

        }catch (Exception e){
            try{
                restaurantDetail = (RestaurantDetailModel) getIntent().getSerializableExtra(Offers_News_Constants.SHOP_DETAIL_OBJECT);
                mapActive = restaurantDetail.getSiteMapActive();
                url = restaurantDetail.getSiteMapURL();
                lat = Double.parseDouble(restaurantDetail.getLatitude());
                lon = Double.parseDouble(restaurantDetail.getLongitude());
                add = restaurantDetail.getAddress();
            }catch (Exception es){
                mallDetail = (MallDetailModel) getIntent().getSerializableExtra(Offers_News_Constants.SHOP_DETAIL_OBJECT);
                mapActive = false;
                lat = Double.parseDouble(mallDetail.getLatitude());
                lon = Double.parseDouble(mallDetail.getLongitude());
                add = mallDetail.getAddress();
            }


        }
        //endregion

        if(mapActive){
            View frag = findViewById(R.id.mapAddress);
            frag.setVisibility(View.GONE);
            shop_map.setVisibility(View.VISIBLE);
            shop_map.setRotation(90);
            Picasso.with(this).load(url).placeholder(R.drawable.mallapp_placeholder).into(shop_map);
        }
        else {
            shop_map.setVisibility(View.GONE);
            initilizeMap();
            AppUtils.drawMarkerandZoom(lat, lon, add,map);
        }



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_close:
                this.finish();
                break;
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

}
